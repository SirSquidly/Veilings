package com.sirsquidly.veilings.common.entity.wicked;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingCustodian;
import com.sirsquidly.veilings.common.entity.ai.EntityAIVeilingHealNearby;
import com.sirsquidly.veilings.init.VeilingsLootTables;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityWickedVeilingCustodian extends AbstractWickedVeiling
{
    public EntityWickedVeilingCustodian(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.25F);
    }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(1, new EntityAIVeilingHealNearby(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    public EntityAgeable createChild(EntityAgeable ageable) { return new EntityWickedVeilingCustodian(ageable.world); }
    public AbstractVeiling getInverse(AbstractVeiling thisVeiling) { return new EntityVeilingCustodian(thisVeiling.world); }
    protected ResourceLocation getLootTable()
    {
        return VeilingsLootTables.WICKED_VEILING_CUSTODIAN_DROPS;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        for (AbstractVeiling veiling : world.getEntitiesWithinAABB(AbstractVeiling.class, this.getEntityBoundingBox().grow(6),
                entity -> entity != this && (entity instanceof AbstractWickedVeiling || this.getOwnerId() != null && entity.getOwnerId() != null && entity.getOwnerId().equals(this.getOwnerId()))))
        {
            veiling.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 5 * 20, 0, true, true));
        }
    }
}