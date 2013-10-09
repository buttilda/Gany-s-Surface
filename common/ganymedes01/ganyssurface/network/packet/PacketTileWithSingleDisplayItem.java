package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.network.PacketTypeHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PacketTileWithSingleDisplayItem extends CustomPacket {

	public int x, y, z;
	public int itemID, meta, stackSize;

	public PacketTileWithSingleDisplayItem() {
		super(PacketTypeHandler.TILE_WITH_SINGLE_DISPLAY_ITEM);
	}

	public PacketTileWithSingleDisplayItem(int x, int y, int z, int itemID, int meta, int stackSize) {
		super(PacketTypeHandler.TILE_WITH_SINGLE_DISPLAY_ITEM);
		this.x = x;
		this.y = y;
		this.z = z;
		this.itemID = itemID;
		this.meta = meta;
		this.stackSize = stackSize;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeInt(itemID);
		data.writeInt(meta);
		data.writeInt(stackSize);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		itemID = data.readInt();
		meta = data.readInt();
		stackSize = data.readInt();
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		GanysSurface.proxy.handleTileWithSingleDisplayItemPacket(x, y, z, itemID, meta, stackSize);
	}
}
