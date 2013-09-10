package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketTileWithIMultipleDisplayItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWorkTable extends TileEntity implements ISidedInventory {

	private ItemStack[] craftMatrix = new ItemStack[9];
	public InventoryCrafting invtCraftMatrix;

	@Override
	public Packet getDescriptionPacket() {
		int[] itemID, meta, stackSize;
		itemID = new int[craftMatrix.length];
		meta = new int[craftMatrix.length];
		stackSize = new int[craftMatrix.length];

		for (int i = 0; i < craftMatrix.length; i++)
			if (craftMatrix[i] != null) {
				itemID[i] = craftMatrix[i].itemID;
				meta[i] = craftMatrix[i].getItemDamage();
				stackSize[i] = craftMatrix[i].stackSize;
			}

		return PacketTypeHandler.populatePacket(new PacketTileWithIMultipleDisplayItems(xCoord, yCoord, zCoord, itemID, meta, stackSize, craftMatrix.length));
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < craftMatrix.length; ++i)
			if (craftMatrix[i] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				craftMatrix[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		data.setTag("Items", tagList);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		NBTTagList tagList = data.getTagList("Items");
		craftMatrix = new ItemStack[9];
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < craftMatrix.length)
				craftMatrix[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
		}
	}

	@Override
	public int getSizeInventory() {
		return craftMatrix.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return craftMatrix[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (craftMatrix[slot] != null) {
			ItemStack itemstack;

			if (craftMatrix[slot].stackSize <= size) {
				itemstack = craftMatrix[slot];
				craftMatrix[slot] = null;
				invtCraftMatrix.setInventorySlotContents(slot, null);
				return itemstack;
			} else {
				itemstack = craftMatrix[slot].splitStack(size);

				if (craftMatrix[slot].stackSize == 0) {
					craftMatrix[slot] = null;
					invtCraftMatrix.setInventorySlotContents(slot, null);
				}

				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (craftMatrix[slot] != null) {
			ItemStack itemstack = craftMatrix[slot];
			craftMatrix[slot] = null;
			invtCraftMatrix.setInventorySlotContents(slot, null);
			return itemstack;
		} else
			return null;
	}

	public void addToCraftMatrix(int slot, ItemStack stack) {
		craftMatrix[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		craftMatrix[slot] = stack;
		invtCraftMatrix.setInventorySlotContents(slot, stack);

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
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
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}
}
