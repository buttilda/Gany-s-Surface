package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.WorkTableResultSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
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

public class ContainerDualWorkTable extends Container {

	public IInventory craftResult = new InventoryCraftResult();
	public IInventory craftResultRight = new InventoryCraftResult();
	public TileEntityDualWorkTable dualWorkTable;

	public ContainerDualWorkTable(InventoryPlayer inventory, TileEntityDualWorkTable tile) {
		dualWorkTable = tile;
		dualWorkTable.invtCraftMatrix = new InventoryCrafting(this, 3, 3);
		dualWorkTable.invtCraftMatrixRight = new InventoryCrafting(this, 3, 3);

		for (int i = 0; i < dualWorkTable.invtCraftMatrix.getSizeInventory(); i++)
			dualWorkTable.invtCraftMatrix.setInventorySlotContents(i, dualWorkTable.getStackInSlot(i));
		for (int i = 0; i < dualWorkTable.invtCraftMatrixRight.getSizeInventory(); i++)
			dualWorkTable.invtCraftMatrixRight.setInventorySlotContents(i, dualWorkTable.getStackInSlot(i + dualWorkTable.invtCraftMatrix.getSizeInventory()));

		addSlotToContainer(new WorkTableResultSlot(tile, inventory.player, dualWorkTable.invtCraftMatrix, craftResult, 0, 75, 35, 0));
		addSlotToContainer(new WorkTableResultSlot(tile, inventory.player, dualWorkTable.invtCraftMatrixRight, craftResultRight, 1, 168, 35, 9));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(dualWorkTable, j + i * 3, 12 + j * 18, 17 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(dualWorkTable, dualWorkTable.invtCraftMatrix.getSizeInventory() + j + i * 3, 105 + j * 18, 17 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 19 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 19 + i * 18, 142));

		onCraftMatrixChanged(dualWorkTable.invtCraftMatrix);
		onCraftMatrixChanged(dualWorkTable.invtCraftMatrixRight);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if (inventory == dualWorkTable.invtCraftMatrix)
			craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(dualWorkTable.invtCraftMatrix, dualWorkTable.worldObj));
		else if (inventory == dualWorkTable.invtCraftMatrixRight)
			craftResultRight.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(dualWorkTable.invtCraftMatrixRight, dualWorkTable.worldObj));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex <= 20) {
				if (!mergeItemStack(itemstack1, 20, inventorySlots.size(), true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}

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
		return !(slot instanceof WorkTableResultSlot);
	}
}