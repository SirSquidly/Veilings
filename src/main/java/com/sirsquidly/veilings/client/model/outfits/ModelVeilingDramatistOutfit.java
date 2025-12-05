package com.sirsquidly.veilings.client.model.outfits;

import com.sirsquidly.veilings.client.model.ModelVeilingDramatist;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVeilingDramatistOutfit extends ModelVeilingDramatist
{
    private final ModelRenderer outfit_torso;
    private final ModelRenderer outfit_armL;
    private final ModelRenderer outfit_armR;
    private final ModelRenderer poncho;
    private final ModelRenderer outfit_legL;
    private final ModelRenderer outfit_legR;
    private final ModelRenderer dress;

    public ModelVeilingDramatistOutfit()
    {
        /* Remove any render overlap on the main body, this uses its own model. */
        super(0,0);
        textureWidth = 64;
        textureHeight = 64;

        outfit_torso = new ModelRenderer(this);
        outfit_torso.cubeList.add(new ModelBox(outfit_torso, 44, 22, -2.0F, -1.9F, -1.0F, 6, 3, 4, 0.01F, false));
        outfit_torso.cubeList.add(new ModelBox(outfit_torso, 0, 16, -1.0F, -1.0F, -0.5F, 4, 6, 3, 0.01F, false));

        outfit_armL = new ModelRenderer(this);
        outfit_armL.cubeList.add(new ModelBox(outfit_armL, 8, 33, 0.0F, -1.0F, -1.0F, 2, 10, 2, 0.01F, true));

        outfit_armR = new ModelRenderer(this);
        outfit_armR.cubeList.add(new ModelBox(outfit_armR, 0, 33, -2.0F, -1.0F, -1.0F, 2, 10, 2, 0.01F, false));

        poncho = new ModelRenderer(this);
        poncho.cubeList.add(new ModelBox(poncho, 30, 50, -6.0F, -8.5F, -2.5F, 12, 9, 5, 0.01F, false));

        outfit_legL = new ModelRenderer(this);
        outfit_legL.cubeList.add(new ModelBox(outfit_legL, 8, 45, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.01F, true));

        outfit_legR = new ModelRenderer(this);
        outfit_legR.cubeList.add(new ModelBox(outfit_legR, 0, 45, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.01F, false));

        dress = new ModelRenderer(this);
        dress.cubeList.add(new ModelBox(dress, 44, 38, -3.0F, -2.0F, -2.0F, 6, 8, 4, 0.01F, false));

        torso.addChild(outfit_torso);
        upper_body.addChild(poncho);
        lower_body.addChild(dress);
        armL.addChild(outfit_armL);
        armR.addChild(outfit_armR);
        legL.addChild(outfit_legL);
        legR.addChild(outfit_legR);
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    { this.main.render(scale); }
}