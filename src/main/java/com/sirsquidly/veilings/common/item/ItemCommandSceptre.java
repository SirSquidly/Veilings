package com.sirsquidly.veilings.common.item;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingCustodian;
import com.sirsquidly.veilings.common.entity.EntityVeilingDeft;
import com.sirsquidly.veilings.common.entity.EntityVeilingDramatist;
import com.sirsquidly.veilings.init.VeilingsSounds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCommandSceptre extends Item
{
    public ItemCommandSceptre()
    {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.TOOLS);

        this.addPropertyOverride(new ResourceLocation("ControlType"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                NBTTagCompound tagCompound = stack.getTagCompound();
                if (tagCompound != null && tagCompound.hasKey("ControlType"))
                { return tagCompound.getInteger("ControlType"); }

                return -1.0F;
            }
        });
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (playerIn.isSneaking())
        {
            int current = getControlType(stack);
            if (++current > 2)
            { current = -1; }
            setControlType(stack, current);

            playerIn.sendStatusMessage(new TextComponentTranslation("message.veilings.attack_sceptre.set_mode_" + current), true);

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        Vec3d eyePosition = playerIn.getPositionEyes(1.0F);
        Vec3d lookVector = playerIn.getLook(1.0F);
        //eye.add(look.scale(30));
        Vec3d traceEnd = eyePosition.add(lookVector.x * 30, lookVector.y * 30, lookVector.z * 30);
        RayTraceResult blockTrace = worldIn.rayTraceBlocks(eyePosition, traceEnd, false, true, false);
        if (blockTrace != null) traceEnd = blockTrace.hitVec;

        List<AbstractVeiling> veilings = getNearbyVeilings(playerIn, stack, 30);
        boolean didAnything = !veilings.isEmpty();

        EntityLivingBase entityTarget = getFirstSeenLivingEntity(playerIn, eyePosition, lookVector, traceEnd, 30);

        if (entityTarget != null)
        { preformAttackCommand(worldIn, veilings, entityTarget, stack); }
        else if (blockTrace != null)
        { preformMoveCommand(worldIn, veilings, blockTrace.getBlockPos(), stack); }

        if (didAnything) playerIn.swingArm(handIn);
        worldIn.playSound(playerIn, playerIn.getPosition(), VeilingsSounds.ITEM_ATTACK_SCEPTRE_USE, SoundCategory.PLAYERS, didAnything ? 0.5F : 0.1F, 1.0F);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    public void preformAttackCommand(World worldIn, List<AbstractVeiling> veilings, EntityLivingBase target, ItemStack heldStack)
    {
        int soundAmount = 0;
        System.out.print("RAN ATTACK COMMAND");
        for (AbstractVeiling v : veilings)
        {
            if (!worldIn.isRemote)
            {
                if (v.getAttackTarget() != null && isRepeatTarget(heldStack, v.getAttackTarget().getPosition(), 9))
                { v.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 0)); }

                v.setCommandMode(0);
                v.setAttackTarget(target);
                v.detachHome();

                if (soundAmount < 6)
                {
                    v.playSound(VeilingsSounds.ENTITY_VEILING_AGREE, 0.5F, v.getPublicSoundPitch());
                    ++soundAmount;
                }


                ((WorldServer) worldIn).spawnParticle(EnumParticleTypes.CRIT_MAGIC,
                        v.posX, v.posY + (v.height * 0.5D), v.posZ,
                        10,
                        0.1D, v.height * 0.25D, 0.1D,
                        0.05D
                );
            }

            if (!worldIn.isRemote)
            {
                ((WorldServer) worldIn).spawnParticle(EnumParticleTypes.SPELL_INSTANT,
                        target.posX, target.posY + (target.height * 0.5D), target.posZ,
                        20,
                        0.1D, target.height * 0.25D, 0.1D,
                        0.05D
                );
            }
        }
    }

    public void preformMoveCommand(World worldIn, List<AbstractVeiling> veilings, BlockPos pos, ItemStack heldStack)
    {
        int soundAmount = 0;

        for (AbstractVeiling v : veilings)
        {
            if (!worldIn.isRemote)
            {
                if (isRepeatTarget(heldStack, pos, 9))
                { v.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 0)); }

                v.detachHome();
                v.setAttackTarget(null);
                v.setCommandMode(2);

                if (soundAmount < 6)
                {
                    v.playSound(VeilingsSounds.ENTITY_VEILING_AGREE, 0.25F, v.getPublicSoundPitch());
                    ++soundAmount;
                }

                v.getNavigator().tryMoveToXYZ(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 1D);
            }
        }
        setLastPos(heldStack, pos);


        if (!worldIn.isRemote)
        {
            ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.SPELL_INSTANT,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    20,
                    0.1D, 0.1D, 0.1D,
                    0.05D
            );
        }
    }

    /** If the given position is close enough to the last one saved to the item. */
    public static boolean isRepeatTarget(ItemStack stack, BlockPos newPos, double maxDistanceSq)
    {
        BlockPos old = getLastPos(stack);
        if (newPos == null || old == null) return false;
        return old.distanceSq(newPos) <= maxDistanceSq;
    }

    /** Returns the Resonance ID of a given itemstack. */
    public static Integer getControlType(ItemStack stack)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("ControlType"))
            { return tagCompound.getInteger("ControlType"); }
        }
        return -1;
    }

    /** Attach the Controlled Veiling Type via NBT. */
    public static void setLastPos(ItemStack stack, BlockPos pos)
    {
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        nbt.setLong("LastPosition", pos.toLong());
        stack.setTagCompound(nbt);
    }

    public static BlockPos getLastPos(ItemStack stack)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("LastPosition"))
        {
            long l = stack.getTagCompound().getLong("LastPosition");
            return BlockPos.fromLong(l);
        }
        return null;
    }

    /** Attach the Controlled Veiling Type via NBT. */
    public static void setControlType(ItemStack stack, int type)
    {
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        nbt.setInteger("ControlType", type);
        stack.setTagCompound(nbt);
    }

    public EntityLivingBase getFirstSeenLivingEntity(EntityPlayer player, Vec3d eyePosition, Vec3d lookVector, Vec3d traceEnd, double distance)
    {
        AxisAlignedBB aabb = player.getEntityBoundingBox().expand(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance).grow(1.0D);

        EntityLivingBase best = null;
        double closest = distance;

        for (Entity entity : player.world.getEntitiesWithinAABBExcludingEntity(player, aabb))
        {
            if (!(entity instanceof EntityLivingBase) || !entity.canBeCollidedWith()) continue;

            if (entity instanceof AbstractVeiling)
            {
                AbstractVeiling veiling = (AbstractVeiling)entity;

                if (player.getUniqueID().equals(veiling.getOwnerId())) continue;
            }

            AxisAlignedBB box = entity.getEntityBoundingBox().grow(0.3D);
            RayTraceResult hit = box.calculateIntercept(eyePosition, traceEnd);

            if (hit != null)
            {
                double hitDist = eyePosition.distanceTo(hit.hitVec);

                if (hitDist < closest)
                {
                    closest = hitDist;
                    best = (EntityLivingBase) entity;
                }
            }
        }

        return best;
    }

    public List<AbstractVeiling> getNearbyVeilings(EntityPlayer player, ItemStack stack, int distance)
    {
        int type = getControlType(stack);

        List<AbstractVeiling> veilings = player.world.getEntitiesWithinAABB(AbstractVeiling.class, player.getEntityBoundingBox().grow(distance));
        /* ONLY get OUR Veilings. */
        veilings.removeIf(thisEntity -> !player.getUniqueID().equals(thisEntity.getOwnerId()));

        veilings.removeIf(thisEntity -> !typeMatchesSceptreSetting(thisEntity, type));

        veilings.removeIf(thisEntity -> thisEntity.getCommandMode() == 1);

        return veilings;
    }

    private boolean typeMatchesSceptreSetting(AbstractVeiling veiling, int controlType)
    {
        if (controlType == -1) return true;

        switch (controlType)
        {
            case 0: return veiling instanceof EntityVeilingDeft;
            case 1: return veiling instanceof EntityVeilingCustodian;
            case 2: return veiling instanceof EntityVeilingDramatist;
            default: return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.YELLOW + I18n.translateToLocalFormatted("description.veilings.command_sceptre.veilingType" + getControlType(stack)));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.command_sceptre.desc1"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.command_sceptre.desc2"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.command_sceptre.desc3"));
    }
}
