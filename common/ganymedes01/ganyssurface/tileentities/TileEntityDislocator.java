package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.Dislocator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityDislocator extends TileEntity implements IPipeConnection {

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
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection side) {
		if (type == PipeType.ITEM) {
			ForgeDirection dir = Dislocator.getDirectionFromMetadata(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
			return dir.getOpposite() == side ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
		}
		return ConnectOverride.DISCONNECT;
	}

	@Override
	public boolean canUpdate() {
		return false;
	}
}