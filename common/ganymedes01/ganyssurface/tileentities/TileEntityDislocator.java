package ganymedes01.ganyssurface.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

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
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if (side == ForgeDirection.UP)
				return meta == 1 ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
			else if (side == ForgeDirection.DOWN)
				return meta == 0 ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
			else
				return meta == side.getOpposite().ordinal() ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
		}
		return ConnectOverride.DISCONNECT;
	}
}
