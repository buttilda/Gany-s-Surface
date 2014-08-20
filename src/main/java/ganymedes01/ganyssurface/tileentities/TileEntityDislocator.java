package ganymedes01.ganyssurface.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityDislocator extends TileEntity {

	public boolean activated;

	public TileEntityDislocator() {
		activated = true;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		activated = data.getBoolean("activated");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setBoolean("activated", activated);
	}

	@Override
	public boolean canUpdate() {
		return false;
	}
}