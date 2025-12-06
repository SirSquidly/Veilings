package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.util.veilingItemUse.IVeilingItemUse;
import com.sirsquidly.veilings.util.veilingItemUse.VeilingItemUseRegistry;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;


public class EntityAIWickedVeilingGetItem extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private final World world;
    protected int runDelay;

    private EntityItem groundItem;

    public EntityAIWickedVeilingGetItem(AbstractVeiling veilingIn)
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
        if (this.groundItem != null) return true;

        List<EntityItem> nearbyItems = world.getEntitiesWithinAABB(EntityItem.class, veiling.getEntityBoundingBox().grow(6),
                entity -> desirableItem(entity.getItem()) && this.veiling.canEntityBeSeen(entity));
        if (nearbyItems.isEmpty()) return false;

        this.groundItem = nearbyItems.get(world.rand.nextInt(nearbyItems.size()));
        return true;
    }

    public boolean shouldContinueExecuting()
    { return this.groundItem != null; }

    public void resetTask()
    {
        this.groundItem = null;
    }

    public void updateTask()
    {
        if (this.groundItem == null) return;

        if (!this.groundItem.isEntityAlive())
        {
            this.groundItem = null;
            runDelay = 10;
            return;
        }

        this.veiling.getLookHelper().setLookPositionWithEntity(this.groundItem, 30F, 30F);
        double dist = veiling.getDistance(this.groundItem);

        if (dist > 1.0D)
        {
            veiling.getNavigator().tryMoveToEntityLiving(this.groundItem, 1.0D);
        }
        else
        {
            veiling.getNavigator().clearPath();

            if (this.groundItem.getAge() > 20)
            {
                veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, this.groundItem.getItem());
                int itemnumber = this.groundItem.getItem().getCount();

                if (itemnumber - 1 == 0)
                { this.groundItem.setDead(); }
                else
                { this.groundItem.getItem().setCount(itemnumber - 1); }

                runDelay = 20 + world.rand.nextInt(60);
                resetTask();
            }
        }
    }

    public boolean desirableItem(ItemStack stack)
    {
        IVeilingItemUse cachedBehavior = VeilingItemUseRegistry.get(stack.getItem());

        if (cachedBehavior != null)
        { return cachedBehavior.canUseItem(this.veiling, stack); }

        return false;
    }
}
