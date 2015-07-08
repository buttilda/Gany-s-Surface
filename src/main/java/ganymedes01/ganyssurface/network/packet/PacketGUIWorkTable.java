package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.inventory.ContainerWorkTable;
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

public class PacketGUIWorkTable extends CustomPacket {

	private int value;

	public PacketGUIWorkTable() {
		super(PacketType.GUI_WORKTABLE);
	}

	public PacketGUIWorkTable(int value) {
		super(PacketType.GUI_WORKTABLE);
		this.value = value;
	}

	@Override
	public void writeData(ByteBuf buffer) {
		buffer.writeInt(value);
	}

	@Override
	public void readData(ByteBuf buffer) {
		value = buffer.readInt();
	}

	@Override
	public void handleClientSide(World world, EntityPlayer player) {
		if (player.openContainer instanceof ContainerWorkTable) {
			ContainerWorkTable container = (ContainerWorkTable) player.openContainer;
			container.setCurrentResultIndex(value);
		}
	}

	@Override
	public void handleServerSide(World world, EntityPlayer player) {
		if (player.openContainer instanceof ContainerWorkTable) {
			ContainerWorkTable container = (ContainerWorkTable) player.openContainer;
			container.handleButtonClick(value);
		}
	}
}