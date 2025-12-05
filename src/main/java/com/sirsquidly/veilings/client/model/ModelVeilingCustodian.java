package com.sirsquidly.veilings.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVeilingCustodian extends ModelVeilingBase
{
    public final ModelRenderer earR;
    public final ModelRenderer ringR_r1;
    public final ModelRenderer earL;
    public final ModelRenderer braceletAL1;
    public final ModelRenderer braceletAL2;
    public final ModelRenderer braceletAR;

    public ModelVeilingCustodian()
    {
        this(64, 64);
    }

    public ModelVeilingCustodian(int textureWidthIn, int textureHeightIn)
    {
        textureWidth = textureWidthIn;
        textureHeight = textureHeightIn;

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 23.0F, 0.0F);


        upper_body = new ModelRenderer(this);
        upper_body.setRotationPoint(0.0F, -5.0F, 0.0F);
        main.addChild(upper_body);


        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -10.0F, -0.75F);
        upper_body.addChild(head);
        head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -5.0F, -4.25F, 8, 8, 8, 0.0F, false));

        maskLayer = new ModelRenderer(this);
        maskLayer.setRotationPoint(3.0F, -6.0F, -4.25F);
        head.addChild(maskLayer);


        mask = new ModelRenderer(this);
        mask.setRotationPoint(-6.0F, 6.0F, 1.0F);
        maskLayer.addChild(mask);
        mask.cubeList.add(new ModelBox(mask, 32, 0, -1.0F, -9.0F, -1.0F, 8, 12, 8, 0.5F, false));

        earR = new ModelRenderer(this);
        earR.setRotationPoint(6.0F, -1.0F, 0.75F);
        head.addChild(earR);
        earR.cubeList.add(new ModelBox(earR, 32, 0, -13.0F, -2.0F, -1.0F, 3, 4, 0, 0.0F, false));

        ringR_r1 = new ModelRenderer(this);
        ringR_r1.setRotationPoint(-13.0F, 1.0F, -1.0F);
        earR.addChild(ringR_r1);
        setRotationAngle(ringR_r1, -0.7854F, 0.0F, 0.0F);
        ringR_r1.cubeList.add(new ModelBox(ringR_r1, 44, 42, 0.0F, -1.0F, -1.0F, 1, 1, 1, 0.0F, false));

        earL = new ModelRenderer(this);
        earL.setRotationPoint(-5.0F, -1.0F, 0.75F);
        head.addChild(earL);
        earL.cubeList.add(new ModelBox(earL, 32, 4, 9.0F, -2.0F, -1.0F, 3, 4, 0, 0.0F, true));

        torso = new ModelRenderer(this);
        torso.setRotationPoint(-1.0F, -8.0F, -1.0F);
        upper_body.addChild(torso);
        torso.cubeList.add(new ModelBox(torso, 20, 17, -1.0F, 5.0F, -0.5F, 4, 2, 3, 0.0F, false));
        torso.cubeList.add(new ModelBox(torso, 0, 16, -2.0F, 1.1F, -1.0F, 6, 4, 4, 0.0F, false));

        wingL = new ModelRenderer(this);
        wingL.setRotationPoint(2.0F, -1.0F, 2.5F);
        torso.addChild(wingL);
        wingL.cubeList.add(new ModelBox(wingL, 34, 20, 0.0F, 2.0F, 0.0F, 0, 8, 8, 0.0F, false));

        wingR = new ModelRenderer(this);
        wingR.setRotationPoint(0.0F, -1.0F, 2.5F);
        torso.addChild(wingR);
        wingR.cubeList.add(new ModelBox(wingR, 34, 12, 0.0F, 2.0F, 0.0F, 0, 8, 8, 0.0F, false));

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, -6.0F, 0.0F);
        upper_body.addChild(arms);


        armL = new ModelRenderer(this);
        armL.setRotationPoint(3.0F, 0.0F, 0.0F);
        arms.addChild(armL);
        setRotationAngle(armL, 0.0F, 0.0F, -0.2182F);
        armL.cubeList.add(new ModelBox(armL, 12, 25, -0.7186F, -1.171F, -1.5F, 3, 10, 3, 0.0F, true));
        armL.cubeList.add(new ModelBox(armL, 12, 38, -0.7186F, -1.171F, -1.5F, 3, 10, 3, 0.25F, true));

        braceletAL1 = new ModelRenderer(this);
        braceletAL1.setRotationPoint(0.673F, 3.8547F, 0.0F);
        armL.addChild(braceletAL1);
        setRotationAngle(braceletAL1, 0.0F, 0.0F, 0.2182F);
        braceletAL1.cubeList.add(new ModelBox(braceletAL1, 48, 32, -1.9664F, -1.0237F, -2.0F, 4, 2, 4, -0.1F, true));

        braceletAL2 = new ModelRenderer(this);
        braceletAL2.setRotationPoint(0.673F, 7.8547F, 0.0F);
        armL.addChild(braceletAL2);
        setRotationAngle(braceletAL2, 0.0F, 0.0F, 0.2182F);
        braceletAL2.cubeList.add(new ModelBox(braceletAL2, 48, 38, -1.9664F, -1.0237F, -2.0F, 4, 2, 4, -0.1F, true));

        armR = new ModelRenderer(this);
        armR.setRotationPoint(-3.0F, 0.0F, 0.0F);
        arms.addChild(armR);
        setRotationAngle(armR, 0.0F, 0.0F, 0.2618F);
        armR.cubeList.add(new ModelBox(armR, 0, 25, -2.419F, -1.1823F, -1.5F, 3, 10, 3, 0.0F, false));
        armR.cubeList.add(new ModelBox(armR, 0, 38, -2.419F, -1.1823F, -1.5F, 3, 10, 3, 0.25F, false));

        braceletAR = new ModelRenderer(this);
        braceletAR.setRotationPoint(-0.8105F, 2.8434F, 0.0F);
        armR.addChild(braceletAR);
        setRotationAngle(braceletAR, 0.0F, 0.0F, -0.2182F);
        braceletAR.cubeList.add(new ModelBox(braceletAR, 32, 36, -1.9664F, -1.0237F, -2.0F, 4, 2, 4, -0.1F, false));

        lower_body = new ModelRenderer(this);
        lower_body.setRotationPoint(0.0F, -6.0F, 0.0F);
        main.addChild(lower_body);

        legL = new ModelRenderer(this);
        legL.setRotationPoint(1.5F, 0.0F, 0.0F);
        lower_body.addChild(legL);
        legL.cubeList.add(new ModelBox(legL, 8, 55, -1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F, true));

        legR = new ModelRenderer(this);
        legR.setRotationPoint(-1.5F, 0.0F, 0.0F);
        lower_body.addChild(legR);
        legR.cubeList.add(new ModelBox(legR, 0, 55, -1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F, false));

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