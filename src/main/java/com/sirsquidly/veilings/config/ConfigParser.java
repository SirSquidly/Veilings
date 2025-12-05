package com.sirsquidly.veilings.config;

import com.google.common.collect.Lists;
import com.sirsquidly.veilings.veilings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

/**
 * 	This is to break part arrays in the config for use in other areas of the code.
 *
 *  I break it up in this class so that I don't have to break the config arrays every time I want to use them.
 */
public class ConfigParser
{
	/** Nightmare spawn biomes list. */
	public static List<ItemStack> veilingTreatItems = Lists.newArrayList();
	public static List<Integer> veilingTreatHappiness = Lists.newArrayList();

	/** Goes through the many Arrays in the config, to translate them into lists to be used elsewhere. */
	public static void breakupConfigArrays()
	{
		for(String S : Config.entity.veilingTreatList)
		{
			String[] split = S.split("=");

			if (split.length != 2) continue;

			ItemStack stack = getItemStackFromString(split[0]);
			int happiness = Integer.parseInt(split[1]);

			if (stack.isEmpty())
			{ veilings.LOGGER.error((split[0]) + " is not a proper itemStack!"); }

			else if (veilingTreatItems.contains(new ResourceLocation(split[0])))
			{ veilings.LOGGER.error(split[0] + " is already a defined Veiling treat! Only the first listed will be used!"); }

			else
			{
				veilingTreatItems.add(stack);
				veilingTreatHappiness.add(happiness);
			}
		}
	}

	public static ItemStack getItemStackFromString(String string)
	{
		String[] ripString = string.split(":");

		if (ripString.length < 2) return ItemStack.EMPTY;

		Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(ripString[0], ripString[1]));
		if(item == null) return ItemStack.EMPTY;

		int meta = 0;
		if (ripString.length > 2) meta = Integer.parseInt(ripString[2]);

		return new ItemStack(item, 1, meta);
	}
}