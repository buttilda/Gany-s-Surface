package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.tileentities.TileEntityOrganicMatterCompressor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class OrganicMatterSlot extends Slot {

	public OrganicMatterSlot(IInventory inventory, int slot, int posX, int posY) {
		super(inventory, slot, posX, posY);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return TileEntityOrganicMatterCompressor.isOrganicMatter(stack);
	}
}