package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.tileentities.TileEntityAutoEncaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerAutoEncaser extends GanysContainer {

	public ContainerAutoEncaser(InventoryPlayer player, TileEntityAutoEncaser tile) {
		super(tile);
		addSlotToContainer(new AutoEncaserSlot(tile, 9, 134, 48));
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new AutoEncaserSlot(tile, j + i * 3, 40 + j * 18, 30 + i * 18));
		addSlotToContainer(new AutoEncaserSlot(tile, 10, 100, 22));
		addSlotToContainer(new Slot(tile, 11, 16, 30));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 101 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 159));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return null;
	}

	private static class AutoEncaserSlot extends Slot {

		public AutoEncaserSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean canTakeStack(EntityPlayer player) {
			return false;
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return false;
		}
	}
}