package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIVeilingCommandSit extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private int animationCooldown = 0;

    public EntityAIVeilingCommandSit(AbstractVeiling veilingIn)
    {
        this.veiling = veilingIn;
        this.setMutexBits(5);
    }

    public boolean shouldExecute()
    {
        if (this.veiling.getCommandMode() != 2 || !this.veiling.isTamed() || this.veiling.isInWater() || !this.veiling.onGround || this.veiling.getAttackTarget() != null)
        { return false; }
        else
        {
            EntityLivingBase entitylivingbase = this.veiling.getOwner();

            if (entitylivingbase == null)
            { return true; }
            else
            { return !(this.veiling.getDistanceSq(entitylivingbase) < 144.0D) || entitylivingbase.getRevengeTarget() == null; }
        }
    }

    public void startExecuting()
    { this.animationCooldown = 120 + this.veiling.world.rand.nextInt(600); }

    public void updateTask()
    {
        if (this.veiling.getNavigator().noPath())
        {
            animationCooldown--;

            if (animationCooldown <= 0)
            {
                if (this.veiling.getArmPose() == AbstractVeiling.PoseBody.EMPTY) this.veiling.setArmPose(AbstractVeiling.PoseBody.SITTING);
                else this.veiling.setArmPose(AbstractVeiling.PoseBody.EMPTY);

                this.animationCooldown = 120 + this.veiling.world.rand.nextInt(600);
            }
        }
        /* If they are moving, then stop sitting! */
        else
        { if (this.veiling.getArmPose() == AbstractVeiling.PoseBody.SITTING) this.veiling.setArmPose(AbstractVeiling.PoseBody.EMPTY); }
    }

    public void resetTask()
    { if (this.veiling.getArmPose() == AbstractVeiling.PoseBody.SITTING) this.veiling.setArmPose(AbstractVeiling.PoseBody.EMPTY); }
}