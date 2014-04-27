package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketWorkTable;
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
		this(9);
	}

	public TileEntityWorkTable(int size) {
		super(size, null);
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketTypeHandler.populatePacket(new PacketWorkTable(xCoord, yCoord, zCoord, inventory.clone()));
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

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if (invtCraftMatrix != null)
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

	@Override
	public boolean canUpdate() {
		return false;
	}
}