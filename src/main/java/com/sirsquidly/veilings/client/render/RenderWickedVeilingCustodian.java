package com.sirsquidly.veilings.client.render;

import com.sirsquidly.veilings.client.model.ModelVeilingCustodian;
import com.sirsquidly.veilings.client.model.outfits.ModelVeilingCustodianOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingBodyOutfit;
import com.sirsquidly.veilings.client.render.layers.LayerVeilingHeldItem;
import com.sirsquidly.veilings.common.entity.wicked.EntityWickedVeilingCustodian;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWickedVeilingCustodian extends RenderVeilingBase<EntityWickedVeilingCustodian>
{
    public static final ResourceLocation WICKED_CUSTODIAN_TEXTURE = new ResourceLocation(veilings.MOD_ID + ":textures/entities/veiling/wicked/wicked_veiling_custodian.png");
    private final ModelVeilingCustodianOutfit outfitBodyModel = new ModelVeilingCustodianOutfit();

    public RenderWickedVeilingCustodian(RenderManager managerIn)
    {
        super(managerIn, new ModelVeilingCustodian(), 0.2F);
        this.addLayer(new LayerVeilingHeldItem(this, 0.4F));
        this.addLayer(new LayerVeilingBodyOutfit(this, outfitBodyModel));
    }

    protected ResourceLocation getEntityTexture(EntityWickedVeilingCustodian entity) { return WICKED_CUSTODIAN_TEXTURE; }
}