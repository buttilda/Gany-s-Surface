package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketItemDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityItemDisplay extends GanysInventory implements IPipeConnection {

	public TileEntityItemDisplay() {
		super(1, Strings.ITEM_DISPLAY_NAME);
	}

	public ItemStack getDisplayItem() {
		return inventory[0];
	}

	public void addItemToDisplay(ItemStack item) {
		if (item != null) {
			inventory[0] = item.copy();
			inventory[0].stackSize = 1;
		} else
			inventory[0] = null;
	}

	@Override
	public Packet getDescriptionPacket() {
		if (inventory[0] != null && inventory[0].stackSize > 0)
			return PacketTypeHandler.populatePacket(new PacketItemDisplay(xCoord, yCoord, zCoord, inventory[0].copy()));
		return null;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return ConnectOverride.DISCONNECT;
	}
}