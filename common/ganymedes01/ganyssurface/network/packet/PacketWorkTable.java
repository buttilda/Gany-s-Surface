package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.network.PacketTypeHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PacketWorkTable extends CustomPacket {

	private int x, y, z;
	private int[] itemID, metaData, stackSize;

	public PacketWorkTable() {
		super(PacketTypeHandler.WORK_TABLE);
	}

	public PacketWorkTable(int x, int y, int z, int[] itemID, int[] metaData, int[] stackSize) {
		super(PacketTypeHandler.WORK_TABLE);
		this.x = x;
		this.y = y;
		this.z = z;
		this.itemID = itemID;
		this.metaData = metaData;
		this.stackSize = stackSize;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		for (int i = 0; i < 9; i++) {
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
		itemID = new int[9];
		metaData = new int[9];
		stackSize = new int[9];
		for (int i = 0; i < 9; i++) {
			itemID[i] = data.readInt();
			metaData[i] = data.readInt();
			stackSize[i] = data.readInt();
		}
	}

	@Override
	public void execute() {
		GanysSurface.proxy.handleWorkTablePacket(x, y, z, itemID, metaData, stackSize);
	}
}