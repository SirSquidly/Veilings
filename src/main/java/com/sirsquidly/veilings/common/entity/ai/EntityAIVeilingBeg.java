package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.util.veilingItemUse.IVeilingItemUse;
import com.sirsquidly.veilings.util.veilingItemUse.VeilingItemUseRegistry;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityAIVeilingBeg extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private EntityPlayer player;
    private final World world;
    private final float minPlayerDistance;
    private int timeoutCounter;

    public EntityAIVeilingBeg(AbstractVeiling veilingIn, float minDistance)
    {
        this.veiling = veilingIn;
        this.world = veilingIn.world;
        this.minPlayerDistance = minDistance;
        this.setMutexBits(2);
    }

    public boolean shouldExecute()
    {
        this.player = this.world.getClosestPlayerToEntity(this.veiling, this.minPlayerDistance);
        return this.player == null ? false : this.hasTemptationItemInHand(this.player) && this.veiling.getArmPose() == AbstractVeiling.PoseBody.EMPTY;
    }

    public boolean shouldContinueExecuting()
    {
        if (this.player == null || !this.player.isEntityAlive())
        { return false; }
        else if (this.veiling.getDistanceSq(this.player) > (double)(this.minPlayerDistance * this.minPlayerDistance))
        { return false; }
        else
        { return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.player); }
    }

    public void startExecuting()
    {
        this.veiling.setArmPose(AbstractVeiling.PoseBody.BEGGING);
        this.timeoutCounter = 40 + this.veiling.getRNG().nextInt(40);
    }

    public void resetTask()
    {
        if (this.veiling.getArmPose() == AbstractVeiling.PoseBody.BEGGING) this.veiling.setArmPose(AbstractVeiling.PoseBody.EMPTY);
        this.player = null;
    }

    public void updateTask()
    {
        ItemStack held = this.veiling.getHeldItemMainhand();
        if (!held.isEmpty() && VeilingItemUseRegistry.get(held.getItem()) != null)
        {
            this.resetTask();
            return;
        }

        this.veiling.getLookHelper().setLookPosition(this.player.posX, this.player.posY + (double)this.player.getEyeHeight(), this.player.posZ, 10.0F, (float)this.veiling.getVerticalFaceSpeed());
        --this.timeoutCounter;
    }

    private boolean hasTemptationItemInHand(EntityPlayer player)
    {
        for (EnumHand enumhand : EnumHand.values())
        {
            ItemStack itemstack = player.getHeldItem(enumhand);

            if (!itemstack.isEmpty())
            {
                IVeilingItemUse cachedBehavior = VeilingItemUseRegistry.get(itemstack.getItem());

                if (cachedBehavior != null)
                {
                    return cachedBehavior.canUseItem(this.veiling, itemstack);
                }

                return false;
            }
        }

        return false;
    }
}