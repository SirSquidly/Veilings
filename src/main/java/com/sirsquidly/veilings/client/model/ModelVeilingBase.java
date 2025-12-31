package com.sirsquidly.veilings.client.model;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* A shared base model for all Veilings.
 *
 * Note that actual placement of
* */
public class ModelVeilingBase extends ModelBase
{
    public ModelRenderer main;
    public ModelRenderer head;
    public ModelRenderer maskLayer;
    public ModelRenderer mask;
    public ModelRenderer torso;
    public ModelRenderer wingL;
    public ModelRenderer wingR;
    public ModelRenderer armL;
    public ModelRenderer armR;
    public ModelRenderer legL;
    public ModelRenderer legR;
    public ModelRenderer arms;
    public ModelRenderer upper_body;
    public ModelRenderer lower_body;
    public AbstractVeiling.PoseBody bodyPose;
    private final List<ModelRenderer> allowedArrowSpots = new ArrayList<>();

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        AbstractVeiling veiling = (AbstractVeiling) entityIn;
        this.bodyPose = veiling.getArmPose();
        boolean holding = !veiling.getHeldItemMainhand().isEmpty();
        float animationTime = veiling.getClientAnimationTime(ageInTicks - (float)veiling.ticksExisted);

        float sadness = veiling.getMood() * 0.01F;
        float sadnessInverse = 1.0F - sadness;

        float f = 1.0F;

        if (false)
        {
            f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F)
        {
            f = 1.0F;
        }

        this.main.rotateAngleX = 0;
        this.main.rotateAngleY = 0;
        this.main.rotateAngleZ = 0;
        this.main.offsetX = 0F;
        this.main.offsetY = 0F;
        this.main.offsetZ = 0F;
        this.head.rotateAngleX = headPitch * 0.017453292F + (0.3F * sadnessInverse);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.offsetX = 0;
        this.torso.rotateAngleY = 0;
        this.upper_body.rotateAngleX = headPitch * 0.005F + (0.2F * sadnessInverse);
        this.upper_body.rotateAngleY = 0;
        this.upper_body.offsetY = 0;
        this.arms.offsetZ = 0;
        this.arms.rotateAngleX = 0;
        this.arms.rotateAngleY = 0;
        this.arms.rotateAngleZ = 0;
        this.armR.offsetY = 0F;
        this.armR.offsetZ = 0F;
        this.armL.offsetY = 0F;
        this.armL.offsetZ = 0F;
        this.armR.rotateAngleX = 0F;
        this.armL.rotateAngleX = 0F;
        this.armR.rotateAngleY = 0F;
        this.armL.rotateAngleY = 0F;
        this.armR.rotateAngleZ = 0F;
        this.armL.rotateAngleZ = 0F;
        this.armR.rotationPointZ = 0F;
        this.armL.rotationPointZ = 0F;
        this.lower_body.rotateAngleX = 0;
        this.lower_body.offsetY = 0F;
        this.lower_body.offsetZ = 0F;
        this.legR.rotateAngleZ = 0F;
        this.legL.rotateAngleZ = 0F;


        if (this.bodyPose == AbstractVeiling.PoseBody.EMPTY)
        {
            this.armR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
            this.armL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        }

        this.armR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F + (0.2F * sadness);
        this.armL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F + (0.2F * sadness);
        this.armR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.armL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        this.legR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        this.legL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;


        this.wingR.rotateAngleY = 0;
        this.wingL.rotateAngleY = 0;
        this.wingR.rotateAngleY -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.wingL.rotateAngleY += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;


