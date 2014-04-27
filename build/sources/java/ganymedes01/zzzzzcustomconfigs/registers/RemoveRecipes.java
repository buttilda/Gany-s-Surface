package ganymedes01.zzzzzcustomconfigs.registers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RemoveRecipes {

	@SuppressWarnings("unchecked")
	public static void removeRecipeFromLine(Logger logger, String line) {
		String itemID = line.split(",")[0].trim();
		int meta = Integer.parseInt(line.split(",")[1]);
		int recipeCount = 0;

		ArrayList<IRecipe> list = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < list.size(); i++) {
			ItemStack output = list.get(i).getRecipeOutput();
			if (output != null)
				if (Item.itemRegistry.getNameForObject(output.getItem()).matches(ensureNamespaced(itemID)) && output.getItemDamage() == meta) {
					CraftingManager.getInstance().getRecipeList().remove(i);
					recipeCount++;
				}
		}
		logger.log(Level.INFO, "\tRemoved " + recipeCount + " recipe" + (recipeCount > 1 ? "s" : "") + " for " + ((Item) Item.itemRegistry.getObject(itemID)).getUnlocalizedName(new ItemStack((Item) Item.itemRegistry.getObject(itemID), 1, meta)));
	}

	protected static String ensureNamespaced(String paramString) {
		return paramString.indexOf(':') == -1 ? "minecraft:" + paramString : paramString;
	}
}