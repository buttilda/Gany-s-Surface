package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.BlockWoodSign;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntitySign;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityWoodSign extends TileEntitySign {

	public boolean isStanding = false;

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("IsStanding", isStanding);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isStanding = nbt.getBoolean("IsStanding");
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		if (pkt.func_148853_f() == 0)
			readFromNBT(pkt.func_148857_g());
	}

	public int getTextColour() {
		BlockWoodSign sign = (BlockWoodSign) getBlockType();
		if (sign != null)
			if (sign.woodMeta == 5)
				return 0xFFE500;
			else if (sign.woodMeta == 3 || sign.woodMeta == 1)
				return 0xFFFFFF;
		return 0x000000;
	}

	public boolean textHasShadow() {
		BlockWoodSign sign = (BlockWoodSign) getBlockType();
		return sign != null && (sign.woodMeta == 3 || sign.woodMeta == 1);
	}
}