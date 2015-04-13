package ganymedes01.ganyssurface.inventory;

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

public class ContainerWoodSign extends Container {

	public ContainerWoodSign(InventoryPlayer player) {
		// Forge bugfix: Containers with no slots crash the game...
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(player, j + i * 9 + 9, -20, -20));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(player, i, -20, -20));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return null;
	}
}