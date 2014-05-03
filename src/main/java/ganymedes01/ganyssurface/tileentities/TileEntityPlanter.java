package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.IPacketHandlingTile;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity.TileData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

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
				if (!worldObj.isAirBlock(xCoord, yCoord - 2, zCoord))
					for (int i = 0; i < inventory.length; i++) {
						if (inventory[i] == null)
							continue;
						if (inventory[i].getItem() instanceof IPlantable) {
							IPlantable seed = (IPlantable) inventory[i].getItem();
							Block soil = worldObj.getBlock(xCoord, yCoord - 2, zCoord);
							if (soil.canSustainPlant(worldObj, xCoord, yCoord - 2, zCoord, ForgeDirection.UP, seed)) {
								armExtension += 0.01F;
								if (armExtension >= 0.5F) {
									worldObj.setBlock(xCoord, yCoord - 1, zCoord, seed.getPlant(worldObj, xCoord, yCoord, zCoord));
									inventory[i].stackSize--;
									if (inventory[i].stackSize <= 0)
										inventory[i] = null;
									isReturning = true;
								}
								update();
							}
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
		PacketHandler.INSTANCE.sendToAll(new PacketTileEntity(this, new TileData() {

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
}