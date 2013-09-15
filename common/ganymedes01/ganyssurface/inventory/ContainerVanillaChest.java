package ganymedes01.ganyssurface.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerVanillaChest extends ContainerChest {

	public ContainerVanillaChest(IInventory player, IInventory tile) {
		super(player, tile);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
