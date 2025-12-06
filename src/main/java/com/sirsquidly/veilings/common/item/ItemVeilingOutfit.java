package com.sirsquidly.veilings.common.item;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemVeilingOutfit extends Item
{
    int outfitType;
    String outfitVariant;
    boolean hasOverlay;

    public ItemVeilingOutfit(int outfitTypeIn, String outfitVariantIn, boolean hasOverlayIn)
    {
        this.setCreativeTab(CreativeTabs.MATERIALS);
        outfitType = outfitTypeIn;
        outfitVariant = outfitVariantIn;
        hasOverlay = hasOverlayIn;
        setMaxStackSize(1);
    }

    /** Allows Cauldron Cleaning of the Outfit's Dye. */
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() != this) return EnumActionResult.PASS;
        if (!((ItemVeilingOutfit)itemstack.getItem()).hasColor(itemstack)) return EnumActionResult.PASS;

        IBlockState state = world.getBlockState(pos);

        if (state.getBlock() == Blocks.CAULDRON)
        {
            BlockCauldron cauldron = (BlockCauldron)state.getBlock();
            int level = state.getValue(BlockCauldron.LEVEL);
            if (level <= 0) return EnumActionResult.PASS;

            this.removeColor(itemstack);
            cauldron.setWaterLevel(world, pos, state, level - 1);
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

    public boolean hasColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
    }


    public int getColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
            { return nbttagcompound1.getInteger("color"); }
        }

        return 16777215;
    }

    public void removeColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color"))
            { nbttagcompound1.removeTag("color"); }
        }
    }

        public void setColor(ItemStack stack, int color)
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound == null)
            {
                nbttagcompound = new NBTTagCompound();
                stack.setTagCompound(nbttagcompound);
            }

            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (!nbttagcompound.hasKey("display", 10))
            { nbttagcompound.setTag("display", nbttagcompound1); }

            nbttagcompound1.setInteger("color", color);
        }


    public int getVeilingOutfitType() { return outfitType; }

    public String getVeilingOutfitVariant() { return outfitVariant; }

    public boolean getVeilingOutfitHasOverlay() { return hasOverlay; }


    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("description.veilings.veiling_outfit.desc1"));
        tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("description.veilings.veiling_outfit.desc2"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.veiling_outfit.desc3"));
    }
}