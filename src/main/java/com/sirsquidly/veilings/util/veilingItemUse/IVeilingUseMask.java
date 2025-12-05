package com.sirsquidly.veilings.util.veilingItemUse;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.wicked.AbstractWickedVeiling;
import com.sirsquidly.veilings.common.item.ItemVeilingMask;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class IVeilingUseMask implements IVeilingItemUse
{
    public void onItemUse(AbstractVeiling veiling, ItemStack stack, int useTime) {}

    public void onItemUseFinish(AbstractVeiling veiling, ItemStack stack, int useTime)
    {
        AbstractVeiling newType = ((ItemVeilingMask)stack.getItem()).getVeilingType(veiling.world);

        if (newType.getClass() != veiling.getClass())
        {
            veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
            veiling.multiplyLogic.transformVeiling(veiling.world, veiling, newType);
        }
        else
        {
            veiling.entityDropItem(stack.copy(), 1);
            veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    public boolean canUseItem(AbstractVeiling veiling, ItemStack stack) { return !(veiling instanceof AbstractWickedVeiling); }

    public int getUseTime() { return 20; }
}
