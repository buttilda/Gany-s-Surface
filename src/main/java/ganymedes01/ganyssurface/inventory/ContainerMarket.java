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

	public ContainerMarket(InventoryPlayer player, TileEntityMarket market) {
		super(market);

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
		return null;
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