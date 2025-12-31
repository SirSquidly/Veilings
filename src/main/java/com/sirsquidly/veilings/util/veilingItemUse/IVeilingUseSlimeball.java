package com.sirsquidly.veilings.util.veilingItemUse;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class IVeilingUseSlimeball implements IVeilingItemUse
{
    public void onItemUse(AbstractVeiling veiling, ItemStack stack, int useTime)
    {
        Random random = veiling.world.rand;

        if (useTime > 6 && random.nextInt(10) == 0)
        {
            if (veiling.onGround) veiling.motionY += random.nextFloat() * 0.5F;
            veiling.playSound(SoundEvents.ENTITY_SLIME_JUMP, 0.25F, (random.nextFloat() * 0.5F) + 1.7F);

            veiling.swingArm(EnumHand.values()[random.nextInt(2)]);

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
        veiling.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1.0F, 1.7F);
        ((WorldServer)veiling.world).spawnParticle(
                EnumParticleTypes.ITEM_CRACK,
                veiling.posX, veiling.posY + 0.2D, veiling.posZ,
                20,
                0.2D, 0.2D, 0.2D,
                0.05D,  Item.getIdFromItem(stack.getItem()), stack.getMetadata()
        );


        veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);

        veiling.shiftHappiness(20);
        veiling.setPlayCooldown(200);
    }

    public boolean canUseItem(AbstractVeiling veiling, ItemStack stack) { return veiling.getPlayCooldown() <= 0; }

    public int getUseTime() { return 60; }

    public boolean isSafeItem(AbstractVeiling veiling, ItemStack stack) { return true; }
}
