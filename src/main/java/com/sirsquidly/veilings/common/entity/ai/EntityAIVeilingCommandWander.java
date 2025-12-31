package com.sirsquidly.veilings.common.entity.ai;

import com.google.common.base.Predicate;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCarpet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityAIVeilingCommandWander extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private final World world;
    private final double speed;
    protected final float probability;

    private int cooldownTimer = 0;
    private int currentActivity;
    private int activityTimer = 0;
    private Entity entityTarget;
    private BlockPos blockTarget = null;

    public EntityAIVeilingCommandWander(AbstractVeiling creatureIn, double speedIn)
    { this(creatureIn, speedIn, 0.001F); }

    public EntityAIVeilingCommandWander(AbstractVeiling veilingIn, double speedIn, float chance)
    {
        this.veiling = veilingIn;
        this.world = veilingIn.world;
        this.speed = speedIn;
        this.probability = chance;
    }

    public boolean shouldExecute()
    { return this.veiling.getCommandMode() == 1; }

    public void updateTask()
    {
        activityTimer--;
        cooldownTimer--;

        if (currentActivity != 0 && activityTimer <= 0)
        {
            currentActivity = 0;
            cooldownTimer = 120 + world.rand.nextInt(30);
        }

        if (cooldownTimer > 0)  return;

        switch (currentActivity)
        {
            case 0:
                veiling.setArmPose(AbstractVeiling.PoseBody.EMPTY);
                randomActivity();
                break;
            case 1:
                if (veiling.getNavigator().noPath())
                {
                    Vec3d target = RandomPositionGenerator.getLandPos(veiling, 8, 3);
                    if (target != null)
                    { veiling.getNavigator().tryMoveToXYZ(target.x, target.y, target.z, speed * 0.75D); }
                }
                break;
            case 2:
                veiling.setArmPose(AbstractVeiling.PoseBody.SITTING);
                veiling.getNavigator().clearPath();
                break;
            case 3:
                if (blockTarget != null)
                {
                    double distToBed = veiling.getDistanceSq(blockTarget);

                    if (distToBed > 0.05D)
                    {
                        veiling.getNavigator().tryMoveToXYZ(blockTarget.getX(), blockTarget.getY(), blockTarget.getZ(), speed * 0.6D);
                        break;
                    }
                    else
                    {
                        veiling.getNavigator().clearPath();
                        veiling.setArmPose(AbstractVeiling.PoseBody.SLEEPING);
                    }
                }


                veiling.setArmPose(AbstractVeiling.PoseBody.SLEEPING);
                veiling.getNavigator().clearPath();
                break;
            case 4:
                veiling.getLookHelper().setLookPositionWithEntity(entityTarget, 30F, 30F);

                double dist = veiling.getDistance(entityTarget);

                if (dist > 2.0D)
                { veiling.getNavigator().tryMoveToEntityLiving(entityTarget, speed * 0.75D); }
                else
                {
                    veiling.getNavigator().clearPath();

                    if (!world.isRemote && world.rand.nextInt(20) == 0) veiling.spawnOverheadParticle(EnumParticleTypes.NOTE, 1);
                }

                break;
        }
    }

    private void randomActivity()
    {
        double roll = world.rand.nextDouble();

        if (roll < 0.10)
        {
            currentActivity = 2;
            activityTimer = 60 + world.rand.nextInt(60);
            return;
        }
        if (roll < 0.70)
        {
            if (getNearestBed())
            {
                currentActivity = 3;
                activityTimer = 100 + world.rand.nextInt(80);
                return;
            }
            return;
        }
        if (roll < 0.80)
        {
            EntityLivingBase target = getNearbyEntity(AbstractVeiling.class, null);
            if (target != null)
            {
                currentActivity = 4;
                entityTarget = target;
                activityTimer = 80 + world.rand.nextInt(60);
                return;
            }
        }

        currentActivity = 1;
        activityTimer = 60 + world.rand.nextInt(40);
    }

    private boolean getNearestBed()
    {
        int maxRadiusXZ = 6;
        int maxRadiusY = 2;
        BlockPos bestBlock = null;
        double shortestDistance = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.getAllInBoxMutable( this.veiling.getPosition().add(-maxRadiusXZ, 0, -maxRadiusXZ), this.veiling.getPosition().add(maxRadiusXZ, maxRadiusY, maxRadiusXZ)))
        {
            if (shouldMoveTo(this.world.getBlockState(pos).getBlock()) && this.veiling.isWithinHomeDistanceFromPosition(pos))
            {
                double distanceSq = this.veiling.getDistanceSqToCenter(pos);

                if (distanceSq < shortestDistance && this.veiling.getNavigator().getPathToPos(pos) != null)
                {
                    shortestDistance = distanceSq;
                    bestBlock = pos.toImmutable();
                }
            }
        }
        if (bestBlock != null)
        {
            this.blockTarget = bestBlock;
            return true;
        }
        return false;
    }

    /** Find air above water */
    protected boolean shouldMoveTo(Block block)
    { return block instanceof BlockBed || block == Blocks.WOOL || block instanceof BlockCarpet; }

    public <T extends Entity> T getNearbyEntity(Class<T> entityClass, Predicate<T> filter)
    {
        List<T> nearby = world.getEntitiesWithinAABB( entityClass, veiling.getEntityBoundingBox().grow(6),
                entity -> entity != this.veiling && (filter == null || filter.test(entity)));
        if (nearby.isEmpty()) return null;

        return nearby.get(world.rand.nextInt(nearby.size()));
    }
}