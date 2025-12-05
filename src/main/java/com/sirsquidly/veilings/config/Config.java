package com.sirsquidly.veilings.config;

import com.sirsquidly.veilings.veilings;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.common.config.Config(modid = veilings.MOD_ID, name = veilings.CONFIG_NAME)
@net.minecraftforge.common.config.Config.LangKey("creaturesfromdarkness.config.title")
@Mod.EventBusSubscriber(modid = veilings.MOD_ID)
public class Config
{

    @net.minecraftforge.common.config.Config.LangKey("config.veilings.entity")
    @net.minecraftforge.common.config.Config.Comment("Config related to Entities")
    public static configEntity entity = new configEntity();

    public static class configEntity
    {

        @net.minecraftforge.common.config.Config.RequiresMcRestart
        @net.minecraftforge.common.config.Config.LangKey("config.veilings.entity.veilingTreatList")
        @net.minecraftforge.common.config.Config.Comment("Items that Veiling will eat, and how much Happiness they give.")
        public String[] veilingTreatList = {
                "minecraft:cake=20",
                "minecraft:cookie=5",
                "minecraft:pumpkin_pie=10",
                "minecraft:sugar=2"
        };
    }














    @Mod.EventBusSubscriber(modid = veilings.MOD_ID)
    public static class ConfigSyncHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if(event.getModID().equals(veilings.MOD_ID))
            { ConfigManager.sync(veilings.MOD_ID, net.minecraftforge.common.config.Config.Type.INSTANCE); }
        }
    }
}