package com.sirsquidly.veilings.client.model.mask;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDramatistMask extends ModelBiped
{
    private final ModelRenderer maskLayer;
    private final ModelRenderer mask;
    private final ModelRenderer bone3;
    private final ModelRenderer bone2;
    private final ModelRenderer cube_r1;
    private final ModelRenderer bone5;
    private final ModelRenderer bone6;
    private final ModelRenderer bone7;
    private final ModelRenderer cube_r2;

    public ModelDramatistMask(float size)
    {
        /* Inheriting from ModelBiped means `textureWidthIn` and `textureHeightIn` are used for rendering over a default ModelBiped, passing 0 lets us ignore that. */
        super(size, 0, 0,0);

        textureWidth = 64;
        textureHeight = 64;

        maskLayer = new ModelRenderer(this);
        maskLayer.setRotationPoint(0.0F, 0.5F, 0.0F);


        mask = new ModelRenderer(this);
        mask.setRotationPoint(-3.5F, -0.5F, -3.0F);
        maskLayer.addChild(mask);
        mask.cubeList.add(new ModelBox(mask, 33, 0, 0.0F, -8.0F, -1.0F, 7, 8, 8, 0.5F, false));

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(3.5F, -8.4F, -1.55F);
        mask.addChild(bone3);
        setRotationAngle(bone3, -0.2618F, 0.0F, 0.0F);
        bone3.cubeList.add(new ModelBox(bone3, 38, 49, -6.5F, -4.0252F, 0.1071F, 13, 4, 0, 0.0F, false));

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, -4.0252F, 0.1071F);
        bone3.addChild(bone2);
        setRotationAngle(bone2, 2.5307F, 0.0F, 0.0F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone2.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.2182F, 0.0F, 0.0F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 38, 45, -6.5F, -4.0F, 0.0F, 13, 4, 0, 0.0F, false));

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(3.5F, -9.4F, -1.8F);
        mask.addChild(bone5);
        setRotationAngle(bone5, 0.3491F, 0.0F, 0.0F);
        bone5.cubeList.add(new ModelBox(bone5, 34, 37, -7.5F, -4.0F, 0.0F, 15, 8, 0, 0.0F, false));

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(0.0F, -2.0F, 0.0F);
        bone5.addChild(bone6);
        setRotationAngle(bone6, 2.2689F, 0.0F, 0.0F);
        bone6.cubeList.add(new ModelBox(bone6, 34, 32, -7.5F, -5.0F, 0.0F, 15, 5, 0, 0.0F, false));

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(0.0F, -4.0F, 0.0F);
        bone5.addChild(bone7);
        setRotationAngle(bone7, 1.789F, 0.0F, 0.0F);


        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone7.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.5672F, 0.0F, 0.0F);
        cube_r2.cubeList.add(new ModelBox(cube_r2, 55, 27, -1.5F, -5.0F, 0.0F, 3, 5, 0, 0.0F, false));

        bipedHead.addChild(maskLayer);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}