package com.sirsquidly.veilings.common.item;

import com.sirsquidly.veilings.client.model.mask.ModelCustodianMask;
import com.sirsquidly.veilings.client.model.mask.ModelDeftMask;
import com.sirsquidly.veilings.client.model.mask.ModelDramatistMask;
import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingCustodian;
import com.sirsquidly.veilings.common.entity.EntityVeilingDeft;
import com.sirsquidly.veilings.common.entity.EntityVeilingDramatist;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemVeilingMask extends ItemArmor
{
    private final String CUSTODIAN_TEXTURE = veilings.MOD_ID + ":textures/entities/veiling/veiling_custodian.png";
    private final String DEFT_TEXTURE = veilings.MOD_ID + ":textures/entities/veiling/veiling_deft.png";
    private final String DRAMATIST_TEXTURE = veilings.MOD_ID + ":textures/entities/veiling/veiling_dramatist.png";
    private final String[] ARMOR_TEXTURES = new String[] {DEFT_TEXTURE, CUSTODIAN_TEXTURE, DRAMATIST_TEXTURE};
    private final ModelBiped[] ARMOR_MODELS = new ModelBiped[] {new ModelDeftMask(0.0F), new ModelCustodianMask(0.0F), new ModelDramatistMask(0.0F)};

    int maskType;
    private final String[] MASK_NAME = new String[] {"mask_deft", "mask_custodian", "mask_dramatist"};

    public ItemVeilingMask(ArmorMaterial materialIn, int maskTypeIn)
    {
        super(materialIn, 0, EntityEquipmentSlot.HEAD);
        this.setCreativeTab(CreativeTabs.MATERIALS);
        maskType = maskTypeIn;
        setMaxStackSize(1);
    }

    /** Literally just gets a random Veiling type. */
    public AbstractVeiling getVeilingType(World world)
    {
        switch (this.getMaskType())
        {
            case 0: return new EntityVeilingDeft(world);
            case 1: return new EntityVeilingCustodian(world);
            case 2: return new EntityVeilingDramatist(world);
        }

        return null;
    }

    public int getMaskType() { return this.maskType; }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
    { return ARMOR_MODELS[maskType]; }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    { return ARMOR_TEXTURES[maskType]; }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings." + MASK_NAME[maskType] + ".desc1"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings." + MASK_NAME[maskType] + ".desc2"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings." + MASK_NAME[maskType] + ".desc3"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings." + MASK_NAME[maskType] + ".desc4"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings." + MASK_NAME[maskType] + ".desc5"));
    }
}