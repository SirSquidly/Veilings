package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.client.model.ModelVeilingBase;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.wicked.AbstractWickedVeiling;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.List;

// TODO: Cancel harassment if they have an attack target!
public class EntityAIWickedVeilingHarass extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private final World world;
    protected int runDelay;

    private AbstractVeiling veilingVictim;

    public EntityAIWickedVeilingHarass(AbstractVeiling veilingIn)
    {
        this.veiling = veilingIn;
        this.world = veilingIn.world;
        this.setMutexBits(3);
    }

    public boolean shouldExecute()
    {
        if (this.runDelay > 0)
        {
            --this.runDelay;
            return false;
        }
        runDelay = 20 + world.rand.nextInt(60);
        if (this.veilingVictim != null) return true;

        List<AbstractVeiling> nearbyNormalVeilings = world.getEntitiesWithinAABB(AbstractVeiling.class, veiling.getEntityBoundingBox().grow(6),
                entity -> entity != this.veiling && !(entity instanceof AbstractWickedVeiling));
        if (nearbyNormalVeilings.isEmpty()) return false;

        this.veilingVictim = nearbyNormalVeilings.get(world.rand.nextInt(nearbyNormalVeilings.size()));
        return true;
    }

    public boolean shouldContinueExecuting()
    { return this.veilingVictim != null; }

    public void resetTask()
    {
        this.veilingVictim = null;
        if (this.veiling.getArmPose() == ModelVeilingBase.PoseBody.CASTING) this.veiling.setArmPose(ModelVeilingBase.PoseBody.EMPTY);
    }

    public void updateTask()
    {

        if (this.veilingVictim == null) return;

        if (!this.veilingVictim.isEntityAlive())
        {
            this.veilingVictim = null;
            runDelay = 10;
            return;
        }

        this.veiling.getLookHelper().setLookPositionWithEntity(this.veilingVictim, 30F, 30F);
        double dist = veiling.getDistance(this.veilingVictim);

        if (dist > 3.0D)
        {
            veiling.getNavigator().tryMoveToEntityLiving(this.veilingVictim, 1.1D);
            if (this.veiling.getArmPose() == ModelVeilingBase.PoseBody.CASTING) this.veiling.setArmPose(ModelVeilingBase.PoseBody.EMPTY);
        }
        else
        {
            veiling.getNavigator().clearPath();
            this.veiling.setArmPose(ModelVeilingBase.PoseBody.CASTING);

            if (veiling.onGround) veiling.motionY += world.rand.nextFloat() * 0.5F;

            if (!world.isRemote && world.rand.nextInt(10) == 0) veiling.spawnOverheadParticle(EnumParticleTypes.FLAME, 2);
        }
    }
}
