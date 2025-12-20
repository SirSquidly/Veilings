package com.sirsquidly.veilings.client.model.mask;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCustodianMask extends ModelBiped
{
    private final ModelRenderer maskLayer;
    private final ModelRenderer mask3;

    public ModelCustodianMask(float size)
    {
        /* Inheriting from ModelBiped means `textureWidthIn` and `textureHeightIn` are used for rendering over a default ModelBiped, passing 0 lets us ignore that. */
        super(size, 0, 0,0);

        textureWidth = 64;
        textureHeight = 64;

        maskLayer = new ModelRenderer(this);
        maskLayer.setRotationPoint(0.0F, 0.0F, 0.0F);


        mask3 = new ModelRenderer(this);
        mask3.setRotationPoint(-3.0F, -3.0F, -3.0F);
        maskLayer.addChild(mask3);
        mask3.cubeList.add(new ModelBox(mask3, 32, 0, -1.0F, -9.0F, -1.0F, 8, 12, 8, 0.5F, false));

        bipedHead.addChild(maskLayer);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}