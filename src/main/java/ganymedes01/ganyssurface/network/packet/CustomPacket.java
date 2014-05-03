package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.network.PacketHandler.PacketType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public abstract class CustomPacket {

	final PacketType type;

	CustomPacket(PacketType type) {
		this.type = type;
	}

	public final int getType() {
		return type.ordinal();
	}

	public abstract void writeData(ByteBuf buffer);

	public abstract void readData(ByteBuf buffer);

	public abstract void handleClientSide(EntityPlayer player);

	public abstract void handleServerSide(EntityPlayer player);
}