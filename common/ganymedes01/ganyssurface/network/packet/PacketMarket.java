package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.network.PacketTypeHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PacketMarket extends CustomPacket {

	private int x, y, z;
	private String owner;
	private ItemStack[] inventory;
	private ArrayList<ItemStack> extraInventory;

	public PacketMarket() {
		super(PacketTypeHandler.MARKET);
	}

	public PacketMarket(int x, int y, int z, String owner, ItemStack[] inventory, ArrayList<ItemStack> extraInventory) {
		super(PacketTypeHandler.MARKET);
		this.x = x;
		this.y = y;
		this.z = z;
		this.owner = owner;
		this.inventory = inventory;
		this.extraInventory = extraInventory;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeUTF(owner);
		data.writeInt(extraInventory.size());

		for (int i = 0; i < inventory.length; i++)
			Packet.writeItemStack(inventory[i], data);
		for (int i = 0; i < extraInventory.size(); i++)
			Packet.writeItemStack(extraInventory.get(i), data);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		owner = data.readUTF();
		int length = data.readInt();

		inventory = new ItemStack[32];
		for (int i = 0; i < inventory.length; i++)
			inventory[i] = Packet.readItemStack(data);
		extraInventory = new ArrayList<ItemStack>();
		for (int i = 0; i < length; i++)
			extraInventory.add(Packet.readItemStack(data));
	}

	@Override
	public void execute() {
		GanysSurface.proxy.handleMarketPacket(x, y, z, owner, inventory, extraInventory);
	}
}