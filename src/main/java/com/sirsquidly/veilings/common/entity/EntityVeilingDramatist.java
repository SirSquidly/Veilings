package com.sirsquidly.veilings.common.entity;

import com.sirsquidly.veilings.common.entity.ai.EntityAIVeilingSpiritDagger;
import com.sirsquidly.veilings.common.entity.wicked.EntityWickedVeilingDramatist;
import com.sirsquidly.veilings.init.VeilingsLootTables;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityVeilingDramatist extends AbstractVeiling
{
    public EntityVeilingDramatist(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.4F);
    }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIVeilingSpiritDagger(this, 1.0D, 20, 20));
    }

    public EntityAgeable createChild(EntityAgeable ageable) { return new EntityVeilingDramatist(ageable.world); }
    public AbstractVeiling getInverse(AbstractVeiling thisVeiling) { return new EntityWickedVeilingDramatist(thisVeiling.world); }
    protected ResourceLocation getLootTable()
    {
        return VeilingsLootTables.VEILING_DRAMATIST_DROPS;
    }
}