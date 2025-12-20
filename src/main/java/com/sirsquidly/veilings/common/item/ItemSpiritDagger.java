package com.sirsquidly.veilings.common.item;

import com.sirsquidly.veilings.common.entity.*;
import com.sirsquidly.veilings.init.VeilingsSounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class ItemSpiritDagger extends Item
{
    public ItemSpiritDagger()
    {
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        playerIn.swingArm(handIn);
        playerIn.getCooldownTracker().setCooldown(stack.getItem(), 15);
        playerIn.playSound(VeilingsSounds.ITEM_SPIRIT_DAGGER_USE, 0.7F, (worldIn.rand.nextFloat() * 0.3F) + 0.9F);
        if (!worldIn.isRemote)
        {
            EntitySpiritDagger spiritDagger = new EntitySpiritDagger(worldIn, playerIn, null);

            ItemStack headSlot = playerIn.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

            if (!headSlot.isEmpty() && headSlot.getItem() instanceof ItemVeilingMask)
            {
                ItemVeilingMask mask = (ItemVeilingMask)headSlot.getItem();

                if (mask.getMaskType() == 2)
                {
                    List<AbstractVeiling> checkNearbyDramatist = playerIn.world.getEntitiesWithinAABB(EntityVeilingDramatist.class, playerIn.getEntityBoundingBox().grow(10),
                            veiling -> (veiling.getOwnerId() != null && veiling.getOwnerId().equals(playerIn.getUniqueID())));

                    if (!checkNearbyDramatist.isEmpty())
                    { spiritDagger.setAppliesWeakness(true); }
                }
            }

            spiritDagger.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.9F, 1.0F);
            worldIn.spawnEntity(spiritDagger);

            stack.shrink(1);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}