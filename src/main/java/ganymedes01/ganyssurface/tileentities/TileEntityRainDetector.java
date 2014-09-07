package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.blocks.RainDetector;
import net.minecraft.tileentity.TileEntity;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityRainDetector extends TileEntity {

	@Override
	public void updateEntity() {
		if (worldObj != null && !worldObj.isRemote)
			if (getBlockType() == ModBlocks.rainDetector)
				RainDetector.updateRainStatus(worldObj, xCoord, yCoord, zCoord, worldObj.isRaining());
	}
}
