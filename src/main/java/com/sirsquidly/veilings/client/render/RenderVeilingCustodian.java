package com.sirsquidly.veilings.client.render;

import com.sirsquidly.veilings.client.model.ModelVeilingCustodian;
import com.sirsquidly.veilings.client.model.outfits.ModelVeilingCustodianOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingArrowed;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingBodyOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingEyeColor;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingHeldItem;
import com.sirsquidly.veilings.common.entity.EntityVeilingCustodian;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVeilingCustodian extends RenderVeilingBase<EntityVeilingCustodian>
{
    public static final ResourceLocation CUSTODIAN_TEXTURE = new ResourceLocation(veilings.MOD_ID + ":textures/entities/veiling/veiling_custodian.png");
    private final ModelVeilingCustodianOutfit outfitBodyModel = new ModelVeilingCustodianOutfit();

    public RenderVeilingCustodian(RenderManager managerIn)
    {
        super(managerIn, new ModelVeilingCustodian(), 0.2F);
        this.addLayer(new LayerVeilingHeldItem(this, 0.4F));
        this.addLayer(new LayerVeilingEyeColor(this, "custodian"));
        this.addLayer(new LayerVeilingBodyOutfit(this, outfitBodyModel));

        this.addLayer(new LayerVeilingArrowed(this));
    }

    protected ResourceLocation getEntityTexture(EntityVeilingCustodian entity)
    {
        return CUSTODIAN_TEXTURE;
    }


}