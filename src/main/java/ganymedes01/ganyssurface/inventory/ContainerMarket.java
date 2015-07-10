package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.BetterSlot;
import ganymedes01.ganyssurface.inventory.slots.MarketSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
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

public class ContainerMarket extends GanysContainer {

	private final TileEntityMarket market;

	public ContainerMarket(InventoryPlayer player, TileEntityMarket market) {
		super(market);
		this.market = market;

		// Display product
		addSlotToContainer(new MarketSlot(market, 0, 12, 29));
		// Display cost
		for (int i = 0; i < 6; i++)
			addSlotToContainer(new MarketSlot(market, 1 + i, 55 + i * 18, 29));
		// Payment
		for (int i = 0; i < 6; i++)
			addSlotToContainer(new BetterSlot(market, 7 + i, 8 + i * 18, 71));
		// Purchase
		addSlotToContainer(new BetterSlot(market, 13, 141, 71));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 110 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 168));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();

			if (slotIndex > 6) {
				if (slotIndex > 13) {
					boolean merged = false;
					for (int i = 7; i < 13; i++)
						if (market.isItemValidForSlot(i, stack))
							if (!mergeItemStack(stack, i, i + 1, false))
								return null;
							else
								merged = true;
					if (!merged)
						return null;
				} else if (!mergeItemStack(stack, 14, 50, true))
					return null;
			} else
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
	public ItemStack slotClick(int slotIndex, int button, int shift, EntityPlayer player) {
		if (slotIndex >= 0) {
			Slot slot = getSlot(slotIndex);
			if (slot instanceof MarketSlot) {
				if (((MarketSlot) slot).isOwner(player))
					if (slot.getHasStack())
						slot.putStack(null);
					else if (player.inventory.getItemStack() != null) {
						ItemStack copy = player.inventory.getItemStack().copy();
						slot.putStack(copy);
					}
				return null;
			}
		}
		return super.slotClick(slotIndex, button, shift, player);
	}
}