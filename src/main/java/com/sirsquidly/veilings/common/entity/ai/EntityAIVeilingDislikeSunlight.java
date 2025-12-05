package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

/**
* Simply lowers happiness
* */
public class EntityAIVeilingDislikeSunlight extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private final World world;
    protected int runDelay;

    public EntityAIVeilingDislikeSunlight(AbstractVeiling veilingIn)
    {
        this.veiling = veilingIn;
        this.world = veilingIn.world;
    }

    public boolean shouldExecute()
    {
        if (this.runDelay > 0)
        {
            this.runDelay--;
            return false;
        }
        return true;
    }

    public void startExecuting()
    {
        if (this.world.isDaytime() && !this.veiling.isChild())
        {
            float f = this.veiling.getBrightness();

            if (f > 0.5F && this.world.canSeeSky(this.veiling.getPosition().up((int)this.veiling.getEyeHeight())))
            { this.veiling.shiftHappiness(-1); }
        }

        this.runDelay = 20 + this.world.rand.nextInt(60);
    }
}