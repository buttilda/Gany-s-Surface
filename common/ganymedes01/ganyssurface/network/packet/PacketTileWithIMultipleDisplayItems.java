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

public class PacketTileWithIMultipleDisplayItems extends CustomPacket {

	public int x, y, z, invSize;
	public int[] itemID, metaData, stackSize;

	public PacketTileWithIMultipleDisplayItems() {
		super(PacketTypeHandler.TILE_WITH_MULTIPLE_DISPLAY_ITEMS);
	}

	public PacketTileWithIMultipleDisplayItems(int x, int y, int z, int[] itemID, int[] metaData, int[] stackSize, int invSize) {
		super(PacketTypeHandler.TILE_WITH_MULTIPLE_DISPLAY_ITEMS);
		this.x = x;
		this.y = y;
		this.z = z;
		this.invSize = invSize;
		this.itemID = itemID;
		this.metaData = metaData;
		this.stackSize = stackSize;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeInt(invSize);
		for (int i = 0; i < invSize; i++) {
			data.writeInt(itemID[i]);
			data.writeInt(metaData[i]);
			data.writeInt(stackSize[i]);
		}
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		invSize = data.readInt();
		itemID = new int[invSize];
		metaData = new int[invSize];
		stackSize = new int[invSize];
		for (int i = 0; i < invSize; i++) {
			itemID[i] = data.readInt();
			metaData[i] = data.readInt();
			stackSize[i] = data.readInt();
		}
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		GanysSurface.proxy.handleTileWithMultipleDisplayItemsPacket(x, y, z, itemID, metaData, stackSize, invSize);
	}
}
