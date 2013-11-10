package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.FixSlot;
import ganymedes01.ganyssurface.inventory.slots.GhostSlot;
import ganymedes01.ganyssurface.inventory.slots.PaymentSlot;
import ganymedes01.ganyssurface.inventory.slots.RetrievalSlot;
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

	private final TileEntityMarket market;

	public ContainerMarketPublic(InventoryPlayer inventory, TileEntityMarket tile, String username) {
		market = tile;

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

		addSlotToContainer(new PaymentSlot(tile, TileEntityMarket.PAYMENT_ONE, 62, 18, getSlot(0), this));
		addSlotToContainer(new RetrievalSlot(tile, TileEntityMarket.RETRIEVE_ONE, 62, 47, getSlot(0), getSlot(1), getSlot(4), 1));

		addSlotToContainer(new PaymentSlot(tile, TileEntityMarket.PAYMENT_TWO, 149, 18, getSlot(2), this));
		addSlotToContainer(new RetrievalSlot(tile, TileEntityMarket.RETRIEVE_TWO, 149, 47, getSlot(2), getSlot(3), getSlot(6), 2));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));

		// Hacky and lazy
		for (int i = 0; i < TileEntityMarket.PRICE_ONE; i++)
			addSlotToContainer(new Slot(tile, i, -300, -300));
	}

	public int getCount(int num) {
		return num == 1 ? market.getQuantityOfferONE() : market.getQuantityOfferTWO();
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
		} else if (slot instanceof PaymentSlot)
			slot.onSlotChanged();
		return super.slotClick(slotNum, mouseButton, modifier, player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();

			if (slotIndex > 7) {
				if (((Slot) inventorySlots.get(4)).isItemValid(itemstack)) {
					if (!mergeItemStack(stack, 4, 5, true))
						return null;
				} else if (((Slot) inventorySlots.get(6)).isItemValid(itemstack))
					if (!mergeItemStack(stack, 6, 7, true))
						return null;
			} else if (slotIndex >= 4 && slotIndex <= 7)
				if (!mergeItemStack(stack, 8, inventorySlots.size() - TileEntityMarket.PRICE_ONE, true))
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

	public void updateRetrievalSlots() {
		RetrievalSlot slotOne = (RetrievalSlot) getSlot(5);
		RetrievalSlot slotTwo = (RetrievalSlot) getSlot(7);

		slotOne.update();
		slotTwo.update();
	}
}