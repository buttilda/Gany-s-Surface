package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.BlockDetector;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityBlockDetector extends GanysInventory implements ISidedInventory {

	protected int coolDown = 50;

	public TileEntityBlockDetector() {
		super(1, Strings.BLOCK_DETECTOR_NAME);
	}

	public void updateRedstoneSignalStatus(boolean flag) {
		blockType = getBlockType();
		if (blockType != null && blockType instanceof BlockDetector)
			((BlockDetector) blockType).updateBlockStatus(worldObj, xCoord, yCoord, zCoord, flag);
	}

	@Override
	public void updateEntity() {
		if (inventory[0] != null)
			if (inventory[0].getItem() instanceof IPlantable || inventory[0].getItem() == Item.dyePowder && inventory[0].getItemDamage() == 3) {
				coolDown--;
				if (coolDown <= 0) {
					updateRedstoneSignalStatus(checkNearbyBlocks());
					coolDown = 50;
				}
			}
	}

	public boolean checkNearbyBlocks() {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if (checkBlock(dir))
				return true;
		return false;
	}

	public boolean checkBlock(ForgeDirection dir) {
		int x = xCoord + dir.offsetX;
		int y = yCoord + dir.offsetY;
		int z = zCoord + dir.offsetZ;

		int id = worldObj.getBlockId(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);

		if (inventory[0] == null)
			return false;
		if (inventory[0].getItem() instanceof IPlantable)
			if (id == ((IPlantable) inventory[0].getItem()).getPlantID(worldObj, xCoord, yCoord, zCoord)) {
				coolDown = 0;
				int finalMeta = inventory[0].getItem() != Item.netherStalkSeeds ? 7 : 3;
				return meta >= finalMeta;
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
		} else if (inventory[0].getItem() == Item.dyePowder && inventory[0].getItemDamage() == 3) {
			coolDown = 0;
			return id == Block.cocoaPlant.blockID && BlockCocoa.func_72219_c(meta) >= 2;
		} else
			return id == inventory[0].itemID && meta == inventory[0].getItemDamage();
		return false;
	}

	protected boolean checkIdPicked(int x, int y, int z) {
		if (!worldObj.isAirBlock(x, y, z))
			if (Block.blocksList[worldObj.getBlockId(x, y, z)].idPicked(worldObj, x, y, z) == inventory[0].itemID)
				return true;
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		updateRedstoneSignalStatus(checkNearbyBlocks());
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}
}