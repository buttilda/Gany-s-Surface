package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.Dislocator;
import ganymedes01.ganyssurface.blocks.SensoringDislocator;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntitySensoringDislocator extends TileEntityBlockDetector implements IPipeConnection {

	@Override
	public boolean checkNearbyBlocks() {
		return checkBlock(Dislocator.getDirectionFromMetadata(worldObj.getBlockMetadata(xCoord, yCoord, zCoord))) && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateRedstoneSignalStatus(boolean flag) {
		blockType = getBlockType();
		if (blockType != null && blockType instanceof SensoringDislocator)
			((SensoringDislocator) blockType).doBreak(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public String getInventoryName() {
		return Utils.getConainerName(Strings.SENSORING_DISLOCATOR_NAME);
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection side) {
		if (type == PipeType.ITEM) {
			ForgeDirection dir = Dislocator.getDirectionFromMetadata(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
			return dir.getOpposite() == side ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
		}
		return ConnectOverride.DISCONNECT;
	}
}