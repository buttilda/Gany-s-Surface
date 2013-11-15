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

public class PacketWorkTable extends CustomPacket {

	private int x, y, z;
	private ItemStack[] inventory;

	public PacketWorkTable() {
		super(PacketTypeHandler.WORK_TABLE);
	}

	public PacketWorkTable(int x, int y, int z, ItemStack[] inventory) {
		super(PacketTypeHandler.WORK_TABLE);
		this.x = x;
		this.y = y;
		this.z = z;
		this.inventory = inventory;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeInt(inventory.length);
		for (int i = 0; i < inventory.length; i++)
			Packet.writeItemStack(inventory[i], data);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();

		inventory = new ItemStack[data.readInt()];
		for (int i = 0; i < inventory.length; i++)
			inventory[i] = Packet.readItemStack(data);
	}

	@Override
	public void execute() {
		GanysSurface.proxy.handleWorkTablePacket(x, y, z, inventory);
	}
}