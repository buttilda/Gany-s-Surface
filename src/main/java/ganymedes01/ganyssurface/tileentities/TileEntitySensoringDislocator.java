package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.blocks.Dislocator;
import ganymedes01.ganyssurface.blocks.SensoringDislocator;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntitySensoringDislocator extends TileEntityBlockDetector {

	public int redstoneStrength = 0;

	public TileEntitySensoringDislocator() {
		this(10, Strings.SENSORING_DISLOCATOR_NAME);
	}

	protected TileEntitySensoringDislocator(int maxCoolDown, String name) {
		super(maxCoolDown, name);
	}

	@Override
	public boolean checkNearbyBlocks() {
		return checkBlock(Dislocator.getDirectionFromMetadata(worldObj.getBlockMetadata(xCoord, yCoord, zCoord))) && redstoneStrength <= 0;
	}

	@Override
	protected void updateStatus() {
		if (checkNearbyBlocks())
			((SensoringDislocator) ModBlocks.sensitiveDislocator).breakSurroundingBlock(worldObj, xCoord, yCoord, zCoord, Dislocator.getDirectionFromMetadata(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		redstoneStrength = nbt.getInteger("RedstoneStrength");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("RedstoneStrength", redstoneStrength);
	}
}