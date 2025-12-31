package com.sirsquidly.veilings.util.veilingItemUse;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.item.ItemStack;

public interface IVeilingItemUse
{
    void onItemUse(AbstractVeiling veiling, ItemStack stack, int useTime);

    void onItemUseFinish(AbstractVeiling veiling, ItemStack stack, int useTime);

    int getUseTime();

    /** If the Veiling can literally use an item, such as obeying cooldowns on Eating or Play. */
    boolean canUseItem(AbstractVeiling veiling, ItemStack stack);

    /** If the item is one Veilings should accept from anyone.*/
    boolean isSafeItem(AbstractVeiling veiling, ItemStack stack);
}
