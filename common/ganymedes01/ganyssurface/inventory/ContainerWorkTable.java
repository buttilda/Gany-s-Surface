package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.WorkTableResultSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerWorkTable extends Container {

	public IInventory craftResult = new InventoryCraftResult();
	public TileEntityWorkTable workTable;

	public ContainerWorkTable(InventoryPlayer inventory, TileEntityWorkTable tile) {
		workTable = tile;
		workTable.invtCraftMatrix = new InventoryCrafting(this, 3, 3);
		for (int i = 0; i < workTable.invtCraftMatrix.getSizeInventory(); i++)
			workTable.invtCraftMatrix.setInventorySlotContents(i, workTable.getStackInSlot(i));

		addSlotToContainer(new WorkTableResultSlot(tile, inventory.player, workTable.invtCraftMatrix, craftResult, 0, 124, 35));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(workTable, j + i * 3, 30 + j * 18, 17 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));

		onCraftMatrixChanged(workTable.invtCraftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(workTable.invtCraftMatrix, workTable.worldObj));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex == 0) {
				if (!mergeItemStack(itemstack1, 10, 46, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotIndex >= 10 && slotIndex < 37) {
				if (!mergeItemStack(itemstack1, 37, 46, false))
					return null;
			} else if (slotIndex >= 37 && slotIndex < 46) {
				if (!mergeItemStack(itemstack1, 10, 37, false))
					return null;
			} else if (!mergeItemStack(itemstack1, 10, 46, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != craftResult && super.func_94530_a(stack, slot);
	}
}
