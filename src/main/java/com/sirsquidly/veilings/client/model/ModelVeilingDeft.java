package com.sirsquidly.veilings.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVeilingDeft extends ModelVeilingBase
{
    public final ModelRenderer earR;
    public final ModelRenderer cube_r1;
    public final ModelRenderer earL;
    public final ModelRenderer braceletL;
    public final ModelRenderer braceletR;

    public ModelVeilingDeft()
    {
        this(64, 64);
    }

    public ModelVeilingDeft(int textureWidthIn, int textureHeightIn)
    {
        textureWidth = textureWidthIn;
        textureHeight = textureHeightIn;

        this.bodyPose = PoseBody.EMPTY;

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 24.0F, 0.0F);


        upper_body = new ModelRenderer(this);
        upper_body.setRotationPoint(0.0F, -6.0F, 0.0F);
        main.addChild(upper_body);


        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -6.0F, 0.25F);
        upper_body.addChild(head);
        head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -8.0F, -4.25F, 8, 8, 8, 0.0F, false));

        maskLayer = new ModelRenderer(this);
        maskLayer.setRotationPoint(3.0F, -6.0F, -4.25F);
        head.addChild(maskLayer);
        maskLayer.cubeList.add(new ModelBox(maskLayer, 25, 2, -7.0F, -5.0F, -1.0F, 2, 5, 1, 0.0F, false));
        maskLayer.cubeList.add(new ModelBox(maskLayer, 25, 2, -1.0F, -5.0F, -1.0F, 2, 5, 1, 0.0F, true));

        mask = new ModelRenderer(this);
        mask.setRotationPoint(-6.0F, 6.0F, 1.0F);
        maskLayer.addChild(mask);
        mask.cubeList.add(new ModelBox(mask, 32, 0, -1.0F, -8.0F, -1.0F, 8, 8, 8, 0.5F, false));

        earR = new ModelRenderer(this);
        earR.setRotationPoint(6.0F, -1.0F, 0.75F);
        head.addChild(earR);
        earR.cubeList.add(new ModelBox(earR, 32, 0, -13.0F, -5.0F, -1.0F, 3, 4, 0, 0.0F, false));

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(-1.0F, -1.0F, -1.0F);
        earR.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.7854F, 0.0F, 0.0F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 16, 46, 0.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, false));

        earL = new ModelRenderer(this);
        earL.setRotationPoint(-5.0F, -1.0F, 0.75F);
        head.addChild(earL);
        earL.cubeList.add(new ModelBox(earL, 32, 4, 9.0F, -5.0F, -1.0F, 3, 4, 0, 0.0F, true));

        torso = new ModelRenderer(this);
        torso.setRotationPoint(-1.0F, -4.0F, -1.0F);
        upper_body.addChild(torso);
        torso.cubeList.add(new ModelBox(torso, 0, 16, -1.0F, -2.0F, -0.5F, 4, 6, 3, 0.0F, false));
        torso.cubeList.add(new ModelBox(torso, 14, 16, -2.0F, -1.9F, -1.0F, 6, 2, 4, 0.0F, false));

        wingL = new ModelRenderer(this);
        wingL.setRotationPoint(2.0F, -1.0F, 2.5F);
        torso.addChild(wingL);
        wingL.cubeList.add(new ModelBox(wingL, 34, 16, 0.0F, -1.0F, 0.0F, 0, 8, 8, 0.0F, false));

        wingR = new ModelRenderer(this);
        wingR.setRotationPoint(0.0F, -1.0F, 2.5F);
        torso.addChild(wingR);
        wingR.cubeList.add(new ModelBox(wingR, 34, 8, 0.0F, -1.0F, 0.0F, 0, 8, 8, 0.0F, false));

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, -5.0F, 0.0F);
        upper_body.addChild(arms);


        armL = new ModelRenderer(this);
        armL.setRotationPoint(2.0F, 0.0F, 0.0F);
        arms.addChild(armL);
        setRotationAngle(armL, 0.0F, 0.0F, -0.1309F);
        armL.cubeList.add(new ModelBox(armL, 8, 33, 0.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F, true));

        braceletL = new ModelRenderer(this);
        braceletL.setRotationPoint(1.0F, 4.0F, -0.5F);
        armL.addChild(braceletL);
        setRotationAngle(braceletL, 0.0F, 0.0F, 0.2182F);
        braceletL.cubeList.add(new ModelBox(braceletL, 16, 41, -1.2836F, -1.0237F, -1.0F, 3, 2, 3, -0.1F, false));

        armR = new ModelRenderer(this);
        armR.setRotationPoint(-2.0F, 0.0F, 0.0F);
        arms.addChild(armR);
        setRotationAngle(armR, 0.0F, 0.0F, 0.1309F);
        armR.cubeList.add(new ModelBox(armR, 0, 33, -2.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F, false));

        braceletR = new ModelRenderer(this);
        braceletR.setRotationPoint(-1.0F, 4.0F, -0.5F);
        armR.addChild(braceletR);
        setRotationAngle(braceletR, 0.0F, 0.0F, -0.2182F);
        braceletR.cubeList.add(new ModelBox(braceletR, 16, 41, -1.7164F, -1.0237F, -1.0F, 3, 2, 3, -0.1F, false));

        lower_body = new ModelRenderer(this);
        lower_body.setRotationPoint(0.0F, -6.0F, 0.0F);
        main.addChild(lower_body);


        legL = new ModelRenderer(this);
        legL.setRotationPoint(1.5F, 0.0F, 0.0F);
        lower_body.addChild(legL);
        legL.cubeList.add(new ModelBox(legL, 8, 42, -1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F, true));

        legR = new ModelRenderer(this);
        legR.setRotationPoint(-1.5F, 0.0F, 0.0F);
        lower_body.addChild(legR);
        legR.cubeList.add(new ModelBox(legR, 0, 42, -1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F, false));

        this.setupAllowedArrowSpots();
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        main.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    { super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn); }
}