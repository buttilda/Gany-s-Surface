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

public class PacketGUINoRecipeConflict2 extends CustomPacket {

	private boolean isFirstMatrix, hasMultipleResults;

	public PacketGUINoRecipeConflict2() {
		super(PacketType.GUI_NO_RECIPE_CONFLICT_2);
	}

	public PacketGUINoRecipeConflict2(boolean isFirstMatrix, boolean hasMultipleResults) {
		super(PacketType.GUI_NO_RECIPE_CONFLICT_2);
		this.isFirstMatrix = isFirstMatrix;
		this.hasMultipleResults = hasMultipleResults;
	}

	@Override
	public void writeData(ByteBuf buffer) {
		buffer.writeBoolean(isFirstMatrix);
		buffer.writeBoolean(hasMultipleResults);
	}

	@Override
	public void readData(ByteBuf buffer) {
		isFirstMatrix = buffer.readBoolean();
		hasMultipleResults = buffer.readBoolean();
	}

	@Override
	public void handleClientSide(World world, EntityPlayer player) {
		if (player.openContainer instanceof INoConflictRecipeContainer) {
			INoConflictRecipeContainer container = (INoConflictRecipeContainer) player.openContainer;
			container.setHasMultipleResults(isFirstMatrix, hasMultipleResults);
		}
	}

	@Override
	public void handleServerSide(World world, EntityPlayer player) {
	}
}