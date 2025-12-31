package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIVeilingDanceParty extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private final World world;

    private BlockPos jukeboxPosition = null;
    private int checkJukeboxRange = 4;
    /** Used to delay scans of `findNearbyJukebox`. */
    protected int checkDelay;
    /** How long this particular Veiling wants to Dance. */
    private int danceTimer;

    public EntityAIVeilingDanceParty(AbstractVeiling veilingIn)
    {
        this.veiling = veilingIn;
        this.world = veilingIn.world;
    }

    public boolean shouldExecute()
    {
        if (this.veiling.snowballFighting) return false;
        if (this.jukeboxPosition != null) return true;

        if (this.checkDelay > 0)
        {
            --this.checkDelay;
            return false;
        }

        findNearbyJukebox();
        this.checkDelay = 20 + world.rand.nextInt(20);
        return this.jukeboxPosition != null;
    }

    public boolean shouldContinueExecuting()
    { return this.jukeboxPosition != null && this.veiling.getDistanceSq(this.jukeboxPosition) < 40F && this.checkJukeboxHasDisc(this.jukeboxPosition); }

    public void startExecuting() { this.danceTimer = 600 + world.rand.nextInt(400); }

    public void resetTask()
    {
        if (this.veiling.getArmPose() == AbstractVeiling.PoseBody.DANCING) this.veiling.setArmPose(AbstractVeiling.PoseBody.EMPTY);
        this.jukeboxPosition = null;
        this.checkDelay = 20 + world.rand.nextInt(20);
    }

    public void updateTask()
    {
        --this.checkDelay;
        --this.danceTimer;

        this.veiling.setArmPose(AbstractVeiling.PoseBody.DANCING);

        if  (this.checkDelay <= 0)
        {
            veiling.spawnOverheadParticle(EnumParticleTypes.NOTE, 1);
            if (!checkJukeboxHasDisc(this.jukeboxPosition)) findNearbyJukebox();
            this.checkDelay = 20 + world.rand.nextInt(20);
        }

        if (this.danceTimer <= 0)
        {
            /* Mood Reward only occurs if they haven't played recently, else add to the delay recheck for the reward. */
            if (this.veiling.getPlayCooldown() <= 0)
            {
                this.veiling.shiftHappiness(40);
                this.veiling.setPlayCooldown(6000);
            }
            else this.danceTimer = 60 + world.rand.nextInt(300);
        }
    }

    public void findNearbyJukebox()
    {
        for (BlockPos pos : BlockPos.getAllInBoxMutable(this.veiling.getPosition().add(-checkJukeboxRange, -2, -checkJukeboxRange), this.veiling.getPosition().add(checkJukeboxRange, 2, checkJukeboxRange)))
        {
            if (this.world.getBlockState(pos).getBlock() == Blocks.JUKEBOX)
            {
                /* Only save Jukeboxes that are holding a Music Disc!*/
                if (!checkJukeboxHasDisc(pos)) continue;
                jukeboxPosition = pos;
                return;
            }
        }

        jukeboxPosition = null;
    }

    public boolean checkJukeboxHasDisc(BlockPos pos)
    {
        TileEntity tile = this.world.getTileEntity(pos);

        if (tile instanceof BlockJukebox.TileEntityJukebox)
        {
            BlockJukebox.TileEntityJukebox jukebox = (BlockJukebox.TileEntityJukebox) tile;
            return !jukebox.getRecord().isEmpty();
        }

        return false;
    }
}