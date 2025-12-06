package com.sirsquidly.veilings.common.entity.wicked;

import com.google.common.base.Optional;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.ai.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

/**
* An inherited class of all types of Veiling.
* */
public class AbstractWickedVeiling extends AbstractVeiling
{
    protected static final DataParameter<Optional<UUID>> OLD_OWNER_UUID = EntityDataManager.<Optional<UUID>>createKey(AbstractWickedVeiling.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    public int multiplyCooldown = 6000;

    public AbstractWickedVeiling(World worldIn)
    { super(worldIn); }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(OLD_OWNER_UUID, Optional.absent());
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIVeilingCommandSit(this));
        this.tasks.addTask(3, new EntityAIVeilingDislikeSunlight(this));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(3, new EntityAIWickedVeilingHarass(this));
        this.tasks.addTask(3, new EntityAIWickedVeilingGetItem(this));
        this.tasks.addTask(6, new EntityAIVeilingCommandWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIVeilingCommandFollow(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIVeilingUseItem(this));
        this.tasks.addTask(7, new EntityAIVeilingSnowballFight(this));
        this.tasks.addTask(8, new EntityAIVeilingDanceParty(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    /** Very little Experience, since they are still pretty weak. */
    protected int getExperiencePoints(EntityPlayer player) { return 1 + this.world.rand.nextInt(3); }

    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + (this.isChild() ? 0.5F : 0);
    }


    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        this.setCommandMode(1);
        return livingdata;
    }



    public void shiftHappiness(int shiftBy) {}

    /** Wicked Veilings don't use the Happiness System. */
    public void shiftHappiness(int shiftBy, boolean clamped) {}


    @Nullable
    public UUID getOldOwnerId()
    {
        return (this.dataManager.get(OLD_OWNER_UUID)).orNull();
    }

    public void setOldOwnerId(@Nullable UUID uuidIn)
    { this.dataManager.set(OLD_OWNER_UUID, Optional.fromNullable(uuidIn)); }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        UUID oldOwner = this.getOldOwnerId();
        if (oldOwner != null)
        { compound.setString("OldOwnerUUID", oldOwner.toString()); }
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        String stringOldOwner = null;

        if (compound.hasKey("OldOwnerUUID", 8))
        { stringOldOwner = compound.getString("OldOwnerUUID"); }
        else if (compound.hasKey("OldOwner", 8))
        {
            String legacy = compound.getString("OldOwner");
            stringOldOwner = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), legacy);
        }

        if (stringOldOwner != null && !stringOldOwner.isEmpty())
        {
            try
            { this.setOldOwnerId(UUID.fromString(stringOldOwner));  }
            catch (Throwable var4) {}
        }
    }
}