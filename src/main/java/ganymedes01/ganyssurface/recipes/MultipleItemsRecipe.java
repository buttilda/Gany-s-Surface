package ganymedes01.ganyssurface.recipes;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class MultipleItemsRecipe extends ShapedRecipes {

	public final ItemStack[][] recipeItems;

	public MultipleItemsRecipe(int width, int height, ItemStack[][] ingredients, ItemStack result) {
		super(width, height, getSimpleArray(ingredients), result);
		recipeItems = ingredients;
	}

	private static ItemStack[] getSimpleArray(ItemStack[][] ingredients) {
		ItemStack[] list = new ItemStack[ingredients.length];

		for (int i = 0; i < ingredients.length; i++)
			list[i] = ingredients[i][0];

		return list;
	}

	// Stolen from vanilla and adapted.
	public static MultipleItemsRecipe createNewRecipe(ItemStack result, Object... recipe) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;

		if (recipe[i] instanceof String[]) {
			String[] astring = (String[]) recipe[i++];

			for (int l = 0; l < astring.length; l++) {
				String s1 = astring[l];
				k++;
				j = s1.length();
				s = s + s1;
			}
		} else
			while (recipe[i] instanceof String) {
				String s2 = (String) recipe[i++];
				k++;
				j = s2.length();
				s = s + s2;
			}

		HashMap hashmap;

		for (hashmap = new HashMap(); i < recipe.length; i += 2) {
			Character character = (Character) recipe[i];
			ItemStack itemstack1 = null;

			if (recipe[i + 1] instanceof Item)
				itemstack1 = new ItemStack((Item) recipe[i + 1]);
			else if (recipe[i + 1] instanceof Block)
				itemstack1 = new ItemStack((Block) recipe[i + 1], 1, OreDictionary.WILDCARD_VALUE);
			else if (recipe[i + 1] instanceof ItemStack)
				itemstack1 = (ItemStack) recipe[i + 1];

			if (itemstack1 != null)
				hashmap.put(character, new ItemStack[] { itemstack1 });

			if (recipe[i + 1] instanceof ItemStack[])
				hashmap.put(character, recipe[i + 1]);
		}

		ItemStack[][] aitemstack = new ItemStack[j * k][];

		for (int i1 = 0; i1 < j * k; i1++) {
			char c0 = s.charAt(i1);

			if (hashmap.containsKey(Character.valueOf(c0)))
				aitemstack[i1] = (ItemStack[]) hashmap.get(Character.valueOf(c0));
			else
				aitemstack[i1] = null;
		}

		return new MultipleItemsRecipe(j, k, aitemstack, result);
	}

	@Override
	public boolean matches(InventoryCrafting craft, World world) {
		for (int i = 0; i <= 3 - recipeWidth; i++)
			for (int j = 0; j <= 3 - recipeHeight; j++) {
				if (checkMatch(craft, i, j, true))
					return true;

				if (checkMatch(craft, i, j, false))
					return true;
			}

		return false;
	}

	private boolean checkMatch(InventoryCrafting craft, int x, int y, boolean par4) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				int i1 = i - x;
				int j1 = j - y;
				ItemStack[] ingredient = null;

				if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight)
					if (par4)
						ingredient = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
					else
						ingredient = recipeItems[i1 + j1 * recipeWidth];

				ItemStack itemstack1 = craft.getStackInRowAndColumn(i, j);

				if (!matchesAtLeastOne(ingredient, itemstack1))
					return false;
			}

		return true;
	}

	private boolean matchesAtLeastOne(ItemStack[] stackArray, ItemStack stack2) {
		if (stackArray == null && stack2 == null)
			return true;
		else if (stackArray == null && stack2 != null || stackArray != null && stack2 == null)
			return false;
		else if (stackArray != null)
			for (ItemStack stack1 : stackArray)
				if (stack1.itemID == stack2.itemID)
					if (stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack1.getItemDamage() == stack2.getItemDamage())
						return true;

		return false;
	}
}