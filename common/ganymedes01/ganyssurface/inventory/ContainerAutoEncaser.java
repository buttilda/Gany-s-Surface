package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.tileentities.TileEntityAutoEncaser;
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

public class ContainerAutoEncaser extends Container {

	public ContainerAutoEncaser(InventoryPlayer player, TileEntityAutoEncaser tile) {
		addSlotToContainer(new Slot(tile, 9, 124, 35));
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(tile, j + i * 3, 30 + j * 18, 17 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
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