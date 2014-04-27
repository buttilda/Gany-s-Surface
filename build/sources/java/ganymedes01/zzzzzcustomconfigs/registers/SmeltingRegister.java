package ganymedes01.zzzzzcustomconfigs.registers;

import ganymedes01.zzzzzcustomconfigs.lib.Logs;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class SmeltingRegister {

	public static void registerSmeltingFromString(Logger logger, String line) {
		String[] data = line.split(",");
		if (data.length != 6) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ARGS_NUMBER + line, "smelting"));
			return;
		}

		String inputID;
		Integer inputMeta;

		String outputID;
		Integer outputSize;
		Integer outputMeta;

		Float xp;

		try {
			inputID = data[0].trim();
			inputMeta = Integer.parseInt(data[1].trim());

			outputID = data[2].trim();
			outputSize = Integer.parseInt(data[3].trim());
			outputMeta = Integer.parseInt(data[4].trim());

			xp = Float.parseFloat(data[5].trim());
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, String.format(Logs.FAILED_TO_CAST + line, "smelting"));
			return;
		}

		Item input = (Item) Item.itemRegistry.getObject(inputID);
		Item output = (Item) Item.itemRegistry.getObject(outputID);
		if (input == null || output == null) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ID + line, "smelting"));
			return;
		}

		FurnaceRecipes.smelting().func_151394_a(new ItemStack(input, 1, inputMeta), new ItemStack(output, outputSize, outputMeta), xp);
		logger.log(Level.INFO, "\tRegistered new Smelting: " + input.getUnlocalizedName(new ItemStack(input, 1, inputMeta)) + " to " + output.getUnlocalizedName(new ItemStack(output, outputSize, outputMeta)));
	}

	public static void registerOreDictSmeltingFromString(Logger logger, String line) {
		String[] data = line.split(",");
		if (data.length != 5) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ARGS_NUMBER + line, "ore dict smelting"));
			return;
		}

		String input;

		String outputID;
		Integer outputSize;
		Integer outputMeta;

		Float xp;

		try {
			input = data[0].trim();

			outputID = data[1].trim();
			outputSize = Integer.parseInt(data[2].trim());
			outputMeta = Integer.parseInt(data[3].trim());

			xp = Float.parseFloat(data[4].trim());
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, String.format(Logs.FAILED_TO_CAST + line, "ore dict smelting"));
			return;
		}

		Item output = (Item) Item.itemRegistry.getObject(outputID);
		if (output == null) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ID + line, "ore dict smelting"));
			return;
		}

		for (ItemStack ore : OreDictionary.getOres(input))
			if (ore != null) {
				FurnaceRecipes.smelting().func_151394_a(ore, new ItemStack(output, outputSize, outputMeta), xp);
				logger.log(Level.INFO, "\tRegistered new ore dictionary Smelting: " + ore.getItem().getUnlocalizedName(ore) + " to " + output.getUnlocalizedName(new ItemStack(output, outputSize, outputMeta)));
			}
	}
}