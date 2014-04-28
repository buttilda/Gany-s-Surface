package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.ChestPropellant;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityChestPropellant extends TileEntity implements ISidedInventory {

	public ItemStack getInventoryToRender() {
		if (Utils.getTileEntity(worldObj, xCoord, yCoord - (ChestPropellant.MAX_PILE_SIZE - 1), zCoord, TileEntityChestPropellant.class) != null)
			return null;
		IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - 1, zCoord, IInventory.class);
		if (tile != null)
			if (!(tile instanceof TileEntityChestPropellant))
				return new ItemStack(worldObj.getBlock(xCoord, yCoord - 1, zCoord), 1, worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord));
			else
				return tile == null ? null : ((TileEntityChestPropellant) tile).getInventoryToRender();
		else
			return null;
	}

	@Override
	public int getSizeInventory() {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				return tile.getSizeInventory();
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				return tile.getStackInSlot(slot);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				return tile.decrStackSize(slot, size);
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				return tile.getStackInSlotOnClosing(slot);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				tile.setInventorySlotContents(slot, stack);
		}
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				return tile.getInventoryStackLimit();
		}
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				return tile.isUseableByPlayer(player);
		}
		return false;
	}

	@Override
	public void openInventory() {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				tile.openInventory();
		}
	}

	@Override
	public void closeInventory() {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				tile.closeInventory();
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				return tile.isItemValidForSlot(slot, stack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				if (tile instanceof ISidedInventory)
					return ((ISidedInventory) tile).getAccessibleSlotsFromSide(side);
				else {
					int size = tile.getSizeInventory();
					int[] array = new int[size];
					for (int j = 0; j < size; j++)
						array[j] = j;
					return array;
				}
		}
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				if (tile instanceof ISidedInventory)
					return ((ISidedInventory) tile).canInsertItem(slot, stack, side);
				else
					return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			IInventory tile = Utils.getTileEntity(worldObj, xCoord, yCoord - i, zCoord, IInventory.class);
			if (tile != null && !(tile instanceof TileEntityChestPropellant))
				if (tile instanceof ISidedInventory)
					return ((ISidedInventory) tile).canExtractItem(slot, stack, side);
				else
					return true;
		}
		return false;
	}

	@Override
	public boolean canUpdate() {
		return false;
	}
}