        /* Adapted from vanilla's arm swing for bipeds. */
        if (this.swingProgress > 0.0F)
        {
            float f1 = this.swingProgress;

            if (!holding)
            {
                this.torso.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

                this.armR.rotationPointZ = MathHelper.sin(this.torso.rotateAngleY) * 5F;
                this.armL.rotationPointZ = -MathHelper.sin(this.torso.rotateAngleY) * 5F;
                this.armR.rotateAngleY += this.torso.rotateAngleY;
                this.armL.rotateAngleY += this.torso.rotateAngleY;

                f1 = 1F - f1;
                f1 *= f1 * f1 * f1;
                f1 = 1F - f1;

                float f2 = MathHelper.sin(f1 * (float)Math.PI);
                float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
                armR.rotateAngleX = (float)((double)armR.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
                armR.rotateAngleY += this.torso.rotateAngleY * 2.0F;
                armR.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
            }
            else
            {
                this.upper_body.rotateAngleX += MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F * (veiling.getPrimaryHand() == EnumHandSide.LEFT ? -1 : 1);
                this.upper_body.rotateAngleY = (MathHelper.sin(this.upper_body.rotateAngleX) * 2F);
                //this.arms.rotateAngleX = 0.0F + (MathHelper.sin(this.upper_body.rotateAngleX) * 2F);
                this.arms.rotateAngleY += 1.2F + (MathHelper.sin(this.upper_body.rotateAngleX) * 5F);
            }
        }

        if (holding && this.bodyPose == AbstractVeiling.PoseBody.EMPTY)
        {
            this.arms.rotateAngleX = -1F;
            this.armL.rotateAngleX = 0F;
            this.armR.rotateAngleX = 0F;
            this.armR.rotateAngleZ = -0.4F;
            this.armL.rotateAngleZ = -this.armR.rotateAngleZ;
        }

        switch (AbstractVeiling.PoseBody.values()[veiling.previousPose])
        {
            case EMPTY:
                break;
            case DANCING:
                float danceMotion = MathHelper.sin((ageInTicks) * 0.8F) * 0.3F;
                float danceMotionSlower = MathHelper.sin((ageInTicks) * 0.4F) * 0.3F;

                switch (veiling.getDanceType())
                {
                    case 0:
                        this.main.offsetY = (-0.1F + Math.abs(danceMotionSlower * 0.3F)) * animationTime;
                        this.head.offsetX = (danceMotion * 0.4F) * animationTime;
                        this.upper_body.offsetY = (-danceMotion * 0.1F) * animationTime;
                        this.armR.rotateAngleX = (-1.4F + danceMotion + headPitch * 0.017453292F) * animationTime;
                        this.armL.rotateAngleX = (-1.4F + danceMotion + headPitch * 0.017453292F) * animationTime;
                        this.armR.rotateAngleY = (0.2F * danceMotion) * animationTime;
                        this.armL.rotateAngleY = (-0.2F * danceMotion) * animationTime;
                        this.legL.rotateAngleZ = (danceMotionSlower * -2 < 0 ? danceMotionSlower * -2 : 0) * animationTime;
                        this.legR.rotateAngleZ = (danceMotionSlower * -2 > 0 ? danceMotionSlower * -2 : 0) * animationTime;
                        break;
                    case 1:
                        this.upper_body.rotateAngleX = Math.abs(danceMotionSlower * 0.7F) * animationTime;
                        this.armR.rotateAngleZ = 1.3F * animationTime;
                        this.armR.rotateAngleX = ((danceMotionSlower * 5) - 1F) * animationTime;
                        this.armL.rotateAngleZ = -this.armR.rotateAngleZ;
                        this.armL.rotateAngleX = ((danceMotionSlower * -5) - 1F) * animationTime;

                        this.legL.rotateAngleX = (danceMotionSlower * -2) * animationTime;
                        this.legL.rotateAngleZ = (danceMotionSlower * -2 < 0 ? danceMotionSlower * -2 : 0) * animationTime;
                        this.legR.rotateAngleX = (danceMotionSlower * 2) * animationTime;
                        this.legR.rotateAngleZ = (danceMotionSlower * -2 > 0 ? danceMotionSlower * -2 : 0) * animationTime;
                        break;
                    case 2:
                        /* Russian Kick */
                        this.main.offsetY = (-0.1F + Math.abs(danceMotionSlower * 0.3F)) * animationTime;
                        this.upper_body.rotateAngleX = (Math.abs(danceMotionSlower * 0.7F)) * animationTime;
                        this.upper_body.offsetY = (-0.1F + Math.abs(danceMotionSlower * 0.6F)) * animationTime;

                        this.armR.rotateAngleX = -1.4F * animationTime;
                        this.armL.rotateAngleX = this.armR.rotateAngleX;
                        this.armR.rotateAngleY = -1.2F * animationTime;
                        this.armL.rotateAngleY = -this.armR.rotateAngleY;

                        this.legL.rotateAngleX = (-0.6F + (danceMotionSlower * -3)) * animationTime;
                        this.legR.rotateAngleX = (-0.6F + (danceMotionSlower * 3)) * animationTime;
                        break;
                    case 3:
                        /* Busting it DOWN BACKWARDS YOOOOO */
                        this.main.offsetZ = -0.2F * animationTime;
                        this.upper_body.rotateAngleX = -1.2F * animationTime;
                        this.upper_body.offsetY = (-danceMotion * 0.2F) * animationTime;

                        this.armR.rotateAngleX = (danceMotionSlower * -6) * animationTime;
                        this.armL.rotateAngleX = -this.armR.rotateAngleX;

                        this.armR.offsetZ = (-danceMotionSlower * 0.6F) * animationTime;
                        this.armL.offsetZ = (danceMotionSlower * 0.6F) * animationTime;

                        this.lower_body.rotateAngleX = -1.0F * animationTime;
                        this.lower_body.offsetY = (0.03F - (danceMotion * 0.2F)) * animationTime;
                        this.legL.rotateAngleX = (danceMotionSlower * -6) * animationTime;
                        this.legR.rotateAngleX = (danceMotionSlower * 6) * animationTime;
                        break;
                    case 4:
                        /* Simple head bob. */
                        this.head.rotateAngleX += (0.3F + (danceMotionSlower * 0.8F)) * animationTime;
                        this.upper_body.rotateAngleX = (danceMotionSlower * 0.1F) * animationTime;
                        break;
                    case 5:
                        this.main.rotateAngleY = (danceMotionSlower * 0.5F) * animationTime;
                        this.upper_body.rotateAngleY = (danceMotionSlower * 1.5F) * animationTime;
                        this.armR.rotateAngleX = (-1.4F + (danceMotionSlower * 2F) + headPitch * 0.017453292F) * animationTime;
                        this.armL.rotateAngleX = (-1.4F - (danceMotionSlower * 2F) + headPitch * 0.017453292F) * animationTime;
                        break;
                    case 6:
                        /* That one kid from Charlie Brown with that shuffly side-to-side thing. */
                        this.main.rotateAngleY = (danceMotionSlower * 0.5F) * animationTime;
                        this.upper_body.rotateAngleY = (danceMotionSlower * 2F) * animationTime;
                        this.head.rotateAngleY = danceMotionSlower * animationTime;
                        this.head.rotateAngleX = Math.abs(danceMotionSlower * 2) * animationTime;

                        this.armR.offsetY = (danceMotionSlower * 0.4F) * animationTime;
                        this.armL.offsetY = -this.armR.offsetY;

                        this.legR.rotateAngleY = (danceMotionSlower * 1F) * animationTime;
                        this.legR.rotateAngleZ = (danceMotionSlower * -2 < 0 ? danceMotionSlower * 2 : 0) * animationTime;

                        this.legL.rotateAngleY = this.legR.rotateAngleY;
                        this.legL.rotateAngleZ = (danceMotionSlower * -2 > 0 ? danceMotionSlower * 2 : 0) * animationTime;
                        break;
                    case 7:
                        /* Side kick boogie inspired by the Dramatist getting dance code mixed up. */
                        this.main.offsetY = (-0.1F + Math.abs(danceMotionSlower * 0.3F)) * animationTime;
                        this.main.rotateAngleY = (danceMotionSlower * 0.5F) * animationTime;
                        this.upper_body.offsetY = (-0.1F + Math.abs(danceMotionSlower * 0.3F)) * animationTime;
                        this.head.offsetX = (danceMotion * 0.4F) * animationTime;
                        this.head.rotateAngleX += (0.3F + (danceMotionSlower * 0.8F)) * animationTime;
                        this.legL.rotateAngleZ = (danceMotionSlower * -2 < 0 ? danceMotionSlower * -2 : 0) * animationTime;
                        this.legR.rotateAngleZ = (danceMotionSlower * -2 > 0 ? danceMotionSlower * -2 : 0) * animationTime;
                        break;
                }
                break;
            case CASTING:
                this.arms.rotateAngleX += -3.1F * animationTime;

                this.armR.rotateAngleY = -0.3F * animationTime;
                this.armL.rotateAngleY = -this.armR.rotateAngleY;
                break;
            case CRYING:
                float cryMotion = MathHelper.sin((ageInTicks) * 1.5F) * 0.1F;

                this.upper_body.rotateAngleY = cryMotion;
                this.head.rotateAngleX = 0.5F * animationTime;

                this.arms.offsetZ = -0.1F * animationTime;
                this.arms.rotateAngleX += -2F * animationTime;

                this.armR.rotateAngleY = -0.3F * animationTime;
                this.armL.rotateAngleY = -this.armR.rotateAngleY;
                break;
            case SLEEPING:
                switch (veiling.getSitType())
                {
                    default:
                    case 0:
                        /* Generic on back. */
                        this.main.offsetZ = -0.5F * animationTime;
                        this.main.rotateAngleX = -1.5F * animationTime;
                        break;
                    case 1:
                        /* On front. */
                        this.main.offsetZ = 0.5F * animationTime;
                        this.main.rotateAngleX = 1.5F * animationTime;

                        this.legL.rotateAngleZ = -0.2F * animationTime;
                        this.legR.rotateAngleZ = -this.legL.rotateAngleZ;

                        this.armL.rotateAngleZ = -0.4F * animationTime;
                        this.armR.rotateAngleZ = -this.armL.rotateAngleZ;
                        break;
                    case 2:
                        /* Curled on side. */
                        this.main.offsetY = -0.3F * animationTime;
                        this.main.offsetX = 0.3F * animationTime;
                        this.main.rotateAngleZ = -1.5F * animationTime;
                        this.upper_body.offsetY = 0.4F * animationTime;

                        this.lower_body.offsetY = 0.1F * animationTime;
                        this.lower_body.rotateAngleX = -0.4F * animationTime;
                        this.lower_body.offsetZ = -0.15F * animationTime;

                        this.legL.rotateAngleZ = -0.1F * animationTime;
                        this.legR.rotateAngleZ = -this.legL.rotateAngleZ;

                        this.arms.rotateAngleX = -1.2F * animationTime;
                        this.armR.rotateAngleZ = -0.2F * animationTime;
                        this.armL.rotateAngleZ = -this.armR.rotateAngleZ;
                        break;
                    case 3:
                        /* Generic on back. */
                        this.main.offsetZ = -0.5F * animationTime;
                        this.main.rotateAngleX = -1.5F * animationTime;
                        break;
                }
                break;
            case SITTING:
                switch (veiling.getSitType())
                {
                    default:
                    case 0:
                        /* Normal sitting. */
                        this.main.offsetY = 0.3F * animationTime;
                        this.lower_body.offsetZ = -0.05F * animationTime;
                        this.lower_body.rotateAngleX = -1.5F * animationTime;
                        this.legL.rotateAngleZ = -0.2F * animationTime;
                        this.legR.rotateAngleZ = -this.legL.rotateAngleZ;
                        break;
                    case 1:
                        /* Lean back sitting. */
                        this.main.offsetY = 0.3F * animationTime;
                        this.lower_body.rotateAngleX = -1.5F * animationTime;
                        this.legL.rotateAngleZ = -0.2F * animationTime;
                        this.legR.rotateAngleZ = -this.legL.rotateAngleZ;

                        this.upper_body.rotateAngleX = -0.3F * animationTime;
                        this.arms.rotateAngleX = 0.4F * animationTime;
                        break;
                    case 2:
                        /* Hugging knees. */
                        this.main.offsetY = 0.0F;
                        this.upper_body.offsetY = 0.4F * animationTime;

                        this.lower_body.offsetY = 0.1F * animationTime;
                        this.lower_body.rotateAngleX = -0.4F * animationTime;
                        this.lower_body.offsetZ = -0.15F * animationTime;

                        this.legL.rotateAngleZ = -0.1F * animationTime;
                        this.legR.rotateAngleZ = -this.legL.rotateAngleZ;

                        this.arms.rotateAngleX = -1.2F * animationTime;
                        this.armR.rotateAngleZ = -0.2F * animationTime;
                        this.armL.rotateAngleZ = -this.armR.rotateAngleZ;
                        break;
                    case 3:
                        /* Criss-Cross. */
                        this.main.offsetY = 0.3F * animationTime;
                        this.lower_body.offsetZ = -0.05F * animationTime;
                        this.lower_body.rotateAngleX = -1.5F * animationTime;
                        this.legL.rotateAngleZ = 1F * animationTime;
                        this.legR.rotateAngleZ = -this.legL.rotateAngleZ;

                        this.arms.rotateAngleX = -1.0F * animationTime;
                        this.armR.rotateAngleZ = -0.3F * animationTime;
                        this.armL.rotateAngleZ = -this.armR.rotateAngleZ;
                        break;
                }
                break;
            case BEGGING:
                float begSwing = MathHelper.sin((ageInTicks) * 0.8F) * 0.3F;
                this.armR.rotateAngleX = (-1.4F + begSwing + headPitch * 0.017453292F) * animationTime;
                this.armL.rotateAngleX = (-1.4F + begSwing + headPitch * 0.017453292F) * animationTime;
                this.armR.rotateAngleY = (0.2F * begSwing) * animationTime;
                this.armL.rotateAngleY = (-0.2F * begSwing) * animationTime;
                break;
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setupAllowedArrowSpots()
    {
        allowedArrowSpots.add(head);
        allowedArrowSpots.add(torso);
        allowedArrowSpots.add(armL);
        allowedArrowSpots.add(armR);
        allowedArrowSpots.add(legL);
        allowedArrowSpots.add(legR);
    }

    /** Only pull a spot from MY list! */
    public ModelRenderer getRandomModelBox(Random rand)
    { return allowedArrowSpots.get(rand.nextInt(allowedArrowSpots.size())); }
}