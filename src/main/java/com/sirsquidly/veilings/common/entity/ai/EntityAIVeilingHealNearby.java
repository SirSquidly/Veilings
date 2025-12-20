package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.client.model.ModelVeilingBase;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.item.ItemVeilingMask;
import com.sirsquidly.veilings.init.VeilingsSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Comparator;
import java.util.List;

/**
* Simply lowers happiness
* */
public class EntityAIVeilingHealNearby extends EntityAIBase
{
    private final AbstractVeiling veiling;
    private final World world;
    protected int runDelay;

    private EntityLivingBase veilingPatient;
    private int healingTime;

    public EntityAIVeilingHealNearby(AbstractVeiling veilingIn)
    {
        this.veiling = veilingIn;
        this.world = veilingIn.world;
    }

    public boolean shouldExecute()
    {
        if (this.runDelay > 0)
        {
            this.runDelay--;
            return false;
        }

        if (this.veilingPatient != null) return true;

        /* Custodians prioritize healing their owner. */
        if (this.veiling.getOwnerId() != null)
        {
            EntityPlayer owner = this.world.getPlayerEntityByUUID(this.veiling.getOwnerId());
            if (owner != null  && owner.isEntityAlive() && owner.getHealth() < owner.getMaxHealth() && owner.getDistance(this.veiling) <= 8.0D)
            {
                Item headSlot = owner.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
                if (headSlot instanceof ItemVeilingMask && ((ItemVeilingMask)headSlot).getMaskType() == 1)
                {
                    this.veilingPatient = owner;
                    return true;
                }
            }
        }

        List<AbstractVeiling> nearbyVeilings = world.getEntitiesWithinAABB(AbstractVeiling.class, veiling.getEntityBoundingBox().grow(6),
                entity -> entity != this.veiling && (this.veiling.getOwnerId() != null && entity.getOwnerId() != null && entity.getOwnerId().equals(this.veiling.getOwnerId())));
        if (nearbyVeilings.isEmpty()) return false;
        nearbyVeilings.sort(Comparator.comparing(AbstractVeiling::getHealth));

        this.veilingPatient = nearbyVeilings.get(0);
        return true;
    }

    public boolean shouldContinueExecuting()
    { return this.veilingPatient != null; }

    public void resetTask()
    {
        this.veilingPatient = null;
        this.healingTime = 0;
        if (this.veiling.getArmPose() == ModelVeilingBase.PoseBody.CASTING) this.veiling.setArmPose(ModelVeilingBase.PoseBody.EMPTY);
    }

    public void updateTask()
    {
        if (this.veilingPatient == null) return;

        if (!this.veilingPatient.isEntityAlive() || this.veilingPatient.getHealth() >= this.veilingPatient.getMaxHealth())
        {
            this.veilingPatient = null;
            runDelay = 10;
            return;
        }

        this.veiling.getLookHelper().setLookPositionWithEntity(this.veilingPatient, 30F, 30F);
        this.veiling.setArmPose(ModelVeilingBase.PoseBody.CASTING);


        float f = this.veiling.renderYawOffset * 0.017453292F + MathHelper.cos((float)this.veiling.ticksExisted * 0.6662F) * 0.25F;
        float f1 = MathHelper.cos(f);
        float f2 = MathHelper.sin(f);
        ((WorldServer)world).spawnParticle(EnumParticleTypes.SPELL_WITCH,
                this.veiling.posX + (double)f1 * 0.4D, this.veiling.posY + 1.3D, this.veiling.posZ + (double)f2 * 0.4D,
                1,
                0, 0, 0,
                0D
        );
        ((WorldServer)world).spawnParticle(EnumParticleTypes.SPELL_WITCH,
                this.veiling.posX - (double)f1 * 0.4D, this.veiling.posY + 1.3D, this.veiling.posZ - (double)f2 * 0.4D,
                1,
                0, 0, 0,
                0D
        );


        if (++healingTime >= 30)
        {
            this.veilingPatient.heal(4);

            /* Rewards the Custodian with Absorption if they healed their owner. */
            if (this.veilingPatient instanceof EntityPlayer)
            { this.veiling.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 180 * 20, 0)); }

            ((WorldServer)world).spawnParticle(EnumParticleTypes.SPELL_WITCH,
                    this.veilingPatient.posX, this.veilingPatient.posY + (this.veilingPatient.height * 0.5D), this.veilingPatient.posZ,
                    10,
                    0.1D, this.veilingPatient.height * 0.25D, 0.1D,
                    0.05D
            );

            world.playSound(null, this.veilingPatient.getPosition(), VeilingsSounds.ITEM_ATTACK_SCEPTRE_USE, SoundCategory.NEUTRAL, 1.0F, 0.2F);
            this.veilingPatient = null;
            this.healingTime = 0;

            runDelay = 20 + world.rand.nextInt(60);
        }
    }
}