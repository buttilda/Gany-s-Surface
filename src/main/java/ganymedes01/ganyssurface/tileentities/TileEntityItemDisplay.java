package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
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
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return ConnectOverride.DISCONNECT;
	}

	@Override
	public boolean canUpdate() {
		return false;
	}
}