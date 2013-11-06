package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.network.PacketTypeHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PacketItemDisplay extends CustomPacket {

	private int x, y, z;
	private int itemID, meta, stacksize;

	public PacketItemDisplay() {
		super(PacketTypeHandler.ITEM_DISPLAY);
	}

	public PacketItemDisplay(int x, int y, int z, ItemStack stack) {
		super(PacketTypeHandler.ITEM_DISPLAY);
		this.x = x;
		this.y = y;
		this.z = z;
		if (stack != null) {
			itemID = stack.itemID;
			meta = stack.getItemDamage();
			stacksize = stack.stackSize;
		} else {
			itemID = 0;
			meta = 0;
			stacksize = 0;
		}
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeInt(itemID);
		data.writeInt(meta);
		data.writeInt(stacksize);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		itemID = data.readInt();
		meta = data.readInt();
		stacksize = data.readInt();
	}

	@Override
	public void execute() {
		GanysSurface.proxy.handleItemDisplayPacket(x, y, z, itemID, meta, stacksize);
	}
}