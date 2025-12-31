package com.sirsquidly.veilings.util.veilingItemUse;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.List;

public class IVeilingUseThrowable implements IVeilingItemUse
{
    private SoundEvent throwSound;
    private int moodShift;

    public IVeilingUseThrowable(SoundEvent throwSoundIn, int moodShiftIn)
    {
        this.throwSound = throwSoundIn;
        this.moodShift = moodShiftIn;
    }

    public void onItemUse(AbstractVeiling veiling, ItemStack stack, int useTime) {}

    public void onItemUseFinish(AbstractVeiling veiling, ItemStack stack, int useTime)
    {
        Entity victim = this.getNearbyRandomEntity(veiling.world, veiling, 5);

        if (victim != null)
        {
            spawnThrowableAtVictim(getProjectileEntity(veiling.world, veiling, stack), veiling, victim);

            veiling.swingArm(EnumHand.MAIN_HAND);
            veiling.playSound(this.throwSound, 1.0F, 1.0F);

            veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
            veiling.shiftHappiness(this.moodShift);
            veiling.setPlayCooldown(200);
        }
    }

    public boolean canUseItem(AbstractVeiling veiling, ItemStack stack) { return veiling.getPlayCooldown() <= 0; }

    public int getUseTime() { return 20; }

    public boolean isSafeItem(AbstractVeiling veiling, ItemStack stack) { return false; }

    public IProjectile getProjectileEntity(World worldIn, AbstractVeiling veiling, ItemStack stackIn)
    { return null; }


    public Entity getNearbyRandomEntity(World world, AbstractVeiling veiling, double expandByRange)
    {
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, veiling.getEntityBoundingBox().grow(expandByRange), entity -> entity != veiling && entity.isEntityAlive());
        if (list.isEmpty()) return null;

        return list.get(world.rand.nextInt(list.size()));
    }

    public void spawnThrowableAtVictim(IProjectile projectile, AbstractVeiling veiling, Entity victim)
    {
        double s1 = victim.posY - veiling.posY;
        double s2 = victim.posX - veiling.posX;
        double s4 = victim.posZ - veiling.posZ;
        projectile.shoot(s2, s1, s4, 0.7F, 2.0F);
        veiling.world.spawnEntity((Entity)projectile);
    }
}