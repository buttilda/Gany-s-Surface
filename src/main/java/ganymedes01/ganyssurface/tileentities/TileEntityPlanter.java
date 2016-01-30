package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.IPacketHandlingTile;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity.TileData;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityPlanter extends GanysInventory implements IPacketHandlingTile {

	private float armExtension = 0.0F;
	private boolean isReturning;

	public TileEntityPlanter() {
		super(9, Strings.PLANTER_NAME);
	}

	protected TileEntityPlanter(String name) {
		super(9, name);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (isReturning) {
			armExtension -= 0.01F;
			if (armExtension <= 0.0F) {
				armExtension = 0.0F;
				isReturning = false;
			}
			update();
		}
		if (!isReturning)
			if (worldObj.isAirBlock(xCoord, yCoord - 1, zCoord))
				for (int i = 0; i < inventory.length; i++) {
					if (inventory[i] == null || inventory[i].stackSize <= 1)
						continue;
					else {
						armExtension += 0.01F;
						if (armExtension >= 0.5F) {
							inventory[i].tryPlaceItemIntoWorld(Utils.getPlayer(worldObj), worldObj, xCoord, yCoord - 2, zCoord, 1, 0, 0, 0);
							if (inventory[i].stackSize <= 0)
								inventory[i] = null;
							isReturning = true;
						}
						update();
						break;
					}
				}
			else
				isReturning = true;
	}

	public float getArmExtension() {
		return armExtension;
	}

	public void setArmExtension(float value) {
		armExtension = value;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		nbt.removeTag("isReturning");
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	private void update() {
		PacketHandler.sendToAll(new PacketTileEntity(this, new TileData() {

			@Override
			public void writeData(ByteBuf buffer) {
				buffer.writeFloat(armExtension);
			}

		}));
	}

	@Override
	public void readPacketData(ByteBuf buffer) {
		armExtension = buffer.readFloat();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null ? stack.getItem() instanceof IPlantable : false;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		armExtension = data.getFloat("armExtension");
		isReturning = data.getBoolean("isReturning");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setFloat("armExtension", armExtension);
		data.setBoolean("isReturning", isReturning);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord - 1, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
}