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

public class PacketPlanter extends CustomPacket {

	private int x, y, z;
	private float armExtension;

	public PacketPlanter() {
		super(PacketTypeHandler.PLANTER);
	}

	public PacketPlanter(int x, int y, int z, float armExtension) {
		super(PacketTypeHandler.PLANTER);
		this.x = x;
		this.y = y;
		this.z = z;
		this.armExtension = armExtension;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeFloat(armExtension);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		armExtension = data.readFloat();
	}

	@Override
	public void execute() {
		GanysSurface.proxy.handlePlanterPacket(x, y, z, armExtension);
	}
}