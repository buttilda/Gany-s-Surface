package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.CubicSensoringDislocator;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

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
}