package com.sirsquidly.veilings.client.render.layers;

import com.sirsquidly.veilings.client.render.RenderVeilingBase;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.wicked.AbstractWickedVeiling;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerVeilingEyeColor implements LayerRenderer<AbstractVeiling>
{
    private String veilingTypeString;
    private final RenderVeilingBase veilingRender;

    public LayerVeilingEyeColor(RenderVeilingBase veilingRenderIn, String veilingTypeStringIn)
    {
        this.veilingRender = veilingRenderIn;
        this.veilingTypeString = veilingTypeStringIn;
    }

    public void doRenderLayer(AbstractVeiling entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entity instanceof AbstractWickedVeiling) return;
        if (entity.getArmPose() == AbstractVeiling.PoseBody.SLEEPING) return;

        ResourceLocation EYECOLOR = new ResourceLocation(veilings.MOD_ID + ":textures/entities/veiling/eyes/veiling_"+ veilingTypeString + "_eye" + entity.getEyeColor() + ".png");
        this.veilingRender.bindTexture(EYECOLOR);
        this.veilingRender.getMainModel().setModelAttributes(veilingRender.getMainModel());
        GlStateManager.pushMatrix();
        this.veilingRender.getMainModel().setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        this.veilingRender.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.popMatrix();
    }

    public boolean shouldCombineTextures() { return true; }
}