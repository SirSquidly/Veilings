package com.sirsquidly.veilings.init;

import com.sirsquidly.veilings.client.render.*;
import com.sirsquidly.veilings.common.entity.*;
import com.sirsquidly.veilings.common.entity.wicked.*;
import com.sirsquidly.veilings.veilings;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VeilingsEntities
{
    public static int id;

    public static void registerEntities()
    {
        registerEntity("veiling_custodian", EntityVeilingCustodian.class, ++id, 100, 460551, 16777215);
        registerEntity("veiling_deft", EntityVeilingDeft.class, ++id, 100, 460551, 16777215);
        registerEntity("veiling_dramatist", EntityVeilingDramatist.class, ++id, 100, 460551, 16777215);

        registerEntity("wicked_veiling_custodian", EntityWickedVeilingCustodian.class, ++id, 100, 460551, 16777215);
        registerEntity("wicked_veiling_deft", EntityWickedVeilingDeft.class, ++id, 100, 460551, 16777215);
        registerEntity("wicked_veiling_dramatist", EntityWickedVeilingDramatist.class, ++id, 100, 460551, 16777215);

        registerEntity("spirit_dagger", EntitySpiritDagger.class, ++id, 100);
    }

    public static void registerEntitySpawns()
    {}

    @SideOnly(Side.CLIENT)
    public static void RegisterRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityVeilingCustodian.class, RenderVeilingCustodian::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityVeilingDeft.class, RenderVeilingDeft::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityVeilingDramatist.class, RenderVeilingDramatist::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityWickedVeilingCustodian.class, RenderWickedVeilingCustodian::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWickedVeilingDeft.class, RenderWickedVeilingDeft::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWickedVeilingDramatist.class, RenderWickedVeilingDramatist::new);

        RenderingRegistry.registerEntityRenderingHandler(EntitySpiritDagger.class, RenderSpiritDagger::new);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
    { EntityRegistry.registerModEntity(new ResourceLocation(veilings.MOD_ID, name), entity, veilings.MOD_ID + "." + name, id, veilings.instance, range, 1, true, color1, color2); }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range)
    { EntityRegistry.registerModEntity(new ResourceLocation(veilings.MOD_ID, name), entity, veilings.MOD_ID + "." + name, id, veilings.instance, range, 1, true); }
}