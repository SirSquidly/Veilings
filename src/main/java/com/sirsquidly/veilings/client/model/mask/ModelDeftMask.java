package com.sirsquidly.veilings.client.model.mask;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDeftMask extends ModelBiped
{
    private final ModelRenderer maskLayer;
    private final ModelRenderer mask;

    public ModelDeftMask(float size)
    {
        /* Inheriting from ModelBiped means `textureWidthIn` and `textureHeightIn` are used for rendering over a default ModelBiped, passing 0 lets us ignore that. */
        super(size, 0, 0,0);

        textureWidth = 64;
        textureHeight = 64;

        maskLayer = new ModelRenderer(this);
        maskLayer.setRotationPoint(0.0F, 0.5F, 0.0F);
        maskLayer.cubeList.add(new ModelBox(maskLayer, 25, 2, -4.0F, -11.5F, -5.0F, 2, 5, 1, 0.0F, false));
        maskLayer.cubeList.add(new ModelBox(maskLayer, 25, 2, 2.0F, -11.5F, -5.0F, 2, 5, 1, 0.0F, true));

        mask = new ModelRenderer(this);
        mask.setRotationPoint(-3.0F, -0.5F, -3.0F);
        maskLayer.addChild(mask);
        mask.cubeList.add(new ModelBox(mask, 32, 0, -1.0F, -8.0F, -1.0F, 8, 8, 8, 0.5F, false));

        bipedHead.addChild(maskLayer);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}