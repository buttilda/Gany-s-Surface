package ganymedes01.ganyssurface.network;

import io.netty.buffer.ByteBuf;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public interface IPacketHandlingTile {

	void readPacketData(ByteBuf buffer);
}