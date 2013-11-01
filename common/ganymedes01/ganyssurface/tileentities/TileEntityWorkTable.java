package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketTileWithIMultipleDisplayItems;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityWorkTable extends GanysInventory implements ISidedInventory {

	public InventoryCrafting invtCraftMatrix;

	public TileEntityWorkTable() {
		super(9, null);
	}

	@Override
	public Packet getDescriptionPacket() {
		int[] itemID, meta, stackSize;
		itemID = new int[inventory.length];
		meta = new int[inventory.length];
		stackSize = new int[inventory.length];

		for (int i = 0; i < inventory.length; i++)
			if (inventory[i] != null) {
				itemID[i] = inventory[i].itemID;
				meta[i] = inventory[i].getItemDamage();
				stackSize[i] = inventory[i].stackSize;
			}

		return PacketTypeHandler.populatePacket(new PacketTileWithIMultipleDisplayItems(xCoord, yCoord, zCoord, itemID, meta, stackSize, inventory.length));
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (inventory[slot] != null) {
			ItemStack itemstack;

			if (inventory[slot].stackSize <= size) {
				itemstack = inventory[slot];
				inventory[slot] = null;
				invtCraftMatrix.setInventorySlotContents(slot, null);
				return itemstack;
			} else {
				itemstack = inventory[slot].splitStack(size);

				if (inventory[slot].stackSize == 0) {
					inventory[slot] = null;
					invtCraftMatrix.setInventorySlotContents(slot, null);
				}

				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (inventory[slot] != null) {
			ItemStack itemstack = inventory[slot];
			inventory[slot] = null;
			invtCraftMatrix.setInventorySlotContents(slot, null);
			return itemstack;
		} else
			return null;
	}

	public void addToCraftMatrix(int slot, ItemStack stack) {
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		invtCraftMatrix.setInventorySlotContents(slot, stack);

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}
}