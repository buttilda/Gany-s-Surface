package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.BetterSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityFarmManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerFarmManager extends GanysContainer {

	public ContainerFarmManager(InventoryPlayer inventory, TileEntityFarmManager tile) {
		super(tile);
		int index = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 6; j++) {
				addSlotToContainer(new BetterSlot(tile, index, 35 + j * 18, 17 + i * 18));
				index++;
			}

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

			if (slotIndex < 18) {
				if (!mergeItemStack(stack, 18, 45, true))
					return null;
			} else if (!mergeItemStack(stack, 0, 18, false))
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
}