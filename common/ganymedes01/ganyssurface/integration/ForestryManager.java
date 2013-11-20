package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ForestryManager {

	public static void init() {
		System.out.println("FORESTRY INIT");
		FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", "farmWheat@" + ModItems.camelliaSeeds.itemID + ".0." + ModBlocks.camelliaCrop.blockID + ".7");

		GameRegistry.addRecipe(getItem("fertilizerCompound", 24), "xxx", "xyx", "xxx", 'x', new ItemStack(ModItems.rot, 1, 1), 'y', new ItemStack(Item.dyePowder, 1, 4));
	}

	private static ItemStack getItem(String name, int size) {
		try {
			Class<?> items = Class.forName("forestry.core.config.ForestryItem");

			Object ret = items.getField(name).get(null);

			if (ret instanceof Item) {
				ItemStack newStack = new ItemStack((Item) ret, size);
				newStack.stackSize = size;
				return newStack;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}