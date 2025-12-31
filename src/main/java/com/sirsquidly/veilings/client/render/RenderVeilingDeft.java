package com.sirsquidly.veilings.client.render;

import com.sirsquidly.veilings.client.model.ModelVeilingDeft;
import com.sirsquidly.veilings.client.model.outfits.ModelVeilingDeftOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingArrowed;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingBodyOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingEyeColor;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingHeldItem;
import com.sirsquidly.veilings.common.entity.EntityVeilingDeft;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVeilingDeft extends RenderVeilingBase<EntityVeilingDeft>
{
    public static final ResourceLocation DEFT_TEXTURE = new ResourceLocation(veilings.MOD_ID + ":textures/entities/veiling/veiling_deft.png");
    private final ModelVeilingDeftOutfit outfitBodyModel = new ModelVeilingDeftOutfit();

    public RenderVeilingDeft(RenderManager managerIn)
    { this(managerIn, new ModelVeilingDeft(), 0.2F); }

    public RenderVeilingDeft(RenderManager managerIn, ModelBase modelIn, float shadowIn)
    {
        super(managerIn, modelIn, shadowIn);
        this.addLayer(new LayerVeilingHeldItem(this, 0.2F));
        this.addLayer(new LayerVeilingEyeColor(this, "deft"));
        this.addLayer(new LayerVeilingBodyOutfit(this, outfitBodyModel));

        this.addLayer(new LayerVeilingArrowed(this));
    }

    protected ResourceLocation getEntityTexture(EntityVeilingDeft entity)
    {
        return DEFT_TEXTURE;
    }
}