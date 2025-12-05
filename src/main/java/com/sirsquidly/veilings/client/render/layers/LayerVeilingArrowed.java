package com.sirsquidly.veilings.client.render.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerVeilingArrowed extends LayerArrow
{
    public LayerVeilingArrowed(RenderLivingBase<?> rendererIn)
    { super(rendererIn); }

    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.translate(0.0F, 0.7F, 0.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entitylivingbaseIn.getBrightnessForRender() % 65536, entitylivingbaseIn.getBrightnessForRender() / 65536);
        super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
}
