package com.sirsquidly.veilings.common;

import com.sirsquidly.veilings.init.VeilingsItems;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
}