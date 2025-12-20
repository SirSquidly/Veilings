package com.sirsquidly.veilings.common;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingDeft;
import com.sirsquidly.veilings.common.item.ItemVeilingMask;
import com.sirsquidly.veilings.init.VeilingsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class CommonEvents
{
    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event)
    {
        if (event.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE))
        {
            final LootPool main = event.getTable().getPool("main");
            if (main != null)
            {
                main.addEntry(new LootEntryItem(VeilingsItems.VEILING_EGG, 5, 0, new LootFunction[0], new LootCondition[0], "loottable:false_fetus"));
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(LivingAttackEvent event)
    {
        Entity sourceEntity = event.getSource().getTrueSource();
        if (!(sourceEntity instanceof EntityPlayer)) return;
        Item wornItem = ((EntityPlayer)sourceEntity).getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
        if (!(wornItem instanceof ItemVeilingMask) || ((ItemVeilingMask)wornItem).getMaskType() != 0) return;

        List<AbstractVeiling> checkNearbyDeft = sourceEntity.world.getEntitiesWithinAABB(EntityVeilingDeft.class, sourceEntity.getEntityBoundingBox().grow(10),
                veiling -> (veiling.getOwnerId() != null && veiling.getOwnerId().equals(sourceEntity.getUniqueID())));

        if (!checkNearbyDeft.isEmpty())
        {
            ((EntityPlayer) sourceEntity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 4 * 20));
        }
    }

    @SubscribeEvent
    public static void attemptVeilingTotemUse(LivingDeathEvent event)
    {
        if (event.getEntityLiving().world.isRemote) return;
        if (!(event.getEntityLiving() instanceof AbstractVeiling)) return;

        AbstractVeiling veiling = (AbstractVeiling) event.getEntityLiving();
        if (veiling.getAttributeValue("undying_bonus") <= 0) return;

        event.setCanceled(true);
        /* Remove the Undying ability after using it once. */
        veiling.applyUpgrade("undying_bonus", 0);

        veiling.setHealth(1.0F);
        veiling.clearActivePotions();
        veiling.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
        // veiling.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
        veiling.world.setEntityState(veiling, (byte)35);
    }
}