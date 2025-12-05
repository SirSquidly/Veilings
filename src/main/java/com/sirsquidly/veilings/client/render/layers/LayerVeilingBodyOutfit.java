package com.sirsquidly.veilings.client.render.layers;

import com.sirsquidly.veilings.client.model.ModelVeilingBase;
import com.sirsquidly.veilings.client.render.RenderVeilingBase;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.item.ItemVeilingOutfit;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerVeilingBodyOutfit implements LayerRenderer<AbstractVeiling>
{
    private final RenderVeilingBase veilingRenderer;
    private final ModelVeilingBase veilingOutfitModel;

    public LayerVeilingBodyOutfit(RenderVeilingBase nautilusRendererIn, ModelVeilingBase nautilusCoralModelIn)
    {
        this.veilingRenderer = nautilusRendererIn;
        this.veilingOutfitModel = nautilusCoralModelIn;
    }

    public void doRenderLayer(AbstractVeiling entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entity.getBodyOutfit().isEmpty()) return;
        ItemStack itemStack = entity.getBodyOutfit();
        if (itemStack == null || !(itemStack.getItem() instanceof ItemVeilingOutfit)) return;
        ItemVeilingOutfit outfit = (ItemVeilingOutfit)itemStack.getItem();

        if (outfit.getVeilingOutfitType() == 0)
        {
            boolean usesOverlay = outfit.getVeilingOutfitHasOverlay();
            String name = outfit.getVeilingOutfitVariant();
            int color = outfit.getColor(itemStack);
            int r = (color & 16711680) >> 16;
            int g = (color & 65280) >> 8;
            int b = (color & 255);

            ResourceLocation OUTFIT = new ResourceLocation(veilings.MOD_ID + ":textures/entities/veiling/outfit/body/" + name + (usesOverlay ? "_0" : "") + ".png");

            this.veilingRenderer.bindTexture(OUTFIT);
            this.veilingOutfitModel.setModelAttributes(this.veilingRenderer.getMainModel());
            GlStateManager.pushMatrix();
            GlStateManager.color(r / 255.0F, g / 255.0F, b / 255.0F, 1.0F);
            this.veilingOutfitModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
            this.veilingOutfitModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
            this.veilingOutfitModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (usesOverlay)
            {
                ResourceLocation OUTFIT_OVERLAY = new ResourceLocation(veilings.MOD_ID + ":textures/entities/veiling/outfit/body/" + name + "_1.png");
                this.veilingRenderer.bindTexture(OUTFIT_OVERLAY);
                this.veilingOutfitModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    { return true; }
}