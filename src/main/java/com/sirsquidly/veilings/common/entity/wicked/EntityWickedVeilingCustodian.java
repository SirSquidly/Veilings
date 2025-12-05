package com.sirsquidly.veilings.common.entity.wicked;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingCustodian;
import com.sirsquidly.veilings.init.VeilingsLootTables;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityWickedVeilingCustodian extends AbstractWickedVeiling
{
    public EntityWickedVeilingCustodian(World worldIn)
    { super(worldIn); }

    public EntityAgeable createChild(EntityAgeable ageable) { return new EntityWickedVeilingCustodian(ageable.world); }
    public AbstractVeiling getInverse(AbstractVeiling thisVeiling) { return new EntityVeilingCustodian(thisVeiling.world); }
    protected ResourceLocation getLootTable()
    {
        return VeilingsLootTables.WICKED_VEILING_CUSTODIAN_DROPS;
    }
}