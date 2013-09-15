package ganymedes01.ganyssurface.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityDispenser;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerVanillaDispenser extends ContainerDispenser {

	public ContainerVanillaDispenser(IInventory player, TileEntityDispenser tile) {
		super(player, tile);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
