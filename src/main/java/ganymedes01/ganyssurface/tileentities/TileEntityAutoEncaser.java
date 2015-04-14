package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.ArrayList;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityAutoEncaser extends GanysInventory implements ISidedInventory {

	private boolean bool;

	public TileEntityAutoEncaser() {
		super(10, Strings.AUTO_ENCASER_NAME);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot == 9) {
			inventory[slot] = stack;
			markDirty();
		} else
			super.setInventorySlotContents(slot, stack);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (bool) {
			bool = false;
			return;
		}
		bool = true;

		for (int i = 0; i < 9; i++)
			if (inventory[i] == null)
				return;

		ItemStack encased = getEncasedItem();

		boolean added = false;
		if (inventory[9] == null) {
			inventory[9] = encased;
			added = true;
		} else if (InventoryUtils.areStacksTheSame(encased, inventory[9], false) && inventory[9].stackSize < inventory[9].getMaxStackSize()) {
			inventory[9].stackSize++;
			added = true;
		}
		if (added)
			for (int i = 0; i < 9; i++)
				if (--inventory[i].stackSize <= 0)
					inventory[i] = null;
	}

	private ItemStack getEncasedItem() {
		ItemStack storageCase = new ItemStack(ModItems.storageCase);
		storageCase.setTagCompound(new NBTTagCompound());

		NBTTagCompound nbt = new NBTTagCompound();
		ItemStack sCopy = inventory[0].copy();
		sCopy.stackSize = 1;
		sCopy.writeToNBT(nbt);
		storageCase.getTagCompound().setTag("stack", nbt);
		return storageCase;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if (slot != 9) {
			ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
			for (int i = 0; i < 9; i++) {
				if (inventory[i] == null)
					continue;
				stacks.add(inventory[i]);
			}
			if (stacks.isEmpty())
				return true;
			for (ItemStack s : stacks)
				if (!InventoryUtils.areStacksTheSame(s, stack, false))
					return false;
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 9;
	}
}