package ganymedes01.ganyssurface.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.tileentity.TileEntityBrewingStand;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerVanillaBrewingStand extends ContainerBrewingStand {

	public ContainerVanillaBrewingStand(InventoryPlayer player, TileEntityBrewingStand tile) {
		super(player, tile);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
