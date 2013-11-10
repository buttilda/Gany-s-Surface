package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.FixSlot;
import ganymedes01.ganyssurface.inventory.slots.GhostSlot;
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

public class ContainerMarketPublic extends Container {

	public ContainerMarketPublic(InventoryPlayer inventory, TileEntityMarket tile, String username) {
		if (tile.isOwner(username)) {
			addSlotToContainer(new GhostSlot(tile, TileEntityMarket.PRICE_ONE, 39, 18));
			addSlotToContainer(new GhostSlot(tile, TileEntityMarket.OFFER_ONE, 39, 47));

			addSlotToContainer(new GhostSlot(tile, TileEntityMarket.PRICE_TWO, 126, 18));
			addSlotToContainer(new GhostSlot(tile, TileEntityMarket.OFFER_TWO, 126, 47));
		} else {
			addSlotToContainer(new FixSlot(tile, TileEntityMarket.PRICE_ONE, 39, 18));
			addSlotToContainer(new FixSlot(tile, TileEntityMarket.OFFER_ONE, 39, 47));

			addSlotToContainer(new FixSlot(tile, TileEntityMarket.PRICE_TWO, 126, 18));
			addSlotToContainer(new FixSlot(tile, TileEntityMarket.OFFER_TWO, 126, 47));
		}

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack slotClick(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
		Slot slot = slotNum < 0 ? null : (Slot) inventorySlots.get(slotNum);
		if (slot instanceof GhostSlot) {
			ItemStack heldStack = player.inventory.getItemStack();
			if (heldStack == null)
				slot.putStack(null);
			else
				((GhostSlot) slot).interact(heldStack.copy(), mouseButton != 0);

			return heldStack;
		}
		return super.slotClick(slotNum, mouseButton, modifier, player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}