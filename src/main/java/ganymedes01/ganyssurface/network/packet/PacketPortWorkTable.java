package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.network.PacketHandler.PacketType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class PacketPortWorkTable extends CustomPacket {

	private int slot;

	public PacketPortWorkTable() {
		super(PacketType.PORT_WORKTABLE);
	}

	public PacketPortWorkTable(int slot) {
		super(PacketType.PORT_WORKTABLE);
		this.slot = slot;
	}

	@Override
	public void writeData(ByteBuf buffer) {
		buffer.writeInt(slot);
	}

	@Override
	public void readData(ByteBuf buffer) {
		slot = buffer.readInt();
	}

	@Override
	public void handleClientSide(World world, EntityPlayer player) {
	}

	@Override
	public void handleServerSide(World world, EntityPlayer player) {
		player.openGui(GanysSurface.instance, GUIsID.PORTABLE_DUAL_WORK_TABLE.ordinal(), world, slot, 0, 0);
	}
}