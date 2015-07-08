package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.inventory.ContainerDualWorkTable;
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

public class PacketGUIDualWorkTable extends CustomPacket {

	private int value;
	private boolean isFirstMatrix;

	public PacketGUIDualWorkTable() {
		super(PacketType.GUI_DUAL_WORKTABLE);
	}

	public PacketGUIDualWorkTable(boolean isFirstMatrix, int value) {
		super(PacketType.GUI_DUAL_WORKTABLE);
		this.isFirstMatrix = isFirstMatrix;
		this.value = value;
	}

	@Override
	public void writeData(ByteBuf buffer) {
		buffer.writeBoolean(isFirstMatrix);
		buffer.writeInt(value);
	}

	@Override
	public void readData(ByteBuf buffer) {
		isFirstMatrix = buffer.readBoolean();
		value = buffer.readInt();
	}

	@Override
	public void handleClientSide(World world, EntityPlayer player) {
		if (player.openContainer instanceof ContainerDualWorkTable) {
			ContainerDualWorkTable container = (ContainerDualWorkTable) player.openContainer;
			container.setCurrentResultIndex(isFirstMatrix, value);
		}
	}

	@Override
	public void handleServerSide(World world, EntityPlayer player) {
		if (player.openContainer instanceof ContainerDualWorkTable) {
			ContainerDualWorkTable container = (ContainerDualWorkTable) player.openContainer;
			container.handleButtonClick(isFirstMatrix, value);
		}
	}
}