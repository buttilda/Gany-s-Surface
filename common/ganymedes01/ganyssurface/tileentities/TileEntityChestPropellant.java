package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.ChestPropellant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityChestPropellant extends TileEntity implements ISidedInventory {

	@Override
	public int getSizeInventory() {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				return ((IInventory) tile).getSizeInventory();
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				return ((IInventory) tile).getStackInSlot(slot);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				return ((IInventory) tile).decrStackSize(slot, size);
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				return ((IInventory) tile).getStackInSlotOnClosing(slot);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				((IInventory) tile).setInventorySlotContents(slot, stack);
		}
	}

	@Override
	public String getInvName() {
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				return ((IInventory) tile).getInventoryStackLimit();
		}
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				return ((IInventory) tile).isUseableByPlayer(player);
		}
		return false;
	}

	@Override
	public void openChest() {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				((IInventory) tile).openChest();
		}
	}

	@Override
	public void closeChest() {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				((IInventory) tile).closeChest();
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				return ((IInventory) tile).isItemValidForSlot(slot, stack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		for (int i = 0; i < ChestPropellant.MAX_PILE_SIZE; i++) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				if (tile instanceof ISidedInventory)
					return ((ISidedInventory) tile).getAccessibleSlotsFromSide(side);
				else {
					int size = ((IInventory) tile).getSizeInventory();
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
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
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
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
			if (tile instanceof IInventory && !(tile instanceof TileEntityChestPropellant))
				if (tile instanceof ISidedInventory)
					return ((ISidedInventory) tile).canExtractItem(slot, stack, side);
				else
					return true;
		}
		return false;
	}
}
