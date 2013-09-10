package ganymedes01.ganyssurface.network;

import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.network.packet.CustomPacket;
import ganymedes01.ganyssurface.network.packet.PacketTileWithIMultipleDisplayItems;
import ganymedes01.ganyssurface.network.packet.PacketTileWithISingleDisplayItem;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public enum PacketTypeHandler {
	TILE_WITH_SINGLE_DISPLAY_ITEM(PacketTileWithISingleDisplayItem.class),
	TILE_WITH_MULTIPLE_DISPLAY_ITEMS(PacketTileWithIMultipleDisplayItems.class);

	private Class<? extends CustomPacket> clazz;

	PacketTypeHandler(Class<? extends CustomPacket> clazz) {
		this.clazz = clazz;
	}

	public static CustomPacket buildPacket(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int selector = bis.read();
		DataInputStream dis = new DataInputStream(bis);

		CustomPacket packet = null;

		try {
			packet = values()[selector].clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		packet.readPopulate(dis);

		return packet;
	}

	public static CustomPacket buildPacket(PacketTypeHandler type) {
		CustomPacket packet = null;

		try {
			packet = values()[type.ordinal()].clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return packet;
	}

	public static Packet populatePacket(CustomPacket customPacket) {
		byte[] data = customPacket.populate();

		Packet250CustomPayload packet250 = new Packet250CustomPayload();
		packet250.channel = Reference.CHANNEL_NAME;
		packet250.data = data;
		packet250.length = data.length;

		return packet250;
	}
}
