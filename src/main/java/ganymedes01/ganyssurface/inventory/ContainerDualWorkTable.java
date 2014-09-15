package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.WorkTableResultSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
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

	protected IInventory result = new InventoryCraftResult();
	protected IInventory resultRight = new InventoryCraftResult();
	protected TileEntityDualWorkTable tile;

	public ContainerDualWorkTable(InventoryPlayer inventory, TileEntityDualWorkTable tile) {
		this(inventory, tile, true);
	}

	public ContainerDualWorkTable(InventoryPlayer inventory, TileEntityDualWorkTable tile, boolean addPlayerInventory) {
		this.tile = tile;
		tile.craftMatrix.setContainer(this);
		tile.craftMatrixRight.setContainer(this);
		tile.craftMatrix.lock();
		tile.craftMatrixRight.lock();

		addSlotToContainer(new WorkTableResultSlot(tile, inventory.player, tile.craftMatrix, result, 0, 75, 35, 0));
		addSlotToContainer(new WorkTableResultSlot(tile, inventory.player, tile.craftMatrixRight, resultRight, 1, 168, 35, 9));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(tile.craftMatrix, j + i * 3, 12 + j * 18, 17 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(tile.craftMatrixRight, j + i * 3, 105 + j * 18, 17 + i * 18));

		if (addPlayerInventory) {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 9; j++)
					addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 19 + j * 18, 84 + i * 18));
			for (int i = 0; i < 9; i++)
				addSlotToContainer(new Slot(inventory, i, 19 + i * 18, 142));
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if (inventory == tile.craftMatrix)
			result.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(tile.craftMatrix, tile.getWorldObj()));
		else if (inventory == tile.craftMatrixRight)
			resultRight.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(tile.craftMatrixRight, tile.getWorldObj()));
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

	@Override
	public void onContainerClosed(EntityPlayer player) {
		tile.craftMatrix.setContainer(null);
		tile.craftMatrixRight.setContainer(null);
	}
}