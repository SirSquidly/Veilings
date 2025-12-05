package com.sirsquidly.veilings.util.veilingItemUse;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.config.ConfigParser;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;

public class IVeilingUseTreat implements IVeilingItemUse
{
    public void onItemUse(AbstractVeiling veiling, ItemStack stack, int useTime)
    {
        if (useTime > 6 && veiling.world.rand.nextInt(20) == 0)
        {
            veiling.playSound(veiling.getEatSound(), 1.0F, veiling.getPublicSoundPitch());

            ((WorldServer)veiling.world).spawnParticle(
                    EnumParticleTypes.ITEM_CRACK,
                    veiling.posX, veiling.posY + 0.2D, veiling.posZ,
                    2,
                    0.2D, 0.2D, 0.2D,
                    0.05D,  Item.getIdFromItem(stack.getItem()), stack.getMetadata()
            );
        }
    }


    public void onItemUseFinish(AbstractVeiling veiling, ItemStack stack, int useTime)
    {
        int moodShift = stack.getItem() == Items.ROTTEN_FLESH ? -10 : ConfigParser.veilingTreatHappiness.get(getIndexStackInVeilingTreat(stack));

        ItemStack favorite = veiling.getFavoriteFood();
        boolean isFoodTheirFavorite = stack.getItem() == favorite.getItem() && stack.getMetadata() == favorite.getMetadata();
        if (isFoodTheirFavorite)
        {
            moodShift += 5;
            veiling.spawnOverheadParticle(EnumParticleTypes.HEART, 1);
        }

        veiling.heal(1);

        veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);

        veiling.shiftHappiness(moodShift);
        veiling.playSound(veiling.getEatSound(), 1.0F, veiling.getPublicSoundPitch());
        veiling.setEatCooldown(200);

        ((WorldServer)veiling.world).spawnParticle(
                EnumParticleTypes.ITEM_CRACK,
                veiling.posX, veiling.posY + 0.2D, veiling.posZ,
                10,
                0.2D, 0.2D, 0.2D,
                0.05D,  Item.getIdFromItem(stack.getItem()), stack.getMetadata()
        );
    }

    public static int getIndexStackInVeilingTreat(ItemStack stack)
    {
        if (stack.isEmpty()) return -1;

        for (int i = 0; i < ConfigParser.veilingTreatItems.size(); i++)
        {
            ItemStack treat = ConfigParser.veilingTreatItems.get(i);

            if (treat.getItem() == stack.getItem() && treat.getMetadata() == stack.getMetadata())
            {
                return i;
            }
        }
        return -1;
    }

    public boolean canUseItem(AbstractVeiling veiling, ItemStack stack) { return veiling.getEatCooldown() <= 0; }
    public int getUseTime() { return 20; }
}
