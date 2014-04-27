package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.FoodSlot;
import ganymedes01.ganyssurface.inventory.slots.InvalidSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityInkHarvester;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerInkHarvester extends Container {

	public ContainerInkHarvester(InventoryPlayer inventory, TileEntityInkHarvester tile) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new InvalidSlot(tile, j + i * 3, 45 + j * 18, 17 + i * 18));

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				addSlotToContainer(new FoodSlot(tile, 9 + j + i * 2, 116 + j * 18, 35 + i * 18));

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

			if (slotIndex < 13) {
				if (!mergeItemStack(stack, 13, 45, true))
					return null;
			} else if (stack != null && stack.getItem() instanceof ItemFood)
				if (!mergeItemStack(stack, 9, 13, false))
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