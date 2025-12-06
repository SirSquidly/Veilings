package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.util.veilingItemUse.IVeilingItemUse;
import com.sirsquidly.veilings.util.veilingItemUse.VeilingItemUseRegistry;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// TODO: Lower item discard chance.
public class EntityAIVeilingUseItem extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private final World world;
    public int useTime;

    private ItemStack cachedStack;
    private IVeilingItemUse cachedBehavior;

    public EntityAIVeilingUseItem(AbstractVeiling veilingIn)
    {
        this.veiling = veilingIn;
        this.world = veilingIn.world;
    }

    public boolean shouldExecute()
    {
        if (this.veiling.getHeldItemMainhand().isEmpty() || this.veiling.snowballFighting) return false;

        if (cachedBehavior == null)
        {
            ItemStack heldItemStack = this.veiling.getHeldItemMainhand();
            cachedBehavior = VeilingItemUseRegistry.get(heldItemStack.getItem());

            if (cachedBehavior != null)
            {
                /* Item must be currently usable (such as food having the eating cooldown for the Veiling itself)*/
                if (!cachedBehavior.canUseItem(this.veiling, heldItemStack)) return false;

                this.useTime = cachedBehavior.getUseTime();
                cachedStack = heldItemStack;
            }
        }
        return cachedBehavior != null;
    }

    public void resetTask()
    {
        this.useTime = 0;
        this.cachedBehavior = null;
        this.cachedStack = null;
    }

    public void updateTask()
    {
        --this.useTime;

        ItemStack heldItem = this.veiling.getHeldItemMainhand();

        if (heldItem != cachedStack)
        {
            this.cachedBehavior = null;
            return;
        }

        if (this.world.rand.nextInt(600) < 50 - this.veiling.getMood() && false)
        {
            this.veiling.entityDropItem(heldItem.copy(), 1);
            this.veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
            this.resetTask();
            return;
        }

        if (this.useTime <= 0)
        { cachedBehavior.onItemUseFinish(this.veiling, heldItem, this.useTime); }
        else
        { cachedBehavior.onItemUse(this.veiling, heldItem, this.useTime); }

    }
}