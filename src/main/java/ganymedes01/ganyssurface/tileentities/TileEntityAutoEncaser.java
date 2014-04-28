package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.ModItems;
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
		} else if (Utils.areStacksTheSame(encased, inventory[9], false) && inventory[9].stackSize < 64) {
			inventory[9].stackSize++;
			added = true;
		}
		if (added)
			for (int i = 0; i < 9; i++) {
				inventory[i].stackSize--;
				if (inventory[i].stackSize <= 0)
					inventory[i] = null;
			}
	}

	private ItemStack getEncasedItem() {
		ItemStack storageCase = new ItemStack(ModItems.storageCase);
		storageCase.setTagCompound(new NBTTagCompound());

		NBTTagCompound stackData = new NBTTagCompound();
		ItemStack sCopy = inventory[0];
		sCopy.stackSize = 1;
		sCopy.writeToNBT(stackData);
		storageCase.getTagCompound().setTag("stack", stackData);
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
				if (!Utils.areStacksTheSame(s, stack, false))
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