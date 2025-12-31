package com.sirsquidly.veilings.client.render.layers;

import com.sirsquidly.veilings.client.model.ModelVeilingBase;
import com.sirsquidly.veilings.client.render.RenderVeilingBase;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerVeilingHeldItem implements LayerRenderer<AbstractVeiling>
{
    private final RenderVeilingBase veilingRender;
    private float holdingOffset;

    public LayerVeilingHeldItem(RenderVeilingBase veilingRendererIn, float heldOffsetIn)
    {
        this.veilingRender = veilingRendererIn;
        this.holdingOffset = heldOffsetIn;
    }

    public void doRenderLayer(AbstractVeiling entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        ItemStack itemstack = entitylivingbaseIn.getHeldItemMainhand();

        if (!itemstack.isEmpty())
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();

            ModelVeilingBase model = (ModelVeilingBase) this.veilingRender.getMainModel();
            ModelRenderer main = model.main;
            ModelRenderer bodyUpper = model.upper_body;

            Item item = itemstack.getItem();
            Minecraft minecraft = Minecraft.getMinecraft();

            if (entitylivingbaseIn.getArmPose() == AbstractVeiling.PoseBody.EMPTY)
            {
                ModelRenderer arms = model.arms;
                /* First, move to the center. */
                GlStateManager.translate(0, 1.2F, 0.0F);

                arms.postRender(scale);
                bodyUpper.postRender(scale);

                GlStateManager.translate(0, 0.6F, -0.1F);

                if (item.isFull3D())
                {
                    if (item.shouldRotateAroundWhenRendering())
                    {
                        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.translate(0.0F, -0.0625F, 0.0F);
                    }

                    GlStateManager.translate(0, 0, -0.1F);
                    this.veilingRender.transformHeldFull3DItemLayer();
                }
                else
                {
                    GlStateManager.scale(0.875F, 0.875F, 0.875F);
                }

                GlStateManager.rotate(-90F, 1F, 0F, 0F);

                minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
            }
            else
            {
                ModelRenderer specificArm = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.LEFT ? model.armL : model.armR;

                /* First, adjust to the rotation point of the LAST bone used in the following `postRender`s. */
                GlStateManager.translate(0, -this.holdingOffset, 0.0F);

                main.postRender(scale);
                bodyUpper.postRender(scale);
                specificArm.postRender(scale);

                /* THEN shift to where we want it to be at. */
                GlStateManager.translate(-0.05, this.holdingOffset, 0F);

                if (item.isFull3D())
                {
                    if (item.shouldRotateAroundWhenRendering())
                    {
                        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.translate(0.0F, -0.0625F, 0.0F);
                    }

                    GlStateManager.translate(0, 0, -0.1F);
                    this.veilingRender.transformHeldFull3DItemLayer();
                }
                else
                {
                    GlStateManager.scale(0.875F, 0.875F, 0.875F);
                }

                GlStateManager.rotate(-90F, 1F, 0F, 0F);

                minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
            }

            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
