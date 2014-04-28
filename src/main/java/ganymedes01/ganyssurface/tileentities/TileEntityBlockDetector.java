package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.blocks.BlockDetector;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.ReflectionHelper;

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
			if (inventory[0].getItem() instanceof IPlantable || inventory[0].getItem() == Items.dye && inventory[0].getItemDamage() == 3) {
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

		Block id = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);

		if (inventory[0] == null)
			return false;
		if (inventory[0].getItem() instanceof IPlantable)
			if (id == ((IPlantable) inventory[0].getItem()).getPlant(worldObj, xCoord, yCoord, zCoord)) {
				coolDown = 0;
				int finalMeta = inventory[0].getItem() != Items.nether_wart ? 7 : 3;
				return meta >= finalMeta;
			}
		if (inventory[0].getItem() instanceof ItemReed)
			return checkIdPicked((ItemReed) inventory[0].getItem(), x, y, z);
		else if (inventory[0].getItem() instanceof ItemBucket) {
			if (inventory[0].getItem() == Items.lava_bucket)
				return worldObj.getBlock(x, y, z).getMaterial() == Material.lava;
			else if (inventory[0].getItem() == Items.water_bucket)
				return worldObj.getBlock(x, y, z).getMaterial() == Material.water;
		} else if (inventory[0].getItem() instanceof ItemSkull) {
			TileEntitySkull skull = Utils.getTileEntity(worldObj, x, y, z, TileEntitySkull.class);
			if (skull != null)
				return skull.func_145904_a() == inventory[0].getItemDamage();
		} else if (inventory[0].getItem() == Items.dye && inventory[0].getItemDamage() == 3) {
			coolDown = 0;
			return id instanceof BlockCocoa && BlockCocoa.func_149987_c(meta) >= 2;
		} else
			return id == Block.getBlockFromItem(inventory[0].getItem()) && meta == inventory[0].getItemDamage();
		return false;
	}

	protected boolean checkIdPicked(ItemReed item, int x, int y, int z) {
		if (!worldObj.isAirBlock(x, y, z))
			return worldObj.getBlock(x, y, z) == (Block) ReflectionHelper.getPrivateValue(ItemReed.class, item, "field_150935_a");
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		super.markDirty();
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