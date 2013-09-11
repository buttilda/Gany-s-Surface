package ganymedes01.ganyssurface.tileentities;

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
		if (worldObj != null && !worldObj.isRemote) {
			blockType = getBlockType();
			if (blockType != null && blockType instanceof RainDetector)
				((RainDetector) blockType).updateRainStatus(worldObj, xCoord, yCoord, zCoord, worldObj.isRaining());
		}
	}
}
