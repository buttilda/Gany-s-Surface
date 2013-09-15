package ganymedes01.ganyssurface.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerVanillaFurnace extends ContainerFurnace {

	public ContainerVanillaFurnace(InventoryPlayer player, TileEntityFurnace tile) {
		super(player, tile);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
