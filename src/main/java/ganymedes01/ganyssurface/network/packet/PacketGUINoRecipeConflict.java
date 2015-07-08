package ganymedes01.ganyssurface.network.packet;

import ganymedes01.ganyssurface.inventory.INoConflictRecipeContainer;
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

public class PacketGUINoRecipeConflict extends CustomPacket {

	private int value;
	private boolean isFirstMatrix;

	public PacketGUINoRecipeConflict() {
		super(PacketType.GUI_NO_RECIPE_CONFLICT);
	}

	public PacketGUINoRecipeConflict(boolean isFirstMatrix, int value) {
		super(PacketType.GUI_NO_RECIPE_CONFLICT);
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
		if (player.openContainer instanceof INoConflictRecipeContainer) {
			INoConflictRecipeContainer container = (INoConflictRecipeContainer) player.openContainer;
			container.setCurrentResultIndex(isFirstMatrix, value);
		}
	}

	@Override
	public void handleServerSide(World world, EntityPlayer player) {
		if (player.openContainer instanceof INoConflictRecipeContainer) {
			INoConflictRecipeContainer container = (INoConflictRecipeContainer) player.openContainer;
			container.handleButtonClick(isFirstMatrix, value);
		}
	}
}