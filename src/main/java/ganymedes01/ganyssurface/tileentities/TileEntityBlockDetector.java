package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityBlockDetector extends GanysInventory implements ISidedInventory {

	protected int coolDown = 20;

	public TileEntityBlockDetector() {
		this(Strings.BLOCK_DETECTOR_NAME);
	}

	protected TileEntityBlockDetector(String name) {
		super(1, name);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		coolDown--;
		if (coolDown <= 0) {
			coolDown = 20;
			updateStatus();
		}
	}

	protected void updateStatus() {
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if (checkNearbyBlocks()) {
			if (meta != 15)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 15, 3);
		} else if (meta != 0)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
	}

	protected boolean checkNearbyBlocks() {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if (checkBlock(dir))
				return true;
		return false;
	}

	public boolean checkBlock(ForgeDirection dir) {
		return checkBlock(inventory[0], dir);
	}

	private boolean checkBlock(ItemStack filter, ForgeDirection dir) {
		if (filter == null)
			return false;

		int x = xCoord + dir.offsetX;
		int y = yCoord + dir.offsetY;
		int z = zCoord + dir.offsetZ;

		Block block = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		ItemStack dropped = new ItemStack(block.getItemDropped(meta, worldObj.rand, 0), 1, block.getDamageValue(worldObj, x, y, z));

		if (filter.getItem() instanceof IPlantable)
			if (block == ((IPlantable) filter.getItem()).getPlant(worldObj, xCoord, yCoord, zCoord)) {
				coolDown = 0;
				int finalMeta = filter.getItem() != Items.nether_wart ? 7 : 3;
				return meta >= finalMeta;
			}

		if (InventoryUtils.areStacksTheSame(filter, dropped, false))
			return true;

		if (block == Block.getBlockFromItem(filter.getItem()) && meta == filter.getItemDamage())
			return true;

		if (filter.getItem() instanceof ItemBucket) {
			if (filter.getItem() == Items.lava_bucket)
				return worldObj.getBlock(x, y, z).getMaterial() == Material.lava;
			else if (filter.getItem() == Items.water_bucket)
				return worldObj.getBlock(x, y, z).getMaterial() == Material.water;
		} else if (filter.getItem() instanceof ItemSkull) {
			TileEntitySkull skull = Utils.getTileEntity(worldObj, x, y, z, TileEntitySkull.class);
			if (skull != null)
				return skull.func_145904_a() == filter.getItemDamage();
		} else if (filter.getItem() == Items.dye && filter.getItemDamage() == 3) {
			coolDown = 0;
			return block instanceof BlockCocoa && BlockCocoa.func_149987_c(meta) >= 2;
		}

		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
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