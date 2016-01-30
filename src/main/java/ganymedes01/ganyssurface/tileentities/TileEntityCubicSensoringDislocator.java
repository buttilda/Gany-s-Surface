package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.blocks.CubicSensoringDislocator;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityCubicSensoringDislocator extends TileEntitySensoringDislocator {

	public TileEntityCubicSensoringDislocator() {
		super(5, Strings.CUBIC_SENSORING_DISLOCATOR_NAME);
	}

	@Override
	protected void updateStatus() {
		Block block = getBlockType();
		if (block == ModBlocks.cubicSensitiveDislocator && redstoneStrength <= 0)
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				if (checkBlock(dir))
					((CubicSensoringDislocator) block).breakSurroundingBlock(worldObj, xCoord, yCoord, zCoord, dir);
	}
}