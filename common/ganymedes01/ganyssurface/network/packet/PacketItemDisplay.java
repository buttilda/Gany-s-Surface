package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.network.PacketTypeHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PacketItemDisplay extends CustomPacket {

	private int x, y, z;
	private ItemStack stack;

	public PacketItemDisplay() {
		super(PacketTypeHandler.ITEM_DISPLAY);
	}

	public PacketItemDisplay(int x, int y, int z, ItemStack stack) {
		super(PacketTypeHandler.ITEM_DISPLAY);
		this.x = x;
		this.y = y;
		this.z = z;
		this.stack = stack;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		Packet.writeItemStack(stack, data);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		stack = Packet.readItemStack(data);
	}

	@Override
	public void execute() {
		GanysSurface.proxy.handleItemDisplayPacket(x, y, z, stack);
	}
}