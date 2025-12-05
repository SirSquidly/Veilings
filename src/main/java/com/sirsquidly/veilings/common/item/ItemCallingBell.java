package com.sirsquidly.veilings.common.item;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.init.VeilingsSounds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

import java.util.List;

public class ItemCallingBell extends Item
{
    public ItemCallingBell()
    {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (playerIn.isSneaking())
        {
            int current = getCommandType(stack);
            if (++current > 2) { current = 0; }
            setCommandType(stack, current);

            playerIn.sendStatusMessage(new TextComponentTranslation("message.veilings.calling_bell.set_mode_" + current), true);
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        double distance = 8;

        Vec3d eyePosition = playerIn.getPositionEyes(1.0F);
        Vec3d lookVector = playerIn.getLook(1.0F);
        //eye.add(look.scale(30));
        Vec3d traceEnd = eyePosition.add(lookVector.x * 30, lookVector.y * 30, lookVector.z * 30);
        RayTraceResult blockTrace = worldIn.rayTraceBlocks(eyePosition, traceEnd, false, true, false);
        if (blockTrace == null) return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        BlockPos selectedBlock = blockTrace.getBlockPos();
        List<AbstractVeiling> veilings = getNearbyVeilings(playerIn, selectedBlock, (int) distance);
        boolean didAnything = !veilings.isEmpty();


        for (AbstractVeiling v : veilings)
        {
            if (!worldIn.isRemote)
            {
                v.setCommandMode(this.getCommandType(stack));

                /** ONLY set HomePosition when they are Wandering! */
                if (v.getCommandMode() == 1) v.setHomePosAndDistance(v.getPosition(), 8);
                else v.detachHome();

                v.setAttackTarget(null);
                v.getNavigator().clearPath();
                v.playSound(VeilingsSounds.ENTITY_VEILING_AGREE, 0.25F, v.getPublicSoundPitch());
            }
        }

        if (didAnything && !worldIn.isRemote)
        {
            ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.CRIT,
                    selectedBlock.getX() + 0.5, selectedBlock.getY() + 1, selectedBlock.getZ() + 0.5,
                    80,
                    0.3D * distance, 0.5D, 0.3D * distance,
                    0.05D
            );
        }



        playerIn.swingArm(handIn);

        SoundEvent sound = this.getCommandType(stack) == 0 ? VeilingsSounds.ITEM_CALLING_BELL_USE1 : this.getCommandType(stack) == 1 ? VeilingsSounds.ITEM_CALLING_BELL_USE2 : VeilingsSounds.ITEM_CALLING_BELL_USE3;
        worldIn.playSound(playerIn, playerIn.getPosition(), sound, SoundCategory.PLAYERS, didAnything ? 0.5F : 0.2F, 1.0F);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    public List<AbstractVeiling> getNearbyVeilings(EntityPlayer player, BlockPos pos, int distance)
    {
        List<AbstractVeiling> veilings = player.world.getEntitiesWithinAABB(AbstractVeiling.class, new AxisAlignedBB(pos).grow(distance));
        /* ONLY get OUR Veilings. */
        veilings.removeIf(thisEntity -> !player.getUniqueID().equals(thisEntity.getOwnerId()));

        return veilings;
    }

    /** Attach the Controlled Veiling Type via NBT. */
    public static void setCommandType(ItemStack stack, int type)
    {
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        nbt.setInteger("ControlType", type);
        stack.setTagCompound(nbt);
    }

    /** Returns the Resonance ID of a given itemstack. */
    public static Integer getCommandType(ItemStack stack)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("ControlType"))
            { return tagCompound.getInteger("ControlType"); }
        }
        return 0;
    }


    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.YELLOW + I18n.translateToLocalFormatted("description.veilings.calling_bell.commandMode" + getCommandType(stack)));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.calling_bell.desc1"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.calling_bell.desc2"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.calling_bell.desc3"));
    }
}