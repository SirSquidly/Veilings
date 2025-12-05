package com.sirsquidly.veilings.util;

import com.google.common.collect.Lists;
import com.sirsquidly.veilings.common.item.ItemVeilingOutfit;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class VeilingOutfitDyeingRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        List<ItemStack> dyes = Lists.newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack slotStack = inv.getStackInSlot(i);

            if (!slotStack.isEmpty())
            {
                if (slotStack.getItem() instanceof ItemVeilingOutfit)
                {
                    itemstack = slotStack.copy();
                }
                else
                {
                    if (!net.minecraftforge.oredict.DyeUtils.isDye(slotStack))  return false;
                    dyes.add(slotStack);
                }
            }
        }

        return !itemstack.isEmpty() && !dyes.isEmpty();
    }

    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;


        for (int k = 0; k < inv.getSizeInventory(); ++k)
        {
            ItemStack slotStack = inv.getStackInSlot(k);

            if (!slotStack.isEmpty())
            {
                if (slotStack.getItem() instanceof ItemVeilingOutfit)
                {
                    itemstack = slotStack.copy();
                    itemstack.setCount(1);
                    ItemVeilingOutfit outfit = ((ItemVeilingOutfit)itemstack.getItem());

                    if (outfit.hasColor(slotStack))
                    {
                        int l = outfit.getColor(itemstack);
                        float f = (float)(l >> 16 & 255) / 255.0F;
                        float f1 = (float)(l >> 8 & 255) / 255.0F;
                        float f2 = (float)(l & 255) / 255.0F;
                        i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int)((float)aint[0] + f * 255.0F);
                        aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                        aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                        ++j;
                    }
                }
                else
                {
                    if (!net.minecraftforge.oredict.DyeUtils.isDye(slotStack))
                    { return ItemStack.EMPTY; }

                    float[] afloat = net.minecraftforge.oredict.DyeUtils.colorFromStack(slotStack).get().getColorComponentValues();
                    int l1 = (int)(afloat[0] * 255.0F);
                    int i2 = (int)(afloat[1] * 255.0F);
                    int j2 = (int)(afloat[2] * 255.0F);
                    i += Math.max(l1, Math.max(i2, j2));
                    aint[0] += l1;
                    aint[1] += i2;
                    aint[2] += j2;
                    ++j;
                }
            }
        }

        if (itemstack.isEmpty())
        { return ItemStack.EMPTY; }
        else
        {
            int i1 = aint[0] / j;
            int j1 = aint[1] / j;
            int k1 = aint[2] / j;
            float f3 = (float)i / (float)j;
            float f4 = (float)Math.max(i1, Math.max(j1, k1));
            i1 = (int)((float)i1 * f3 / f4);
            j1 = (int)((float)j1 * f3 / f4);
            k1 = (int)((float)k1 * f3 / f4);
            int k2 = (i1 << 8) + j1;
            k2 = (k2 << 8) + k1;
            ((ItemVeilingOutfit)itemstack.getItem()).setColor(itemstack, k2);
            return itemstack;
        }
    }

    public boolean canFit(int width, int height) { return width * height >= 2; }

    public ItemStack getRecipeOutput() { return ItemStack.EMPTY; }
}