package com.sirsquidly.veilings.common.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.sirsquidly.veilings.client.model.ModelVeilingBase;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;


public class EntityAIVeilingFearMob<T extends Entity> extends EntityAIAvoidEntity
{
    AbstractVeiling veiling;

    public EntityAIVeilingFearMob(EntityCreature entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn)
    { this(entityIn, classToAvoidIn, Predicates.alwaysTrue(), avoidDistanceIn, farSpeedIn, nearSpeedIn); }

    public EntityAIVeilingFearMob(EntityCreature entityIn, Class<T> classToAvoidIn, Predicate<? super T > avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn)
    {
        super(entityIn, classToAvoidIn, avoidTargetSelectorIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
        veiling = (AbstractVeiling)entityIn;
    }

    public void resetTask()
    {
        super.resetTask();
        if (this.veiling.getArmPose() == ModelVeilingBase.PoseBody.CRYING) this.veiling.setArmPose(ModelVeilingBase.PoseBody.EMPTY);
    }

    public void updateTask()
    {
        super.updateTask();

        if (this.entity.ticksExisted % 10 != 0) return;

        if (this.entity.getDistanceSq(this.closestLivingEntity) < 49.0D)
        {
            veiling.shiftHappiness(-1);
            veiling.setArmPose(ModelVeilingBase.PoseBody.CRYING);
            veiling.playSound(veiling.getPanicSound(), 1.0F, veiling.getPublicSoundPitch());
        }
    }
}
