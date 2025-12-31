package com.sirsquidly.veilings.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemVeilingTreat extends ItemFood
{
    public ItemVeilingTreat(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        //this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.veiling_tiramisu.desc1"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.veiling_tiramisu.desc2"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.veiling_tiramisu.desc3"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.veiling_tiramisu.effect", StringUtils.ticksToElapsedTime(30 * 20)));
    }
}