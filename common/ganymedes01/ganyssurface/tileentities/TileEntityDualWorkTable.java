package ganymedes01.ganyssurface.tileentities;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityDualWorkTable extends TileEntityWorkTable {

	public InventoryCrafting invtCraftMatrixRight;

	public TileEntityDualWorkTable() {
		super(18);
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (slot < invtCraftMatrix.getSizeInventory())
			return super.decrStackSize(slot, size);

		if (inventory[slot] != null) {
			ItemStack itemstack;

			if (inventory[slot].stackSize <= size) {
				itemstack = inventory[slot];
				inventory[slot] = null;
				if (invtCraftMatrixRight != null)
					invtCraftMatrixRight.setInventorySlotContents(slot - invtCraftMatrix.getSizeInventory(), null);
				return itemstack;
			} else {
				itemstack = inventory[slot].splitStack(size);

				if (inventory[slot].stackSize == 0) {
					inventory[slot] = null;
					if (invtCraftMatrixRight != null)
						invtCraftMatrixRight.setInventorySlotContents(slot - invtCraftMatrix.getSizeInventory(), null);
				}

				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot < invtCraftMatrix.getSizeInventory())
			return super.getStackInSlotOnClosing(slot);

		if (inventory[slot] != null) {
			ItemStack itemstack = inventory[slot];
			inventory[slot] = null;
			if (invtCraftMatrixRight != null)
				invtCraftMatrixRight.setInventorySlotContents(slot - invtCraftMatrix.getSizeInventory(), null);
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot < invtCraftMatrix.getSizeInventory()) {
			super.setInventorySlotContents(slot, stack);
			return;
		}

		inventory[slot] = stack;
		if (invtCraftMatrixRight != null)
			invtCraftMatrixRight.setInventorySlotContents(slot - invtCraftMatrix.getSizeInventory(), stack);

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}
}