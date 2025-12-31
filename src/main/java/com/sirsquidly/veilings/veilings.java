package com.sirsquidly.veilings;

import com.sirsquidly.veilings.common.CommonProxy;
import com.sirsquidly.veilings.util.VeilingOutfitDyeingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = veilings.MOD_ID, name = veilings.NAME, version = veilings.VERSION, dependencies = veilings.DEPENDENCIES)
public class veilings {
    public static final String MOD_ID = "veilings";
    public static final String NAME = "Veilings";
    public static final String CONFIG_NAME = "veilings";
    public static final String VERSION = "1.2.1";
    public static final String DEPENDENCIES = "";
    public static final String CLIENT_PROXY_CLASS = "com.sirsquidly.veilings.client.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.sirsquidly.veilings.common.CommonProxy";
    public static Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Mod.Instance
    public static veilings instance;

    @SidedProxy(clientSide = veilings.CLIENT_PROXY_CLASS, serverSide = veilings.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    { proxy.preInitRegisteries(event); }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {  proxy.postInitRegisteries(event);  }

    @SubscribeEvent
    public static void onRegisterRecipes(RegistryEvent.Register<IRecipe> event)
    {
        event.getRegistry().register(new VeilingOutfitDyeingRecipe().setRegistryName("veiling_outfit_dyeing"));
    }
}
