package com.sirsquidly.veilings.client.render;

import com.sirsquidly.veilings.client.model.ModelVeilingDramatist;
import com.sirsquidly.veilings.client.model.outfits.ModelVeilingDramatistOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingBodyOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingHeldItem;
import com.sirsquidly.veilings.common.entity.wicked.EntityWickedVeilingDramatist;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWickedVeilingDramatist extends RenderVeilingBase<EntityWickedVeilingDramatist>
{
    public static final ResourceLocation WICKED_DRAMATIST_TEXTURE = new ResourceLocation(veilings.MOD_ID + ":textures/entities/veiling/wicked/wicked_veiling_dramatist.png");
    private final ModelVeilingDramatistOutfit outfitBodyModel = new ModelVeilingDramatistOutfit();

    public RenderWickedVeilingDramatist(RenderManager managerIn)
    {
        super(managerIn, new ModelVeilingDramatist(), 0.2F);
        this.addLayer(new LayerVeilingHeldItem(this, 0.4F));
        this.addLayer(new LayerVeilingBodyOutfit(this, outfitBodyModel));
    }

    protected ResourceLocation getEntityTexture(EntityWickedVeilingDramatist entity) { return WICKED_DRAMATIST_TEXTURE; }
}