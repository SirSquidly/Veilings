package com.sirsquidly.veilings.common.entity.wicked;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingDeft;
import com.sirsquidly.veilings.init.VeilingsLootTables;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityWickedVeilingDeft extends AbstractWickedVeiling
{
    public EntityWickedVeilingDeft(World worldIn)
    { super(worldIn); }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
    }

    public EntityAgeable createChild(EntityAgeable ageable) { return new EntityWickedVeilingDeft(ageable.world); }
    public AbstractVeiling getInverse(AbstractVeiling thisVeiling) { return new EntityVeilingDeft(thisVeiling.world); }
    protected ResourceLocation getLootTable()
    {
        return VeilingsLootTables.WICKED_VEILING_DEFT_DROPS;
    }
}