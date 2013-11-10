package ganymedes01.ganyssurface.inventory.slots;

import ganymedes01.ganyssurface.core.utils.ItemStackMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class GhostSlot extends Slot {

	private final int slotIndex;

	public GhostSlot(IInventory inventory, int slot, int posX, int posY) {
		super(inventory, slot, posX, posY);
		slotIndex = slot;
	}

	public void interact(ItemStack stack, boolean rightClick) {
		ItemStack ghostStack = inventory.getStackInSlot(slotIndex);
		if (stack == null)
			inventory.setInventorySlotContents(slotIndex, null);
		else if (ItemStackMap.areItemsEqual(ghostStack, stack)) {
			if (rightClick) {
				ghostStack.stackSize -= stack.stackSize;
				if (ghostStack.stackSize <= 0)
					inventory.setInventorySlotContents(slotIndex, null);
			} else {
				ghostStack.stackSize += stack.stackSize;
				if (ghostStack.stackSize > ghostStack.getMaxStackSize())
					ghostStack.stackSize = ghostStack.getMaxStackSize();
			}
		} else if (rightClick) {
			ItemStack copy = stack.copy();
			copy.stackSize = 1;
			inventory.setInventorySlotContents(slotIndex, copy);
		} else
			inventory.setInventorySlotContents(slotIndex, stack);
		onSlotChanged();
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}
}