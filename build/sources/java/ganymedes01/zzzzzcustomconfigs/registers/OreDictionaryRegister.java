package ganymedes01.zzzzzcustomconfigs.registers;

import ganymedes01.zzzzzcustomconfigs.lib.Logs;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegister {

	public static void registerOreFromString(Logger logger, String line) {
		String[] data = line.split(",");
		if (data.length != 3) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ARGS_NUMBER + line, "ore dictionary item"));
			return;
		}

		String oreName = data[0].trim();
		String itemID;
		Integer itemMeta;
		try {
			itemID = data[1].trim();
			itemMeta = Integer.parseInt(data[2].trim());
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, String.format(Logs.FAILED_TO_CAST + line, "ore dictionary item"));
			return;
		}

		Item item = (Item) Item.itemRegistry.getObject(itemID);
		if (item == null) {
			logger.log(Level.SEVERE, String.format(Logs.INVALID_ID + line, "ore dictionary item"));
			return;
		}

		ItemStack stack = new ItemStack(item, 1, itemMeta);
		OreDictionary.registerOre(oreName, stack);

		logger.log(Level.INFO, "\tRegistered to Ore Dictionary: " + oreName + " - " + item.getUnlocalizedName(stack));
	}
}