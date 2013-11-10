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

public class PacketMarket extends CustomPacket {

	private int x, y, z;
	private String owner;

	public PacketMarket() {
		super(PacketTypeHandler.MARKET);
	}

	public PacketMarket(int x, int y, int z, String owner) {
		super(PacketTypeHandler.MARKET);
		this.x = x;
		this.y = y;
		this.z = z;
		this.owner = owner;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeUTF(owner);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		owner = data.readUTF();
	}

	@Override
	public void execute() {
		GanysSurface.proxy.handleMarketPacket(x, y, z, owner);
	}
}