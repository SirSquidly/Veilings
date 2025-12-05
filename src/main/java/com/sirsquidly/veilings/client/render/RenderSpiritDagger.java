package com.sirsquidly.veilings.client.render;

import com.sirsquidly.veilings.common.entity.EntitySpiritDagger;
import com.sirsquidly.veilings.init.VeilingsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpiritDagger extends Render<EntitySpiritDagger>
{
    protected final ItemStack stack;

    public RenderSpiritDagger(RenderManager manager)
    {
        super(manager);
        this.stack = new ItemStack(VeilingsItems.SPIRIT_DAGGER);
    }

    @Override
    public void doRender(EntitySpiritDagger entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.translate(0, 0.1, 0);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(45.0F, 0.0F, 0.0F, -1.0F);
        GlStateManager.scale(0.875F, 0.875F, 0.875F);
        Minecraft.getMinecraft().getRenderItem().renderItem(this.stack, ItemCameraTransforms.TransformType.NONE);
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySpiritDagger entity)
    { return null;  }
}