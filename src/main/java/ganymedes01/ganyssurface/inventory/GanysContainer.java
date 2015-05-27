package ganymedes01.ganyssurface.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class GanysContainer extends Container {

	private final IInventory inventory;

	public GanysContainer(IInventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public final boolean canInteractWith(EntityPlayer player) {
		return inventory == null || inventory.isUseableByPlayer(player);
	}
}