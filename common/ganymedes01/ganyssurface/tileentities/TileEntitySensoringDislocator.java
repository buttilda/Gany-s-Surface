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
import net.minecraft.item.ItemSkull;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
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
		int x = xCoord + dir.offsetX;
		int y = yCoord + dir.offsetY;
		int z = zCoord + dir.offsetZ;

		if (inventory[0] == null)
			return false;
		if (inventory[0].getItem() instanceof ItemSeeds)
			if (worldObj.getBlockId(x, y, z) == ((ItemSeeds) inventory[0].getItem()).getPlantID(worldObj, xCoord, yCoord, zCoord)) {
				coolDown = 0;
				return worldObj.getBlockMetadata(x, y, z) >= 7;
			}
		if (inventory[0].getItem() instanceof ItemReed)
			return checkIdPicked(x, y, z);
		else if (inventory[0].getItem() instanceof ItemBucket) {
			if (inventory[0].getItem().itemID == Item.bucketLava.itemID)
				return worldObj.getBlockMaterial(x, y, z) == Material.lava;
			else if (inventory[0].getItem().itemID == Item.bucketWater.itemID)
				return worldObj.getBlockMaterial(x, y, z) == Material.water;
		} else if (inventory[0].getItem() instanceof ItemSkull) {
			TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
			if (tile != null && tile instanceof TileEntitySkull)
				return ((TileEntitySkull) tile).getSkullType() == inventory[0].getItemDamage();
		} else
			return worldObj.getBlockId(x, y, z) == inventory[0].itemID;
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