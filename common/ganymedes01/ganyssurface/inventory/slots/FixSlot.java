package ganymedes01.ganyssurface.inventory.slots;

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

public class FixSlot extends Slot {

	private final String owner;

	public FixSlot(TileEntityMarket inventory, int slot, int posX, int posY) {
		super(inventory, slot, posX, posY);
		owner = inventory.getOwner();
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return owner.compareTo(player.username) == 0;
	}
}