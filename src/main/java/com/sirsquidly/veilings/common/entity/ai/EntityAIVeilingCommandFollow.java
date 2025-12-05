package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIVeilingCommandFollow extends EntityAIFollowOwner
{
    private final AbstractVeiling veiling;

    public EntityAIVeilingCommandFollow(EntityTameable tameableIn, double followSpeedIn, float minDistIn, float maxDistIn)
    {
        super(tameableIn, followSpeedIn, minDistIn, maxDistIn);
        this.veiling = (AbstractVeiling)tameableIn;
    }

    public boolean shouldExecute()
    {
        if (this.veiling.getCommandMode() != 0) return false;
        return super.shouldExecute();
    }
}