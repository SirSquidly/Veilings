package com.sirsquidly.veilings.client.render;

import com.sirsquidly.veilings.client.model.ModelVeilingDramatist;
import com.sirsquidly.veilings.client.model.outfits.ModelVeilingDramatistOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingArrowed;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingBodyOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingEyeColor;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingHeldItem;
import com.sirsquidly.veilings.common.entity.EntityVeilingDramatist;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVeilingDramatist extends RenderVeilingBase<EntityVeilingDramatist>
{
    public static final ResourceLocation DRAMATIST_TEXTURE = new ResourceLocation(veilings.MOD_ID + ":textures/entities/veiling/veiling_dramatist.png");
    private final ModelVeilingDramatistOutfit outfitBodyModel = new ModelVeilingDramatistOutfit();

    public RenderVeilingDramatist(RenderManager managerIn)
    {
        super(managerIn, new ModelVeilingDramatist(), 0.2F);
        this.addLayer(new LayerVeilingHeldItem(this, 0.4F));
        this.addLayer(new LayerVeilingEyeColor(this, "dramatist"));
        this.addLayer(new LayerVeilingBodyOutfit(this, outfitBodyModel));

        this.addLayer(new LayerVeilingArrowed(this));
    }

    protected ResourceLocation getEntityTexture(EntityVeilingDramatist entity)
    {
        return DRAMATIST_TEXTURE;
    }
}