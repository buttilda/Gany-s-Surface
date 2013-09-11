package ganymedes01.ganyssurface.network;

import ganymedes01.ganyssurface.network.packet.CustomPacket;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		CustomPacket customPacket = PacketTypeHandler.buildPacket(packet.data);
		customPacket.execute(manager, player);
	}
}
