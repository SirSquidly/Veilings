package com.sirsquidly.veilings.common.item;

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

public class ItemVeilingNail extends Item
{
    float upgradeValue;
    String upgradeString;

    public ItemVeilingNail(String stringIn, float valueIn)
    {
        this.setCreativeTab(CreativeTabs.MATERIALS);
        this.setMaxStackSize(1);

        upgradeString = stringIn;
        upgradeValue = valueIn;
    }

    public String getUpgradeString() { return this.upgradeString; }

    public float getUpgradeValue() { return this.upgradeValue; }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.nail." + this.getUpgradeString() + ".desc1"));
    }
}