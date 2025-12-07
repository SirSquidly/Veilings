package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.EntitySpiritDagger;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.init.VeilingsSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// TODO: Polish Retreat via a timer to prevent 'stopping just at the edge' repeatedly
public class EntityAIVeilingSpiritDagger extends EntityAIBase
{
    World world;
    protected AbstractVeiling veiling;
    double moveSpeed;
    double retreatSpeed;
    private EntityLivingBase attackTarget;

    private int rangedAttackTime;
    private int seeTime;
    private final float maxAttackDistance;
    private final float minAttackDistance;

    public EntityAIVeilingSpiritDagger(AbstractVeiling veilingIn, double speedIn, double retreatSpeedIn, float maxAttackDistanceIn, float minAttackDistanceIn)
    {
        this.veiling = veilingIn;
        this.world = veilingIn.world;
        this.retreatSpeed = speedIn;
        this.moveSpeed = retreatSpeedIn;
        this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
        this.minAttackDistance = minAttackDistanceIn * minAttackDistanceIn;
        this.setMutexBits(3);
    }

    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.veiling.getAttackTarget();

        if (entitylivingbase == null || !entitylivingbase.isEntityAlive())
        { return false; }
        else
        {
            this.attackTarget = entitylivingbase;
            return true;
        }
    }

    public boolean shouldContinueExecuting()
    { return this.shouldExecute() || !this.veiling.getNavigator().noPath(); }

    public void startExecuting()
    { this.rangedAttackTime = 20; }

    public void resetTask()
    {
        this.attackTarget = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
    }

    public void updateTask()
    {
        double distanceToTarget = this.veiling.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
        boolean flag = this.veiling.getEntitySenses().canSee(this.attackTarget);

        if (flag)
        { ++this.seeTime; }
        else
        { this.seeTime = 0; }

        if (distanceToTarget <= (double)this.maxAttackDistance && this.seeTime >= 20)
        {
            if (distanceToTarget <= (double)this.minAttackDistance)
            {
                Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.veiling, 16, 7, new Vec3d(this.attackTarget.posX, this.attackTarget.posY, this.attackTarget.posZ));

                if (vec3d != null) this.veiling.getNavigator().setPath(this.veiling.getNavigator().getPathToXYZ(vec3d.x, vec3d.y, vec3d.z), this.retreatSpeed);
            }
            else this.veiling.getNavigator().clearPath();
        }
        else
        { this.veiling.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.moveSpeed); }

        this.veiling.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);

        if (--this.rangedAttackTime <= 0)
        {
            if (!flag)
            { return; }

            spawnSpiritDagger();
            this.rangedAttackTime = 20;
        }
    }

    public void spawnSpiritDagger()
    {
        EntitySpiritDagger dagger = new EntitySpiritDagger(world, veiling, attackTarget);

        double s1 = attackTarget.posY - (veiling.posY + 0.5);
        double s2 = attackTarget.posX - veiling.posX;
        double s4 = attackTarget.posZ - veiling.posZ;
        dagger.shoot(s2, s1, s4, 0.9F, 1.0F);

        double forward = 0.75;
        dagger.setPosition(veiling.posX, veiling.posY + (double)veiling.getEyeHeight() - 0.10000000149011612D + 0.5, veiling.posZ);

        this.veiling.playSound(VeilingsSounds.ENTITY_VEILING_SHOOT, 0.7F, (this.veiling.world.rand.nextFloat() * 0.3F) + 0.9F);
        this.veiling.world.spawnEntity(dagger);
        this.veiling.swingArm(EnumHand.MAIN_HAND);
        //this.veiling.playSound(event, 1.0F, 1.0F);
    }
}