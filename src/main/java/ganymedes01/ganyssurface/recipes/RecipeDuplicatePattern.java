package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.tileentities.TileEntityBanner;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class RecipeDuplicatePattern implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting grid, World world) {
		ItemStack itemstack = null;
		ItemStack itemstack1 = null;

		for (int i = 0; i < grid.getSizeInventory(); i++) {
			ItemStack slot = grid.getStackInSlot(i);

			if (slot != null) {
				if (slot.getItem() != Item.getItemFromBlock(ModBlocks.banner))
					return false;

				if (itemstack != null && itemstack1 != null)
					return false;

				int colour = TileEntityBanner.getBaseColor(slot);
				boolean flag = TileEntityBanner.getPatterns(slot) > 0;

				if (itemstack != null) {
					if (flag)
						return false;

					if (colour != TileEntityBanner.getBaseColor(itemstack))
						return false;

					itemstack1 = slot;
				} else if (itemstack1 != null) {
					if (!flag)
						return false;

					if (colour != TileEntityBanner.getBaseColor(itemstack1))
						return false;

					itemstack = slot;
				} else if (flag)
					itemstack = slot;
				else
					itemstack1 = slot;
			}
		}

		return itemstack != null && itemstack1 != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting grid) {
		for (int i = 0; i < grid.getSizeInventory(); i++) {
			ItemStack slot = grid.getStackInSlot(i);

			if (slot != null && TileEntityBanner.getPatterns(slot) > 0) {
				ItemStack copy = slot.copy();
				copy.stackSize = 1;
				return copy;
			}
		}

		return null;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}
}