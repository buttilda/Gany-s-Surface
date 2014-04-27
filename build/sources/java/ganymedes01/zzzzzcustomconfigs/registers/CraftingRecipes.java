package ganymedes01.zzzzzcustomconfigs.registers;

import ganymedes01.zzzzzcustomconfigs.lib.Logs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingRecipes {

	public static void registerShapedRecipeFromLine(Logger logger, String line) {
		String[] data = line.split(",");
		if (data.length < 6) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ARGS_NUMBER + line, "shaped recipe"));
			return;
		}

		String outputID;
		Integer outputSize;
		Integer outputMeta;

		String topRow;
		String middleRow;
		String bottomRow;

		HashMap<Character, ItemStack> map;
		try {
			outputID = data[0].trim();
			outputSize = Integer.parseInt(data[1].trim());
			outputMeta = Integer.parseInt(data[2].trim());

			topRow = data[3].startsWith(" ") ? data[3].substring(1) : data[3];
			middleRow = data[4].startsWith(" ") ? data[4].substring(1) : data[4];
			bottomRow = data[5].startsWith(" ") ? data[5].substring(1) : data[5];

			map = new HashMap<Character, ItemStack>();
			int remaining = data.length - 6;
			int index = 1;
			for (int i = 0; i < remaining / 3; i++) {
				map.put(data[5 + index].trim().charAt(0), new ItemStack((Item) Item.itemRegistry.getObject(data[6 + index].trim()), 1, Integer.parseInt(data[7 + index].trim())));
				index += 3;
			}

		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, String.format(Logs.FAILED_TO_CAST + line, "shaped recipe"));
			return;
		}

		Item output = (Item) Item.itemRegistry.getObject(outputID);
		if (output == null) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ID + line, "shaped recipe"));
			return;
		}

		ArrayList<Object> list = new ArrayList<Object>();
		if (!topRow.isEmpty())
			list.add(topRow);
		if (!middleRow.isEmpty())
			list.add(middleRow);
		if (!bottomRow.isEmpty())
			list.add(bottomRow);

		for (Entry<Character, ItemStack> entry : map.entrySet()) {
			list.add(entry.getKey());
			list.add(entry.getValue());
		}
		GameRegistry.addRecipe(new ItemStack(output, outputSize, outputMeta), list.toArray(new Object[0]));
		logger.log(Level.INFO, "\tRegistered shaped recipe for " + output.getUnlocalizedName(new ItemStack(output, 1, outputMeta)));
	}

	public static void registerShapelessRecipeFromLine(Logger logger, String line) {
		String[] data = line.split(",");
		if (data.length < 5) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ARGS_NUMBER + line, "shapeless recipe"));
			return;
		}

		String outputID;
		Integer outputSize;
		Integer outputMeta;

		ArrayList<Object> inputs;
		try {
			outputID = data[0].trim();
			outputSize = Integer.parseInt(data[1].trim());
			outputMeta = Integer.parseInt(data[2].trim());

			inputs = new ArrayList<Object>();
			int remaining = data.length - 3;
			int index = 1;
			for (int i = 0; i < remaining / 2; i++) {
				inputs.add(new ItemStack((Item) Item.itemRegistry.getObject(data[2 + index].trim()), 1, Integer.parseInt(data[3 + index].trim())));
				index += 2;
			}

		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, String.format(Logs.FAILED_TO_CAST + line, "shapeless recipe"));
			return;
		}

		Item output = (Item) Item.itemRegistry.getObject(outputID);
		if (output == null) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ID + line, "shapeless recipe"));
			return;
		}

		System.out.println(inputs);
		GameRegistry.addShapelessRecipe(new ItemStack(output, outputSize, outputMeta), inputs.toArray(new Object[0]));
		logger.log(Level.INFO, "\tRegistered shapeless recipe for " + output.getUnlocalizedName(new ItemStack(output, 1, outputMeta)));
	}
}