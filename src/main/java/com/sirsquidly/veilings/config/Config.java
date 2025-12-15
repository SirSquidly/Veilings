package com.sirsquidly.veilings.config;

import com.sirsquidly.veilings.veilings;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.common.config.Config(modid = veilings.MOD_ID, name = veilings.CONFIG_NAME)
@net.minecraftforge.common.config.Config.LangKey("config.veilings.title")
@Mod.EventBusSubscriber(modid = veilings.MOD_ID)
public class Config
{
    @net.minecraftforge.common.config.Config.LangKey("config.veilings.configVersion")
    @net.minecraftforge.common.config.Config.Comment({
            "Config Versions help inform modpack makers/config users if changes have been made to the config between updates. These differ from main versioning, since the config file is static.",
            "Basically, you compare the current default of this value, to the default of when you generated it.",
            "",
            "The versioning follows:",
            "0.0.x - Default values have been slightly adjusted.",
            "0.x.0 - Config options have been added.",
            "x.0.0 - Previous Config Options have been completely overhauled and/or removed. Creating a fresh file is recommended."
    })
    public static String configVersion = "1.0.0";

    @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling")
    @net.minecraftforge.common.config.Config.Comment("Config related to Veilings themselves")
    public static configEntity entity = new configEntity();

    public static class configEntity
    {

        @net.minecraftforge.common.config.Config.RequiresMcRestart
        @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.veilingTreatList")
        @net.minecraftforge.common.config.Config.Comment("Items that Veiling will eat, and how much Happiness they give.")
        public String[] veilingTreatList = {
                "minecraft:cake=20",
                "minecraft:cookie=5",
                "minecraft:pumpkin_pie=10",
                "minecraft:sugar=2"
        };

        @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.custodian")
        @net.minecraftforge.common.config.Config.Comment("Config specific for Veiling Custodians")
        public configEntity.configVeilingCustodian custodian = new configEntity.configVeilingCustodian();

        public static class configVeilingCustodian
        {
            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.custodian.baseHealth")
            @net.minecraftforge.common.config.Config.Comment("The mood shift when a Veilings is given a new name (by their owner), for the first time only.")
            public int baseHealth = 8;

            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.custodian.baseAttackDamage")
            @net.minecraftforge.common.config.Config.Comment("The mood shift when a Veilings is given a new name (by their owner), for the first time only.")
            public int baseAttackDamage = 4;
        }

        @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.deft")
        @net.minecraftforge.common.config.Config.Comment("Config specific for Veiling Defts")
        public configEntity.configVeilingDeft deft = new configEntity.configVeilingDeft();

        public static class configVeilingDeft
        {
            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.deft.baseHealth")
            @net.minecraftforge.common.config.Config.Comment("The mood shift when a Veilings is given a new name (by their owner), for the first time only.")
            public int baseHealth = 8;

            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.deft.baseAttackDamage")
            @net.minecraftforge.common.config.Config.Comment("The mood shift when a Veilings is given a new name (by their owner), for the first time only.")
            public int baseAttackDamage = 4;
        }

        @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.dramatist")
        @net.minecraftforge.common.config.Config.Comment("Config specific for Veiling Dramatists")
        public configEntity.configVeilingDramatist dramatist = new configEntity.configVeilingDramatist();

        public static class configVeilingDramatist
        {
            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.dramatist.baseHealth")
            @net.minecraftforge.common.config.Config.Comment("The mood shift when a Veilings is given a new name (by their owner), for the first time only.")
            public int baseHealth = 8;

            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.dramatist.baseAttackDamage")
            @net.minecraftforge.common.config.Config.Comment("The mood shift when a Veilings is given a new name (by their owner), for the first time only.")
            public int baseAttackDamage = 4;
        }

        @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.mood")
        @net.minecraftforge.common.config.Config.Comment("Config related to Veilings themselves")
        public configEntity.configVeilingMood mood = new configEntity.configVeilingMood();

        public static class configVeilingMood
        {
            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.mood.enableSunlightDislike")
            @net.minecraftforge.common.config.Config.Comment("Veilings slightly dislike Sunlight.")
            public boolean enableSunlightDislike = true;

            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.mood.enableZombieFear")
            @net.minecraftforge.common.config.Config.Comment("If Veilings should fear (and flee from) Zombies.")
            public boolean enableZombieFear = true;

            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.mood.veilingNewName")
            @net.minecraftforge.common.config.Config.Comment("The mood shift when a Veilings is given a new name (by their owner), for the first time only.")
            public int veilingNewName = 100;

            @net.minecraftforge.common.config.Config.RequiresMcRestart
            @net.minecraftforge.common.config.Config.LangKey("config.veilings.veiling.mood.veilingSympathy")
            @net.minecraftforge.common.config.Config.Comment("The mood shift when a Veilings friends (ones of the same owner) die.")
            public int veilingSympathy = -5;
        }
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