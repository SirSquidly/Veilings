package com.sirsquidly.veilings.util.veilingItemUse;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;

import java.util.UUID;

public class IVeilingUseAttributeModifier implements IVeilingItemUse
{
    private String name;
    private float value;

    public IVeilingUseAttributeModifier(String nameIn, float valueIn)
    {
        this.name = nameIn;
        this.value = valueIn;
    }

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
        veiling.spawnOverheadParticle(EnumParticleTypes.HEART, 1);

        veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);

        if (requiredCondition(veiling, stack))
        { veiling.applyUpgrade(name, value); }
        else
        { conditionTriggerFalse(veiling, stack); }

        veiling.heal(4);
        veiling.shiftHappiness(10);
        veiling.playSound(veiling.getEatSound(), 1.0F, veiling.getPublicSoundPitch());
        veiling.setEatCooldown(600);


        ((WorldServer)veiling.world).spawnParticle(
                EnumParticleTypes.ITEM_CRACK,
                veiling.posX, veiling.posY + 0.2D, veiling.posZ,
                10,
                0.2D, 0.2D, 0.2D,
                0.05D,  Item.getIdFromItem(stack.getItem()), stack.getMetadata()
        );
    }

    public int getUseTime() { return 60; }

    public boolean isSafeItem(AbstractVeiling veiling, ItemStack stack) { return false; }

    public boolean canUseItem(AbstractVeiling veiling, ItemStack stack) { return veiling.getEatCooldown() <= 0; }

    public boolean requiredCondition(AbstractVeiling veiling, ItemStack stack)
    { return true; }

    public void conditionTriggerFalse(AbstractVeiling veiling, ItemStack stack) {}
}
