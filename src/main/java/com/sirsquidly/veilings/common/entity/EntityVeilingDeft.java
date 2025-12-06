package com.sirsquidly.veilings.common.entity;

import com.sirsquidly.veilings.common.entity.wicked.EntityWickedVeilingDeft;
import com.sirsquidly.veilings.init.VeilingsLootTables;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityVeilingDeft extends AbstractVeiling
{
    public EntityVeilingDeft(World worldIn)
    { super(worldIn); }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
    }

    public EntityAgeable createChild(EntityAgeable ageable) { return new EntityVeilingDeft(ageable.world); }
    public AbstractVeiling getInverse(AbstractVeiling thisVeiling) { return new EntityWickedVeilingDeft(thisVeiling.world); }
    protected ResourceLocation getLootTable()
    {
        return VeilingsLootTables.VEILING_DEFT_DROPS;
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isOwner(player))
        {
            if (!itemstack.isEmpty() && (itemstack.getItem() instanceof ItemSword || itemstack.getItem() instanceof ItemAxe))
            {
                ItemStack oldHeldItem = this.getHeldItem(EnumHand.MAIN_HAND);

                ItemStack newHeld = itemstack.copy();
                newHeld.setCount(1);
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, newHeld);

                if (!world.isRemote && !player.isCreative())
                {
                    itemstack.shrink(1);

                    if (!oldHeldItem.isEmpty())
                    {
                        if (itemstack.isEmpty())
                        { player.setHeldItem(EnumHand.MAIN_HAND, oldHeldItem); }
                        else if (!player.inventory.addItemStackToInventory(oldHeldItem))
                        { player.dropItem(oldHeldItem, false); }
                    }
                }

                player.swingArm(hand);
                return true;
            }
        }

        return super.processInteract(player, hand);
    }
}