package ganymedes01.ganyssurface.inventory.slots;

import ganymedes01.ganyssurface.core.utils.ItemStackMap;
import ganymedes01.ganyssurface.inventory.ContainerMarketPublic;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PaymentSlot extends Slot {

	private final Slot reference;
	private final ContainerMarketPublic container;

	public PaymentSlot(TileEntityMarket inventory, int slot, int posX, int posY, Slot reference, ContainerMarketPublic container) {
		super(inventory, slot, posX, posY);
		this.reference = reference;
		this.container = container;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return ItemStackMap.areItemsEqual(reference.getStack(), stack);
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		container.updateRetrievalSlots();
	}
}