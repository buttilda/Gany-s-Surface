package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.InvalidSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerMarketPrivate extends Container {

	public ContainerMarketPrivate(InventoryPlayer inventory, TileEntityMarket tile) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 4; j++)
				addSlotToContainer(new Slot(tile, j + i * 4, 8 + j * 18, 16 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 4; j++)
				addSlotToContainer(new InvalidSlot(tile, 12 + j + i * 4, 98 + j * 18, 16 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();

			if (slotIndex < 24) {
				if (!mergeItemStack(stack, 24, inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(stack, 0, 12, false))
				return null;

			if (stack.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (stack.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(player, stack);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}