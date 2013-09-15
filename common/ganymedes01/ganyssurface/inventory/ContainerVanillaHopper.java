package ganymedes01.ganyssurface.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerVanillaHopper extends ContainerHopper {

	public ContainerVanillaHopper(InventoryPlayer player, IInventory tile) {
		super(player, tile);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
