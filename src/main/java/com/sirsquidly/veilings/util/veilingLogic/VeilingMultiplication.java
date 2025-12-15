package com.sirsquidly.veilings.util.veilingLogic;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.wicked.AbstractWickedVeiling;
import com.sirsquidly.veilings.config.ConfigCache;
import com.sirsquidly.veilings.config.ConfigParser;
import com.sirsquidly.veilings.init.VeilingsItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.UUID;

public class VeilingMultiplication
{

    public void tick(AbstractVeiling veiling)
    {
        if (veiling.world.isRemote || !ConfigCache.mlt_mltEnb) return;

        if (veiling.getSpawnCooldown() > 0) veiling.setSpawnCooldown(veiling.getSpawnCooldown() - 1);
        else if (veiling.isWet())
        {
            if (ConfigCache.mlt_spnEpo)
            {
                boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(veiling.world, veiling);
                veiling.world.createExplosion(veiling, veiling.posX, veiling.posY, veiling.posZ, 0.5F, flag);
            }

            ((WorldServer)veiling.world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, veiling.posX, veiling.posY, veiling.posZ,
                    200, 0.25D, 0.25D, 0.25D, 0.05D);

            AbstractVeiling newVeiling = veiling.multiplyLogic.generateNewVeiling(veiling.world, veiling);

            veiling.shiftHappiness(ConfigCache.mod_mltSft);
            newVeiling.setMood(ConfigCache.mlt_irtPntMod ? veiling.getMood() : 100);

            newVeiling.setPosition(veiling.posX, veiling.posY, veiling.posZ);
            veiling.world.spawnEntity(newVeiling);

            /* Wicked drop no Essence when multiplying. */
            if (!(veiling instanceof AbstractWickedVeiling))
            {
                this.imprintOnNearestPlayer(newVeiling);
                veiling.dropItem(VeilingsItems.VEILING_ESSENCE, 1);
            }

            veiling.setSpawnCooldown(veiling.multiplyCooldown);
            veiling.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0F, 1.0F);
        }
    }

    public void imprintOnNearestPlayer(AbstractVeiling veiling)
    {
        boolean happyBaby = true;
        EntityPlayer player = veiling.world.getClosestPlayerToEntity(veiling, 10);

        if (player == null)
        {
            /* You did this, what is wrong with you. */
            happyBaby = false;
            player = veiling.world.getClosestPlayerToEntity(veiling, 100);
        }

        if (!(player == null))
        {
            veiling.setTamedBy(player);
            if (happyBaby)
            {
                veiling.spawnOverheadParticle(EnumParticleTypes.HEART, 7);
                veiling.world.setEntityState(veiling, (byte)7);
            }
            else
            {
                veiling.shiftHappiness(-30);
            }
        }
    }


    /**
     * This is used to set up a new Veiling
     * `baseVeiling` - The Veiling to parent from, inheriting the type via `createChild`.
     * */
    public AbstractVeiling generateNewVeiling(World world, AbstractVeiling baseVeiling)
    {
        AbstractVeiling newVeiling = (AbstractVeiling)baseVeiling.createChild(baseVeiling);

        newVeiling.setSpawnCooldown(3000);

        /* Get a RANDOM food to be their favorite. */
        newVeiling.setEyeColor(world.rand.nextInt(5));
        newVeiling.favoriteFood = ConfigParser.veilingTreatItems.get(world.rand.nextInt(ConfigParser.veilingTreatItems.size()));
        newVeiling.setDanceType(world.rand.nextInt(8));
        newVeiling.setSitType(world.rand.nextInt(8));
        return newVeiling;
    }

    /**
     * This is used to set up a new Veiling
     * `baseVeiling` - The Veiling being transformed, for the new one it inherit from.
     * `transformInto` - The Veiling that is resulting from this method.
     * */
    public void transformVeiling(World world, AbstractVeiling baseVeiling, AbstractVeiling transformInto)
    {
        ((WorldServer)world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, baseVeiling.posX, baseVeiling.posY + (baseVeiling.height * 0.5), baseVeiling.posZ,
                200, 0.25D, 0.5D, 0.25D, 0.05D);

        transformInto.setLocationAndAngles(baseVeiling.posX, baseVeiling.posY, baseVeiling.posZ, baseVeiling.rotationYaw, baseVeiling.rotationPitch);
        transformInto.prevRotationYaw = baseVeiling.prevRotationYaw;
        transformInto.prevRotationYawHead = baseVeiling.prevRotationYawHead;
        transformInto.prevRotationPitch = baseVeiling.prevRotationPitch;

        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) { transformInto.setItemStackToSlot(slot, baseVeiling.getItemStackFromSlot(slot)); }

        transformInto.setLeftHanded(baseVeiling.isLeftHanded());
        transformInto.setMood(baseVeiling.getMood());
        transformInto.setPlayCooldown(baseVeiling.getPlayCooldown());
        transformInto.setSpawnCooldown(baseVeiling.getSpawnCooldown());
        transformInto.setEatCooldown(baseVeiling.getEatCooldown());
        transformInto.setEyeColor(baseVeiling.getEyeColor());
        transformInto.favoriteFood = baseVeiling.getFavoriteFood();
        transformInto.setDanceType(baseVeiling.getDanceType());
        transformInto.setBodyOutfit(baseVeiling.getBodyOutfit());
        transformInto.copyAttributesFrom(baseVeiling);

        boolean fromWicked = baseVeiling instanceof AbstractWickedVeiling;
        boolean toWicked = transformInto instanceof AbstractWickedVeiling;

        UUID ownerUUID = fromWicked ? ((AbstractWickedVeiling)baseVeiling).getOldOwnerId() : baseVeiling.getOwnerId();

        if (toWicked)
        {
            ((AbstractWickedVeiling)transformInto).setOldOwnerId(ownerUUID);
            transformInto.setMood(100);
        }
        else
        { transformInto.setOwnerId(ownerUUID); }

        if (baseVeiling.hasCustomName())
        {
            transformInto.setCustomNameTag(baseVeiling.getCustomNameTag());
            transformInto.setAlwaysRenderNameTag(baseVeiling.getAlwaysRenderNameTag());
        }

        world.spawnEntity(transformInto);
        baseVeiling.setDead();
    }
}