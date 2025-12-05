package com.sirsquidly.veilings.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

// TODO: Add Crafting Recipe
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

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);


        playerIn.swingArm(handIn);

        if (!worldIn.isRemote)
        {
            this.setColor(stack, EnumDyeColor.byMetadata(playerIn.world.rand.nextInt(16)).getColorValue());
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
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
        //tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings." + MASK_NAME[maskType] + ".desc1"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted(" +4 Armor"));
    }
}