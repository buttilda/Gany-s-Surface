package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class StorageCaseRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting craft, World world) {
		ItemStack storageCase = null;
		for (int i = 0; i < craft.getSizeInventory(); i++) {
			ItemStack stack = craft.getStackInSlot(i);
			if (stack != null && stack.getItem() == ModItems.storageCase)
				if (storageCase == null)
					storageCase = stack;
				else
					return false;
		}
		return storageCase != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting craft) {
		ItemStack storageCase = null;
		for (int i = 0; i < craft.getSizeInventory(); i++) {
			ItemStack stack = craft.getStackInSlot(i);
			if (stack != null && stack.getItem() == ModItems.storageCase)
				if (storageCase == null)
					storageCase = stack;
				else
					return null;
		}
		if (!storageCase.hasTagCompound())
			return null;
		ItemStack stack = ItemStack.loadItemStackFromNBT(storageCase.getTagCompound().getCompoundTag("stack"));
		if (stack != null) {
			stack.stackSize = 9;
			return stack;
		}
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}
}