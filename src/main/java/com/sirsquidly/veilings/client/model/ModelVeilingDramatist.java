package com.sirsquidly.veilings.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVeilingDramatist extends ModelVeilingBase
{
    public final ModelRenderer bone3;
    public final ModelRenderer bone2;
    public final ModelRenderer cube_r1;
    public final ModelRenderer bone5;
    public final ModelRenderer bone6;
    public final ModelRenderer bone7;
    public final ModelRenderer cube_r2;
    public final ModelRenderer earR;
    public final ModelRenderer earL;
    public final ModelRenderer braceletAL;
    public final ModelRenderer braceletAR;
    public final ModelRenderer braceletLL;
    public final ModelRenderer braceletLR;

    public ModelVeilingDramatist()
    {
        this(64, 64);
    }

    public ModelVeilingDramatist(int textureWidthIn, int textureHeightIn)
    {
        textureWidth = textureWidthIn;
        textureHeight = textureHeightIn;

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 24.0F, 0.0F);


        upper_body = new ModelRenderer(this);
        upper_body.setRotationPoint(0.0F, -7.0F, 0.0F);
        main.addChild(upper_body);


        head = new ModelRenderer(this);
        head.setRotationPoint(-0.5F, -8.0F, 0.25F);
        upper_body.addChild(head);
        head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -8.0F, -4.25F, 7, 8, 8, 0.0F, false));

        maskLayer = new ModelRenderer(this);
        maskLayer.setRotationPoint(3.0F, -6.0F, -4.25F);
        head.addChild(maskLayer);


        mask = new ModelRenderer(this);
        mask.setRotationPoint(-6.0F, 6.0F, 1.0F);
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

        earR = new ModelRenderer(this);
        earR.setRotationPoint(6.0F, -1.0F, 0.75F);
        head.addChild(earR);
        earR.cubeList.add(new ModelBox(earR, 32, 0, -12.0F, -5.0F, -1.0F, 3, 4, 0, 0.0F, false));

        earL = new ModelRenderer(this);
        earL.setRotationPoint(-5.0F, -1.0F, 0.75F);
        head.addChild(earL);
        earL.cubeList.add(new ModelBox(earL, 32, 4, 9.0F, -5.0F, -1.0F, 3, 4, 0, 0.0F, true));

        torso = new ModelRenderer(this);
        torso.setRotationPoint(-1.0F, -6.0F, -1.0F);
        upper_body.addChild(torso);
        torso.cubeList.add(new ModelBox(torso, 0, 16, -1.0F, -2.0F, -0.5F, 4, 7, 3, 0.0F, false));
        torso.cubeList.add(new ModelBox(torso, 14, 16, -2.0F, -1.9F, -1.0F, 6, 3, 4, 0.0F, false));

        wingL = new ModelRenderer(this);
        wingL.setRotationPoint(2.0F, -1.0F, 2.5F);
        torso.addChild(wingL);
        wingL.cubeList.add(new ModelBox(wingL, 34, 16, 0.0F, -1.0F, 0.0F, 0, 8, 8, 0.0F, false));

        wingR = new ModelRenderer(this);
        wingR.setRotationPoint(0.0F, -1.0F, 2.5F);
        torso.addChild(wingR);
        wingR.cubeList.add(new ModelBox(wingR, 34, 8, 0.0F, -1.0F, 0.0F, 0, 8, 8, 0.0F, false));

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, -7.0F, 0.0F);
        upper_body.addChild(arms);


        armL = new ModelRenderer(this);
        armL.setRotationPoint(2.0F, 0.0F, 0.0F);
        arms.addChild(armL);
        setRotationAngle(armL, 0.0F, 0.0F, -0.1309F);
        armL.cubeList.add(new ModelBox(armL, 8, 27, 0.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, true));
        armL.cubeList.add(new ModelBox(armL, 16, 30, -0.5F, 4.0F, -1.5F, 3, 4, 3, 0.0F, true));

        braceletAL = new ModelRenderer(this);
        braceletAL.setRotationPoint(1.0F, 6.0F, 0.0F);
        armL.addChild(braceletAL);
        setRotationAngle(braceletAL, 0.0F, 0.0F, 0.2182F);
        braceletAL.cubeList.add(new ModelBox(braceletAL, 16, 51, -1.9664F, -1.0237F, -2.0F, 4, 2, 4, -0.1F, false));

        armR = new ModelRenderer(this);
        armR.setRotationPoint(-2.0F, 0.0F, 0.0F);
        arms.addChild(armR);
        setRotationAngle(armR, 0.0F, 0.0F, 0.1309F);
        armR.cubeList.add(new ModelBox(armR, 0, 27, -2.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, false));
        armR.cubeList.add(new ModelBox(armR, 16, 23, -2.5F, 4.0F, -1.5F, 3, 4, 3, 0.0F, true));

        braceletAR = new ModelRenderer(this);
        braceletAR.setRotationPoint(-1.0F, 6.0F, 0.0F);
        armR.addChild(braceletAR);
        setRotationAngle(braceletAR, 0.0F, 0.0F, -0.2182F);
        braceletAR.cubeList.add(new ModelBox(braceletAR, 0, 51, -1.9664F, -1.0237F, -2.0F, 4, 2, 4, -0.1F, false));

        lower_body = new ModelRenderer(this);
        lower_body.setRotationPoint(0.0F, -8.0F, 0.0F);
        main.addChild(lower_body);


        legL = new ModelRenderer(this);
        legL.setRotationPoint(1.5F, 0.0F, 0.0F);
        lower_body.addChild(legL);
        legL.cubeList.add(new ModelBox(legL, 8, 40, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, true));
        legL.cubeList.add(new ModelBox(legL, 16, 44, -1.5F, 3.0F, -1.5F, 3, 4, 3, 0.0F, true));

        braceletLL = new ModelRenderer(this);
        braceletLL.setRotationPoint(0.0F, 5.0F, 0.0F);
        legL.addChild(braceletLL);
        setRotationAngle(braceletLL, 0.0F, 0.0F, 0.2182F);
        braceletLL.cubeList.add(new ModelBox(braceletLL, 16, 57, -1.9664F, -1.0237F, -2.0F, 4, 2, 4, -0.11F, false));

        legR = new ModelRenderer(this);
        legR.setRotationPoint(-1.5F, 0.0F, 0.0F);
        lower_body.addChild(legR);
        legR.cubeList.add(new ModelBox(legR, 0, 40, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));
        legR.cubeList.add(new ModelBox(legR, 16, 37, -1.5F, 3.0F, -1.5F, 3, 4, 3, 0.0F, true));

        braceletLR = new ModelRenderer(this);
        braceletLR.setRotationPoint(0.0F, 5.0F, 0.0F);
        legR.addChild(braceletLR);
        setRotationAngle(braceletLR, 0.0F, 0.0F, -0.2182F);
        braceletLR.cubeList.add(new ModelBox(braceletLR, 0, 57, -1.9664F, -1.0237F, -2.0F, 4, 2, 4, -0.1F, false));

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