package com.sirsquidly.veilings.util.veilingItemUse;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.item.ItemStack;

public interface IVeilingItemUse
{
    void onItemUse(AbstractVeiling veiling, ItemStack stack, int useTime);

    void onItemUseFinish(AbstractVeiling veiling, ItemStack stack, int useTime);

    int getUseTime();

    boolean canUseItem(AbstractVeiling veiling, ItemStack stack);
}
