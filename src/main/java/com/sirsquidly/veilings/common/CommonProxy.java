package com.sirsquidly.veilings.common;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.config.ConfigParser;
import com.sirsquidly.veilings.init.VeilingsEntities;
import com.sirsquidly.veilings.init.VeilingsItems;
import com.sirsquidly.veilings.init.VeilingsLootTables;
import com.sirsquidly.veilings.init.VeilingsSounds;
import com.sirsquidly.veilings.network.CFDPacketHandler;
import com.sirsquidly.veilings.util.veilingItemUse.*;
import com.sirsquidly.veilings.veilings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy
{
    public static DamageSource causeNightmareDamage(Entity source)
    { return (new EntityDamageSource(veilings.MOD_ID + "." + "nightmare", source)).setDamageBypassesArmor().setDamageIsAbsolute(); }

    public void preInitRegisteries(FMLPreInitializationEvent event)
    {
        VeilingsEntities.registerEntities();
        VeilingsLootTables.registerLootTables();
        VeilingsSounds.registerSounds();
        CFDPacketHandler.registerMessages();

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) VeilingsEntities.RegisterRenderers();
    }

    public void postInitRegisteries(FMLPostInitializationEvent event)
    {
        ConfigParser.breakupConfigArrays();
        VeilingsEntities.registerEntitySpawns();


        /* Add all items from the Treat list. */
        for (int i = 0; i < ConfigParser.veilingTreatItems.size(); i++)
        { VeilingItemUseRegistry.register(ConfigParser.veilingTreatItems.get(i).getItem(), new IVeilingUseTreat()); }

        VeilingItemUseRegistry.register(VeilingsItems.VEILING_TIRAMISU, new IVeilingUseAttributeModifier("health_bonus", 2.0F)
        {
            public boolean requiredCondition(AbstractVeiling veiling, ItemStack stack)
            { return veiling.getMaxHealth() < 30.0F; }

            public void conditionTriggerFalse(AbstractVeiling veiling, ItemStack stack)
            { veiling.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 60 * 20, 0)); }
        });

        VeilingItemUseRegistry.register(Items.GLOWSTONE_DUST, new IVeilingUseTreat());

        VeilingItemUseRegistry.register(VeilingsItems.VEILING_MASK_CUSTODIAN, new IVeilingUseMask());
        VeilingItemUseRegistry.register(VeilingsItems.VEILING_MASK_DEFT, new IVeilingUseMask());
        VeilingItemUseRegistry.register(VeilingsItems.VEILING_MASK_DRAMATIST, new IVeilingUseMask());

        VeilingItemUseRegistry.register(Items.EGG, new IVeilingUseThrowable(SoundEvents.ENTITY_EGG_THROW, 3)
        {
            public IProjectile getProjectileEntity(World worldIn, AbstractVeiling veiling, ItemStack stackIn)
            { return new EntityEgg(worldIn, veiling); }
        });

        VeilingItemUseRegistry.register(Items.SNOWBALL, new IVeilingUseThrowable(SoundEvents.ENTITY_SNOWBALL_THROW, 3)
        {
            public IProjectile getProjectileEntity(World worldIn, AbstractVeiling veiling, ItemStack stackIn)
            { return new EntitySnowball(worldIn, veiling); }
        });

        VeilingItemUseRegistry.register(Items.ENDER_PEARL, new IVeilingUseThrowable(SoundEvents.ENTITY_ENDERPEARL_THROW, 3)
        {
            public IProjectile getProjectileEntity(World worldIn, AbstractVeiling veiling, ItemStack stackIn)
            { return new EntityEnderPearl(worldIn, veiling); }
        });

        VeilingItemUseRegistry.register(Items.SPLASH_POTION, new IVeilingUseThrowable(SoundEvents.ENTITY_SPLASH_POTION_THROW, 10)
        {
            public IProjectile getProjectileEntity(World worldIn, AbstractVeiling veiling, ItemStack stackIn)
            { return new EntityPotion(worldIn, veiling, stackIn); }
        });

        VeilingItemUseRegistry.register(Items.LINGERING_POTION, new IVeilingUseThrowable(SoundEvents.ENTITY_LINGERINGPOTION_THROW, 10)
        {
            public IProjectile getProjectileEntity(World worldIn, AbstractVeiling veiling, ItemStack stackIn)
            { return new EntityPotion(worldIn, veiling, stackIn); }
        });

    }

    @SideOnly(Side.CLIENT)
    public void registerItemRenderer(Item item, int meta, String id){}
}