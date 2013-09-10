package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.CubicSensoringDislocator;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemReed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntityCubicSensoringDislocator extends TileEntitySensoringDislocator {

	public boolean activated;

	public TileEntityCubicSensoringDislocator() {
		activated = true;
	}

	@Override
	public boolean checkNearbyBlocks() {
		if (inventory[0] == null)
			return false;

		if (inventory[0].getItem() instanceof ItemReed) {
			if (checkIdPicked(xCoord + 1, yCoord, zCoord) || checkIdPicked(xCoord - 1, yCoord, zCoord) || checkIdPicked(xCoord, yCoord + 1, zCoord) || checkIdPicked(xCoord, yCoord - 1, zCoord) || checkIdPicked(xCoord, yCoord, zCoord + 1) || checkIdPicked(xCoord, yCoord, zCoord - 1))
				return true;

		} else if (inventory[0].getItem() instanceof ItemBucket) {
			if (inventory[0].getItem().itemID == Item.bucketLava.itemID) {
				if (worldObj.getBlockMaterial(xCoord + 1, yCoord, zCoord) == Material.lava || worldObj.getBlockMaterial(xCoord - 1, yCoord, zCoord) == Material.lava || worldObj.getBlockMaterial(xCoord, yCoord, zCoord + 1) == Material.lava ||
				worldObj.getBlockMaterial(xCoord, yCoord, zCoord - 1) == Material.lava || worldObj.getBlockMaterial(xCoord, yCoord + 1, zCoord) == Material.lava || worldObj.getBlockMaterial(xCoord, yCoord - 1, zCoord) == Material.lava)
					return true;

			} else if (inventory[0].getItem().itemID == Item.bucketWater.itemID)
				if (worldObj.getBlockMaterial(xCoord + 1, yCoord, zCoord) == Material.water || worldObj.getBlockMaterial(xCoord - 1, yCoord, zCoord) == Material.water || worldObj.getBlockMaterial(xCoord, yCoord, zCoord + 1) == Material.water ||
				worldObj.getBlockMaterial(xCoord, yCoord, zCoord - 1) == Material.water || worldObj.getBlockMaterial(xCoord, yCoord + 1, zCoord) == Material.water || worldObj.getBlockMaterial(xCoord, yCoord - 1, zCoord) == Material.water)
					return true;

		} else if (worldObj.getBlockId(xCoord + 1, yCoord, zCoord) == inventory[0].itemID || worldObj.getBlockId(xCoord - 1, yCoord, zCoord) == inventory[0].itemID || worldObj.getBlockId(xCoord, yCoord, zCoord + 1) == inventory[0].itemID ||
		worldObj.getBlockId(xCoord, yCoord, zCoord - 1) == inventory[0].itemID || worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == inventory[0].itemID || worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == inventory[0].itemID)
			return true;

		return false;
	}

	public boolean checkBlock(int x, int y, int z) {
		if (inventory[0] == null)
			return false;
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
		if (blockType != null && blockType instanceof CubicSensoringDislocator)
			((CubicSensoringDislocator) blockType).doBreak(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public String getInvName() {
		return Utils.getConainerName(Strings.CUBIC_SENSORING_DISLOCATOR_NAME);
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
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		if (type == PipeType.ITEM)
			if (with == ForgeDirection.UP || with == ForgeDirection.DOWN)
				return ConnectOverride.CONNECT;
		return ConnectOverride.DISCONNECT;
	}
}
