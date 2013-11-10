package ganymedes01.ganyssurface.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BlockDetectorSlot extends Slot {

	public BlockDetectorSlot(IInventory inventory, int slot, int posX, int posY) {
		super(inventory, slot, posX, posY);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
