package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.CubicSensoringDislocator;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.transport.IPipeTile.PipeType;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityCubicSensoringDislocator extends TileEntitySensoringDislocator {

	@Override
	public void updateRedstoneSignalStatus(boolean flag) {
		blockType = getBlockType();
		if (blockType != null && blockType instanceof CubicSensoringDislocator)
			((CubicSensoringDislocator) blockType).doBreak(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public String getInventoryName() {
		return Utils.getConainerName(Strings.CUBIC_SENSORING_DISLOCATOR_NAME);
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return type == PipeType.ITEM ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
	}
}