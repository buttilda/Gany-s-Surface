package ganymedes01.ganyssurface.inventory.slots;

import ganymedes01.ganyssurface.core.utils.ItemStackMap;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class RetrievalSlot extends Slot {

	private final Slot price, offer, payment;
	private final TileEntityMarket market;
	private final int side;

	public RetrievalSlot(TileEntityMarket inventory, int slot, int posX, int posY, Slot price, Slot offer, Slot payment, int side) {
		super(inventory, slot, posX, posY);
		market = inventory;
		this.price = price;
		this.offer = offer;
		this.payment = payment;
		this.side = side;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return isOperational();
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		if (isOperational()) {
			int price = this.price.getStack().stackSize;

			market.makePayment(payment.getStack().splitStack(price));
			charge(offer.getStack().stackSize);
			if (payment.getStack() != null && payment.getStack().stackSize <= 0)
				payment.putStack(null);

		}
		update();
		super.onPickupFromSlot(player, stack);
	}

	private void charge(int price) {
		while (price > 0) {
			for (int i = 0; i < 12; i++) {
				ItemStack pay = market.getStackInSlot(i);
				if (pay == null)
					continue;
				else if (ItemStackMap.areItemsEqual(pay, offer.getStack())) {
					pay.stackSize--;
					if (pay.stackSize <= 0)
						market.setInventorySlotContents(i, null);
				}

			}
			price--;
		}
	}

	public void update() {
		if (isOperational()) {
			if (payment.getStack().stackSize >= price.getStack().stackSize)
				putStack(offer.getStack().copy());
		} else
			putStack(null);
	}

	private boolean isOperational() {
		boolean flag = false;
		if (side == 1)
			flag = market.getQuantityOfferONE() > 0;
		else if (side == 2)
			flag = market.getQuantityOfferTWO() > 0;

		return flag && price.getStack() != null && price.getStack().stackSize > 0 && offer.getStack() != null && offer.getStack().stackSize > 0 && payment.getStack() != null && payment.getStack().stackSize > 0;
	}
}