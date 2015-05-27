package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.BetterSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityBlockDetector;
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

public class ContainerBlockDetector extends GanysContainer {

	public ContainerBlockDetector(InventoryPlayer inventory, TileEntityBlockDetector tile) {
		super(tile);
		addSlotToContainer(new BetterSlot(tile, 0, 80, 42));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotItemStack = slot.getStack();
			stack = slotItemStack.copy();

			if (slotIndex < 1) {
				if (!mergeItemStack(slotItemStack, 1, inventorySlots.size(), true))
					return null;
			} else if (((Slot) inventorySlots.get(0)).getStack() == null) {
				((Slot) inventorySlots.get(0)).putStack(slotItemStack.splitStack(1));
				if (slotItemStack.stackSize <= 0)
					slot.putStack(null);
				return null;
			} else
				return null;

			if (slotItemStack.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
		}

		return stack;
	}
}