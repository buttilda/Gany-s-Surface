package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.Dislocator;
import ganymedes01.ganyssurface.blocks.SensoringDislocator;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.common.ForgeDirection;
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
		return checkNearbyBlocks(Dislocator.getDirectionFromMetadata(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)));
	}

	public boolean checkNearbyBlocks(ForgeDirection dir) {
		if (inventory[0] == null)
			return false;
		if (inventory[0].getItem() instanceof ItemSeeds) {
			int cropID = ((ItemSeeds) inventory[0].getItem()).getPlantID(worldObj, xCoord, yCoord, zCoord);
			if (worldObj.getBlockId(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == cropID) {
				coolDown = 0;
				if (worldObj.getBlockMetadata(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) >= 7)
					return true;
			}
		}
		if (inventory[0].getItem() instanceof ItemReed) {
			if (checkIdPicked(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ))
				return true;
		} else if (inventory[0].getItem() instanceof ItemBucket) {
			if (inventory[0].getItem().itemID == Item.bucketLava.itemID) {
				if (worldObj.getBlockMaterial(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == Material.lava)
					return true;
			} else if (inventory[0].getItem().itemID == Item.bucketWater.itemID)
				if (worldObj.getBlockMaterial(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == Material.water)
					return true;
		} else if (worldObj.getBlockId(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == inventory[0].itemID)
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
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection side) {
		if (type == PipeType.ITEM) {
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if (meta == 1 && side == ForgeDirection.UP || meta == 0 && side == ForgeDirection.DOWN || meta == 2 && side == ForgeDirection.SOUTH || meta == 3 && side == ForgeDirection.NORTH || meta == 4 && side == ForgeDirection.EAST || meta == 5 && side == ForgeDirection.WEST)
				return ConnectOverride.CONNECT;
		}
		return ConnectOverride.DISCONNECT;
	}
}