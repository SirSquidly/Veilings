package com.sirsquidly.veilings.common.item;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingCustodian;
import com.sirsquidly.veilings.common.entity.EntityVeilingDeft;
import com.sirsquidly.veilings.common.entity.EntityVeilingDramatist;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemVeilingMask extends Item
{
    int maskType;
    private final String[] MASK_NAME = new String[] {"mask_deft", "mask_custodian", "mask_dramatist"};

    public ItemVeilingMask(int maskTypeIn)
    {
        this.setCreativeTab(CreativeTabs.MATERIALS);
        maskType = maskTypeIn;
        setMaxStackSize(1);
    }

    /** Literally just gets a random Veiling type. */
    public AbstractVeiling getVeilingType(World world)
    {
        switch (maskType)
        {
            case 0: return new EntityVeilingDeft(world);
            case 1: return new EntityVeilingCustodian(world);
            case 2: return new EntityVeilingDramatist(world);
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings." + MASK_NAME[maskType] + ".desc1"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings." + MASK_NAME[maskType] + ".desc2"));
    }
}