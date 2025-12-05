package com.sirsquidly.veilings.common.entity;

import com.sirsquidly.veilings.client.model.ModelVeilingBase;
import com.sirsquidly.veilings.client.model.ModelVeilingDeft;
import com.sirsquidly.veilings.common.entity.ai.*;
import com.sirsquidly.veilings.common.entity.wicked.AbstractWickedVeiling;
import com.sirsquidly.veilings.common.item.ItemVeilingOutfit;
import com.sirsquidly.veilings.init.VeilingsSounds;
import com.sirsquidly.veilings.util.veilingItemUse.VeilingItemUseRegistry;
import com.sirsquidly.veilings.util.veilingLogic.VeilingMultiplication;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
* An inherited class of all types of Veiling.
* */
public class AbstractVeiling extends EntityTameable
{
    public final VeilingMultiplication multiplyLogic = new VeilingMultiplication();

    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final UUID HEALTH_MODIFIER_UUID = UUID.fromString("0c234b6e-341a-43ba-b282-cc76af94e65c");

    private static final DataParameter<Integer> ARM_POSE = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);
    public int previousPose = 0;

    private static final DataParameter<Integer> COMMAND_MODE = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DANCE_TYPE = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> EYE_COLOR = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> MOOD = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SIT_TYPE = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);

    private static final DataParameter<Integer> EAT_COOLDOWN = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> PLAY_COOLDOWN = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);
    private static final DataParameter<ItemStack> OUTFIT_ITEM_BODY = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Integer> SPAWN_COOLDOWN = EntityDataManager.createKey(AbstractVeiling.class, DataSerializers.VARINT);

    public ItemStack favoriteFood = ItemStack.EMPTY;
    private boolean dancing = false;
    public boolean snowballFighting = false;

    private float animTransSpeed = 0.4F;
    private float animationTime;
    private float prevAnimationTime;
    public int multiplyCooldown = 4000;

    /** Persistent upgrade values */
    private final Map<String, Float> attributeDifferences = new HashMap<>();

    public AbstractVeiling(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.2F);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ARM_POSE, 0);
        this.dataManager.register(COMMAND_MODE, 0);
        this.dataManager.register(DANCE_TYPE, 0);
        this.dataManager.register(EYE_COLOR, 0);
        this.dataManager.register(MOOD, 100);
        this.dataManager.register(SIT_TYPE, 0);
        this.dataManager.register(EAT_COOLDOWN, 0);
        this.dataManager.register(PLAY_COOLDOWN, 0);
        this.dataManager.register(OUTFIT_ITEM_BODY, ItemStack.EMPTY);
        this.dataManager.register(SPAWN_COOLDOWN, 3000);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);

        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);

        setupAttributeDifferences();
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIVeilingCommandSit(this));
        this.tasks.addTask(3, new EntityAIVeilingDislikeSunlight(this));
        this.tasks.addTask(3, new EntityAIVeilingBeg(this, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(5, new EntityAIVeilingFearMob<>(this, EntityZombie.class, 8.0F, 1.0D, 1.0D));
        this.tasks.addTask(5, new EntityAIVeilingFearMob<>(this, AbstractWickedVeiling.class, 2.0F, 1.2D, 1.0D));
        this.tasks.addTask(6, new EntityAIVeilingCommandWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIVeilingCommandFollow(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIVeilingUseItem(this));
        this.tasks.addTask(7, new EntityAIVeilingSnowballFight(this));
        this.tasks.addTask(8, new EntityAIVeilingDanceParty(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, AbstractWickedVeiling.class));
    }

    /** Yes, these babies are Undead. */
    public EnumCreatureAttribute getCreatureAttribute() { return EnumCreatureAttribute.UNDEAD; }

    /** This is recycled for Veiling Multiplying via water. */
    public EntityAgeable createChild(EntityAgeable thisVeiling) { return null; }

    /** Gets the INVERSE of this Veiling (Normal -> Wicked, Wicked -> Normal). */
    public AbstractVeiling getInverse(AbstractVeiling thisVeiling) { return null; }

    protected int getExperiencePoints(EntityPlayer player) { return 0; }

    public int getTalkInterval() { return 480; }

    protected SoundEvent getAmbientSound()
    {
        return this.getMood() > 75 ? VeilingsSounds.ENTITY_VEILING_AMBIENT_HAPPY : this.getMood() < 25 ? VeilingsSounds.ENTITY_VEILING_AMBIENT_SAD : VeilingsSounds.ENTITY_VEILING_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return VeilingsSounds.ENTITY_VEILING_HURT;
    }
    protected SoundEvent getDeathSound()
    {
        return VeilingsSounds.ENTITY_VEILING_DEATH;
    }

    public SoundEvent getEatSound() { return VeilingsSounds.ENTITY_VEILING_EAT; }
    public SoundEvent getPanicSound() { return VeilingsSounds.ENTITY_VEILING_PANIC; }

    /** WOW, it's 'getSoundPitch', but PUBLIC! WOW*/
    public float getPublicSoundPitch() { return this.getSoundPitch(); }


    public void onLivingUpdate()
    {
        this.updateArmSwingProgress();

        prevAnimationTime = animationTime;

        if (this.previousPose != this.getArmPose().ordinal())
        {
            this.animationTime = Math.max(0.0F, this.animationTime - this.animTransSpeed);
            if (this.animationTime <= 0.0F) previousPose = this.getArmPose().ordinal();
        }
        else
        { this.animationTime = Math.min(1.0F, this.animationTime + this.animTransSpeed); }


        if (!this.world.isRemote)
        {
            multiplyLogic.tick(this);
            if (this.getMood() < 10) tantrumUpdate();
            if (this.getPlayCooldown() > 0) this.setPlayCooldown(this.getPlayCooldown() - 1);
            if (this.getEatCooldown() > 0) this.setEatCooldown(this.getEatCooldown() - 1);
        }

        super.onLivingUpdate();
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isOwner(player))
        {
            if (!itemstack.isEmpty() && VeilingItemUseRegistry.get(itemstack.getItem()) != null)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemstack.copy());
                player.swingArm(hand);
                return true;
            }
            if (itemstack.getItem() instanceof ItemVeilingOutfit)
            {
                this.playSound(VeilingsSounds.ITEM_VEILING_OUTFIT_EQUIP, 0.5F, 1.0F);

                ItemStack oldOutfitStack = this.getBodyOutfit();
                this.setBodyOutfit(itemstack);

                if (!world.isRemote && !player.isCreative())
                {
                    itemstack.shrink(1);

                    if (!oldOutfitStack.isEmpty())
                    {
                        if (itemstack.isEmpty())
                        { player.setHeldItem(EnumHand.MAIN_HAND, oldOutfitStack); }
                        else if (!player.inventory.addItemStackToInventory(oldOutfitStack))
                        { player.dropItem(oldOutfitStack, false); }
                    }
                }

                player.swingArm(hand);
                return true;
            }
            else if (itemstack.getItem() instanceof ItemShears && !this.getBodyOutfit().isEmpty())
            {
                this.playSound(VeilingsSounds.ITEM_VEILING_OUTFIT_UNEQUIP_SHEARS, 0.5F, 1.0F);
                if (!world.isRemote) this.entityDropItem(this.getBodyOutfit(), 1);
                this.setBodyOutfit(ItemStack.EMPTY);
                player.swingArm(hand);
                return true;
            }
            else if (itemstack.getItem() instanceof ItemNameTag && itemstack.hasDisplayName())
            {
                if (!this.hasCustomName())
                {
                    this.shiftHappiness(100);
                    spawnOverheadParticle(EnumParticleTypes.HEART, 1 + this.world.rand.nextInt(3));
                }
                else this.shiftHappiness(-5 + this.world.rand.nextInt(20));

                return super.processInteract(player, hand);
            }
            else
            {
                if (!this.world.isRemote)
                {
                    this.playSound(VeilingsSounds.ENTITY_VEILING_AGREE, 0.25F, this.getPublicSoundPitch());

                    int next = this.getCommandMode() == 2 ? 0 : this.getCommandMode() + 1;

                    spawnOverheadParticle(EnumParticleTypes.CRIT_MAGIC, this.getCommandMode() + 1);

                    this.setCommandMode(next);

                    /** ONLY set HomePosition when they are Wandering! */
                    if (this.getCommandMode() == 1) this.setHomePosAndDistance(this.getPosition(), 8);
                    else this.detachHome();

                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.setAttackTarget(null);

                    System.out.print("Max Health:" + this.getMaxHealth());
                }

                //this.dataManager.set(DANCE_TYPE, this.rand.nextInt(8));
                //this.setArmPose(ModelVeilingBase.PoseBody.SITTING);
                player.swingArm(hand);
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (!this.snowballFighting && source.getImmediateSource() instanceof EntitySnowball)
        {
            boolean trySnowballFight = false;

            if (getPlayCooldown() <= 0 && (source.getTrueSource() instanceof EntityPlayer && this.getHeldItemMainhand().isEmpty() || source.getTrueSource() instanceof AbstractVeiling))
            {
                trySnowballFight = true;
            }

            if (trySnowballFight)
            {
                BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
                IBlockState iblockstate = this.world.getBlockState(blockpos);

                if (iblockstate.getBlock() == Blocks.SNOW_LAYER)
                {
                    if (!world.isRemote) this.entityDropItem(this.getHeldItemMainhand().copy(), 1);
                    this.snowballFighting = true;
                }
            }
        }
        else if (amount > 0 && source.getTrueSource() instanceof EntityPlayer)
        {
            if (this.getOwnerId() == source.getTrueSource().getUniqueID()) shiftHappiness(-10);
        }

        return super.attackEntityFrom(source, amount);
    }

    /** Veilings get sad when nearby ones die. */
    public void onDeath(DamageSource cause)
    {
        for (AbstractVeiling veiling : world.getEntitiesWithinAABB(AbstractVeiling.class, this.getEntityBoundingBox().grow(5)))
        {
            if (veiling.getOwnerId() != null && !veiling.getOwnerId().equals(this.getOwnerId())) continue;
            if (veiling.getMood() > 10) veiling.shiftHappiness(-5);
        }

        this.setArmPose(ModelVeilingBase.PoseBody.EMPTY);

        if (!this.getBodyOutfit().isEmpty() && !world.isRemote)
        {
            this.entityDropItem(this.getBodyOutfit(), 1);
        }

        super.onDeath(cause);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
            this.getHeldItemMainhand().damageItem(1, this);
        }

        return flag;
    }

    /** Spawns a Particle over the Veiling's Head. */
    public void spawnOverheadParticle(EnumParticleTypes particle, int quantity)
    {
        if (world.isRemote) return;

        ((WorldServer)this.world).spawnParticle(
                particle, this.posX, this.posY + (height * 1.2), this.posZ, quantity,
                0.1D, 0.05D, 0.1D, 0.05D );
    }

    /**
     * Section below is Mood-related logic
     *
     * */

    public void tantrumUpdate()
    {
        setArmPose(ModelVeilingBase.PoseBody.CRYING);
        ((WorldServer)this.world).spawnParticle(
                EnumParticleTypes.WATER_SPLASH,
                this.posX, this.posY + this.getEyeHeight(), this.posZ,
                2,
                0.1D, 0.1D, 0.1D,
                0.05D
        );

        shiftHappiness(-1, false);

        /* Tantrums spread among nearby Veilings. */
        if (this.ticksExisted % 10 == 0)
        {
            this.playSound(VeilingsSounds.ENTITY_VEILING_TANTRUM, 1.0F, 1.0F);

            this.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 2, false, false));

            for (AbstractVeiling veiling : world.getEntitiesWithinAABB(AbstractVeiling.class, this.getEntityBoundingBox().grow(5)))
            {
                if (veiling.getMood() > 10) veiling.shiftHappiness(-1);
            }

            if (this.getMood() <= -100)
            {
                multiplyLogic.transformVeiling(world, this, getInverse(this));

                this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0F, 1.0F);
            }
        }
    }
    public void shiftHappiness(int shiftBy)
    { shiftHappiness(shiftBy, true); }

    public void shiftHappiness(int shiftBy, boolean clamped)
    {
        /* Do NOT run Client-side! */
        if (world.isRemote) return;

        spawnOverheadParticle(shiftBy > 0 ? EnumParticleTypes.VILLAGER_HAPPY : EnumParticleTypes.VILLAGER_ANGRY, Math.abs(shiftBy));

        /* Mood shift sounds are more likely the higher the shift is. */
        if (world.rand.nextFloat() < Math.abs(shiftBy) * 0.05F) this.playSound( shiftBy > 0 ? VeilingsSounds.ENTITY_VEILING_MOOD_INCREASE : VeilingsSounds.ENTITY_VEILING_MOOD_DECREASE, Math.min(Math.abs(shiftBy) * 0.1F, 1.0F), 1.0F);

        int adjustment = this.getMood() + shiftBy;
        this.setMood(clamped ? MathHelper.clamp(adjustment, 0, 100) : adjustment);
    }

    /** Sets up the swim timer for proper animation usage.  */
    @SideOnly(Side.CLIENT)
    public float getClientAnimationTime(float partialTick)
    { return this.prevAnimationTime + (this.animationTime - this.prevAnimationTime) * partialTick;  }

    public void setArmPose(ModelVeilingBase.PoseBody pose)
    {
        int newOrdinal = pose.ordinal();
        int oldOrdinal = this.getArmPose().ordinal();

        if (newOrdinal != oldOrdinal)
        {
            this.previousPose = oldOrdinal;
            this.dataManager.set(ARM_POSE, newOrdinal);
            this.animationTime = 0.0F;
        }
    }
    public ModelVeilingDeft.PoseBody getArmPose()
    { return ModelVeilingDeft.PoseBody.values()[this.dataManager.get(ARM_POSE)]; }

    /** 0 = Follow, 1 = Wander, 2 = Sit */
    public int getCommandMode() { return this.dataManager.get(COMMAND_MODE); }
    /** 0 = Follow, 1 = Wander, 2 = Sit */
    public void setCommandMode(int state) { this.dataManager.set(COMMAND_MODE, state); }

    public int getMood() { return this.dataManager.get(MOOD); }
    public void setMood(int state) { this.dataManager.set(MOOD, state); }

    public int getEatCooldown() { return this.dataManager.get(EAT_COOLDOWN); }
    public void setEatCooldown(int state) { this.dataManager.set(EAT_COOLDOWN, state); }

    public int getEyeColor() { return this.dataManager.get(EYE_COLOR); }
    public void setEyeColor(int state) { this.dataManager.set(EYE_COLOR, state); }

    public int getPlayCooldown() { return this.dataManager.get(PLAY_COOLDOWN); }
    public void setPlayCooldown(int state) { this.dataManager.set(PLAY_COOLDOWN, state); }

    public int getSpawnCooldown() { return this.dataManager.get(SPAWN_COOLDOWN); }
    public void setSpawnCooldown(int state) { this.dataManager.set(SPAWN_COOLDOWN, state); }

    public ItemStack getFavoriteFood() { return this.favoriteFood; }
    public void setDanceType(int state) { this.dataManager.set(DANCE_TYPE, state); }
    public int getDanceType() { return this.dataManager.get(DANCE_TYPE); }
    public void setSitType(int state) { this.dataManager.set(SIT_TYPE, state); }
    public int getSitType() { return this.dataManager.get(SIT_TYPE); }

    public void setAnimTransSpeed(float newSpeed) { this.animTransSpeed = newSpeed; }

    public ItemStack getBodyOutfit() { return this.dataManager.get(OUTFIT_ITEM_BODY); }
    public void setBodyOutfit(ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            stack = stack.copy();
            stack.setCount(1);
        }

        if (!this.world.isRemote)
        {
            updateEntityAttribute(SharedMonsterAttributes.ARMOR, ARMOR_MODIFIER_UUID, "Body Outfit Armor bonus", stack.isEmpty() ? 0 : 4, 0);
        }

        this.dataManager.set(OUTFIT_ITEM_BODY, stack);
    }

    /** A simple method for adding new Attributes to the Veiling. */
    public void applyUpgrade(String key, float amount)
    {
        float newValue = this.getAttributeValue(key) + amount;
        attributeDifferences.put(key, newValue);

        updateAttributeFromKey(key, newValue);
    }

    public Float getAttributeValue(String key) { return attributeDifferences.getOrDefault(key, 0f); }

    private void setupAttributeDifferences()
    {
        if (attributeDifferences == null || attributeDifferences.isEmpty()) return;

        for (Map.Entry<String, Float> entry : attributeDifferences.entrySet())
        { updateAttributeFromKey(entry.getKey(), entry.getValue()); }
    }

    /** Updates the entity attribute based on the passed key. */
    private void updateAttributeFromKey(String key, double value)
    {
        IAttribute attribute = null;
        UUID uuid = null;

        switch (key)
        {
            case "health_bonus":
                attribute = SharedMonsterAttributes.MAX_HEALTH;
                uuid = HEALTH_MODIFIER_UUID;
                break;
        }

        if (attribute != null)
        {
            updateEntityAttribute(attribute, uuid, key, value, 0);
        }
    }

    /** An easy method for updating Attributes. */
    public void updateEntityAttribute(IAttribute attribute, UUID uuid, String name, double value, int operation)
    {
        this.getEntityAttribute(attribute).removeModifier(uuid);

        if (value != 0)
        { this.getEntityAttribute(attribute).applyModifier((new AttributeModifier(uuid, name, value, operation))); }
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Mood", this.getMood());
        compound.setInteger("EyeColor", this.getEyeColor());
        compound.setInteger("CommandMode", this.getCommandMode());
        compound.setInteger("EatingCooldown", this.getEatCooldown());
        compound.setInteger("PlayCooldown", this.getPlayCooldown());
        compound.setInteger("SpawningCooldown", this.getSpawnCooldown());
        if (!this.getFavoriteFood().isEmpty())
        { compound.setTag("FavoriteFood", this.getFavoriteFood().writeToNBT(new NBTTagCompound())); }
        if (!this.getBodyOutfit().isEmpty())
        { compound.setTag("OutfitBodyItemstack", this.getBodyOutfit().writeToNBT(new NBTTagCompound())); }

        NBTTagList list = new NBTTagList();

        for (Map.Entry<String, Float> entry : attributeDifferences.entrySet())
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Key", entry.getKey());
            tag.setFloat("Value", entry.getValue());
            list.appendTag(tag);
        }
        compound.setTag("VeilingUpgrades", list);

        compound.setInteger("DanceType", this.getDanceType());
        compound.setInteger("SitType", this.getSitType());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setMood(compound.getInteger("Mood"));
        this.setEyeColor(compound.getInteger("EyeColor"));
        this.setCommandMode(compound.getInteger("CommandMode"));
        this.setEatCooldown(compound.getInteger("EatingCooldown"));
        this.setPlayCooldown(compound.getInteger("PlayCooldown"));
        this.setSpawnCooldown(compound.getInteger("SpawningCooldown"));
        if (!compound.getCompoundTag("FavoriteFood").isEmpty())
        { this.favoriteFood = new ItemStack(compound.getCompoundTag("FavoriteFood")); }
        if (!compound.getCompoundTag("OutfitBodyItemstack").isEmpty())
        { this.setBodyOutfit(new ItemStack(compound.getCompoundTag("OutfitBodyItemstack"))); }

        attributeDifferences.clear();
        NBTTagList list = compound.getTagList("VeilingUpgrades", 10);
        for (int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            attributeDifferences.put( tag.getString("Key"), tag.getFloat("Value"));
        }
        setupAttributeDifferences();

        this.dataManager.set(DANCE_TYPE, compound.getInteger("DanceType"));
        this.dataManager.set(SIT_TYPE, compound.getInteger("SitType"));
    }
}