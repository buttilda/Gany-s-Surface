package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.SensoringDislocator;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemSeeds;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntitySensoringDislocator extends TileEntityBlockDetector implements IPipeConnection {

	public boolean activated;

	public TileEntitySensoringDislocator() {
		activated = true;
	}

	@Override
	public boolean checkNearbyBlocks() {
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

		int x = 0, y = 0, z = 0;

		switch (meta) {
			case 0:
				x = xCoord;
				y = yCoord + 1;
				z = zCoord;
				break;
			case 1:
				x = xCoord;
				y = yCoord - 1;
				z = zCoord;
				break;
			case 2:
				x = xCoord;
				y = yCoord;
				z = zCoord - 1;
				break;
			case 3:
				x = xCoord;
				y = yCoord;
				z = zCoord + 1;
				break;
			case 4:
				x = xCoord - 1;
				y = yCoord;
				z = zCoord;
				break;
			case 5:
				x = xCoord + 1;
				y = yCoord;
				z = zCoord;
				break;
		}

		if (inventory[0] == null)
			return false;
		if (inventory[0].getItem() instanceof ItemSeeds) {
			int cropID = ((ItemSeeds) inventory[0].getItem()).getPlantID(worldObj, xCoord, yCoord, zCoord);
			if (worldObj.getBlockId(x, y, z) == cropID) {
				coolDown = 0;
				if (worldObj.getBlockMetadata(x, y, z) >= 7)
					return true;
			}
		}
		if (inventory[0].getItem() instanceof ItemReed) {
			if (checkIdPicked(x, y, z))
				return true;
		} else if (inventory[0].getItem() instanceof ItemBucket) {
			if (inventory[0].getItem().itemID == Item.bucketLava.itemID) {
				if (worldObj.getBlockMaterial(x, y, z) == Material.lava)
					return true;
			} else if (inventory[0].getItem().itemID == Item.bucketWater.itemID)
				if (worldObj.getBlockMaterial(x, y, z) == Material.water)
					return true;
		} else if (worldObj.getBlockId(x, y, z) == inventory[0].itemID)
			return true;
		return false;
	}

	@Override
	public void updateRedstoneSignalStatus(boolean flag) {
		blockType = getBlockType();
		if (blockType != null && blockType instanceof SensoringDislocator)
			((SensoringDislocator) blockType).doBreak(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public String getInvName() {
		return Utils.getConainerName(Strings.SENSORING_DISLOCATOR_NAME);
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
			if (meta == 1 && side == ForgeDirection.UP || meta == 0 && side == ForgeDirection.DOWN || meta == 2 && side == ForgeDirection.SOUTH || meta == 3 && side == ForgeDirection.NORTH || meta == 4 && side == ForgeDirection.EAST || meta == 5 && side == ForgeDirection.WEST)
				return ConnectOverride.CONNECT;
		}
		return ConnectOverride.DISCONNECT;
	}
}
