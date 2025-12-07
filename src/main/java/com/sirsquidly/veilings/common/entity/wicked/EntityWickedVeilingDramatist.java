package com.sirsquidly.veilings.common.entity.wicked;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingDramatist;
import com.sirsquidly.veilings.common.entity.ai.EntityAIVeilingSpiritDagger;
import com.sirsquidly.veilings.init.VeilingsLootTables;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityWickedVeilingDramatist extends AbstractWickedVeiling
{
    public EntityWickedVeilingDramatist(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.4F);
    }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIVeilingSpiritDagger(this, 1.0D, 1.2D, 18, 6));
    }

    public EntityAgeable createChild(EntityAgeable ageable) { return new EntityWickedVeilingDramatist(ageable.world); }
    public AbstractVeiling getInverse(AbstractVeiling thisVeiling) { return new EntityVeilingDramatist(thisVeiling.world); }
    protected ResourceLocation getLootTable()
    {
        return VeilingsLootTables.WICKED_VEILING_DRAMATIST_DROPS;
    }
}