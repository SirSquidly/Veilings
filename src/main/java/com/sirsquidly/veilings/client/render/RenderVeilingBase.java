package com.sirsquidly.veilings.client.render;

import com.sirsquidly.veilings.client.model.ModelVeilingDeft;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** This class exists to be inherited by all Veilings, so they can use the same layer classes. */
@SideOnly(Side.CLIENT)
public class RenderVeilingBase<T extends AbstractVeiling> extends RenderLiving<T>
{
    public RenderVeilingBase(RenderManager managerIn)
    { this(managerIn, new ModelVeilingDeft(), 0.2F); }

    public RenderVeilingBase(RenderManager managerIn, ModelBase modelIn, float shadowIn)
    { super(managerIn, modelIn, shadowIn); }

    protected ResourceLocation getEntityTexture(T entity) { return null; }
}