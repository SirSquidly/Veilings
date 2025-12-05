package com.sirsquidly.veilings.common.entity.wicked;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.ai.EntityAIVeilingCommandFollow;
import com.sirsquidly.veilings.common.entity.ai.EntityAIVeilingCommandSit;
import com.sirsquidly.veilings.common.entity.ai.EntityAIVeilingSnowballFight;
import com.sirsquidly.veilings.common.entity.ai.EntityAIVeilingUseItem;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
* An inherited class of all types of Veiling.
* */
public class AbstractWickedVeiling extends AbstractVeiling
{
    public int multiplyCooldown = 6000;

    public AbstractWickedVeiling(World worldIn)
    { super(worldIn); }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIVeilingCommandSit(this));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIVeilingCommandFollow(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.4D, 10.0F, 3.0F));
        this.tasks.addTask(7, new EntityAIVeilingUseItem(this));
        this.tasks.addTask(7, new EntityAIVeilingSnowballFight(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + (this.isChild() ? 0.5F : 0);
    }


    public void shiftHappiness(int shiftBy) {}

    /** Wicked Veilings don't use the Happiness System. */
    public void shiftHappiness(int shiftBy, boolean clamped) {}
}