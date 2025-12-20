package com.sirsquidly.veilings.common.entity;

import com.sirsquidly.veilings.init.VeilingsSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.UUID;

// TODO: Move Damage to a value that is attached to the Dagger.
public class EntitySpiritDagger extends EntityThrowable
{
    private int warmupTicks;
    private int lifeTicks;
    protected EntityLivingBase target;
    private UUID targetUUID;

    private boolean appliesWeakness;

    private boolean storedInitialMotion;
    private double storedX;
    private double storedY;
    private double storedZ;
    private float storedPitch;
    private float storedYaw;

    public EntitySpiritDagger(World worldIn)
    { super(worldIn); }

    public EntitySpiritDagger(World worldIn, EntityLivingBase throwerIn, EntityLivingBase targetIn)
    { this(worldIn, throwerIn.posX, throwerIn.posY + (double)throwerIn.getEyeHeight() - 0.10000000149011612D, throwerIn.posZ, throwerIn, targetIn); }

    public EntitySpiritDagger(World worldIn, double x, double y, double z, EntityLivingBase throwerIn, EntityLivingBase targetIn)
    {
        this(worldIn);
        this.setPosition(x, y, z);
        this.thrower = throwerIn;
        this.ignoreEntity = throwerIn;
        if (targetIn != null)
        {
            this.target = targetIn;
            this.targetUUID = targetIn.getUniqueID();
        }
        this.warmupTicks = 10;
        this.lifeTicks = 20;
    }

    protected float getGravityVelocity()
    { return 0.0075F; }

    public void onUpdate()
    {
        super.onUpdate();

        if (!storedInitialMotion)
        {
            storedX = this.motionX;
            storedY = this.motionY;
            storedZ = this.motionZ;
            storedPitch = this.rotationPitch;
            storedYaw = this.rotationYaw;
            storedInitialMotion = true;
        }

        /* Seems to work when right here? */
        rotationYaw = prevRotationYaw = storedYaw;
        rotationPitch = prevRotationPitch = storedPitch;


        if (warmupTicks-- > 0)
        {
            rotationYaw = prevRotationYaw = storedYaw;
            rotationPitch = prevRotationPitch = storedPitch;
            motionX = motionY = motionZ = 0;
            noClip = true;
            onGround = false;
            return;
        }
        if (warmupTicks == -1)
        {
            motionX = storedX;
            motionY = storedY;
            motionZ = storedZ;
            playSound(VeilingsSounds.ITEM_SPIRIT_DAGGER_SHOOT, 1.0F, (this.rand.nextFloat() * 0.3F) + 0.9F);
            noClip = false;
            warmupTicks--;
        }

        this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);

        if (!world.isRemote)
        {
            if (--this.lifeTicks < 0)
            { this.setDead(); }
        }
    }


    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            Entity entity = result.entityHit;
            if (entity.equals(this.getThrower()) || this.world.isRemote) return;

            if (targetUUID == null || entity.getUniqueID().equals(targetUUID))
            {
                if (entity instanceof EntityTameable && ((EntityTameable)entity).getOwnerId() != null && ((EntityTameable)entity).getOwnerId().equals(this.getThrower().getUniqueID())) return;

                int i = 3;
                entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);

                if (this.appliesWeakness && entity instanceof EntityLivingBase)
                {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 3 * 20, 0));
                }

                this.setDead();
            }
        }
    }

    public void setAppliesWeakness(boolean weaknessIn) { this.appliesWeakness = weaknessIn; }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.appliesWeakness = compound.getBoolean("AppliesWeakness");
        this.targetUUID = compound.getUniqueId("TargetUUID");
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("AppliesWeakness", this.appliesWeakness);
        if (this.targetUUID != null)
        { compound.setUniqueId("TargetUUID", this.targetUUID);  }
    }
}