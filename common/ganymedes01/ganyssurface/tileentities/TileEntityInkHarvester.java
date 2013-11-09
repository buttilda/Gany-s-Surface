package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityInkHarvester extends GanysInventory implements ISidedInventory {

	private final int[] multiBlocks = new int[] { 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9,
	20, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1 };

	private final int[] multiBlocks2 = new int[] { 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 20, 1, 1, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9,
	9, 20, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1 };

	private int coolDown = 300;
	private int strikeCount = 0;

	public TileEntityInkHarvester() {
		super(13, Strings.INK_HARVESTER_NAME);
	}

	@Override
	public void updateEntity() {
		if (worldObj == null || worldObj.isRemote)
			return;

		if (GanysSurface.inkHarvesterMaxStrike > 0 && strikeCount > GanysSurface.inkHarvesterMaxStrike) {
			killOneSquid();
			strikeCount = 0;
			return;
		}

		coolDown--;
		if (coolDown <= worldObj.rand.nextInt(10)) {
			if (isFormed())
				if (worldObj.rand.nextInt(8) == 4)
					if (consumeFoodItem())
						Utils.addStackToInventory(this, new ItemStack(Item.dyePowder));
					else
						strikeCount++;
			coolDown = 300;
		}
	}

	private void killOneSquid() {
		List list = getSquids();
		if (!list.isEmpty())
			((EntitySquid) list.iterator().next()).attackEntityFrom(DamageSource.starve, 11.0F);

		isFormed();
	}

	private boolean consumeFoodItem() {
		for (int i = 9; i < 13; i++) {
			if (inventory[i] == null)
				continue;
			inventory[i].splitStack(1);
			if (inventory[i].stackSize <= 0)
				inventory[i] = null;
			return true;
		}
		return false;
	}

	private boolean hasSquids() {
		List list = getSquids();
		if (!list.isEmpty()) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				EntitySquid squid = (EntitySquid) iterator.next();
				if (!squid.isNoDespawnRequired())
					squid.func_110163_bv();
			}
		}

		return list.size() >= 2;
	}

	private List getSquids() {
		int minY, maxY;
		if (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.waterStill.blockID) {
			minY = -3;
			maxY = 0;
		} else {
			minY = 0;
			maxY = 3;
		}

		return worldObj.selectEntitiesWithinAABB(EntitySquid.class, AxisAlignedBB.getAABBPool().getAABB(xCoord - 2.0D, yCoord + minY, zCoord - 2.0D, xCoord + 2.0D, yCoord + maxY, zCoord + 2.0D), IEntitySelector.selectAnything);
	}

	public boolean isFormed() {
		boolean formed = checkMultiBlock() && hasSquids();
		if (formed)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
		else
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
		return formed;
	}

	private ArrayList<Integer> getMultiBlockList() {
		ArrayList<Integer> list = new ArrayList<Integer>();

		int minY, maxY;
		if (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.waterStill.blockID) {
			minY = -3;
			maxY = 0;
		} else if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == Block.waterStill.blockID) {
			minY = 0;
			maxY = 3;
		} else
			return list;

		boolean flag = false;
		for (int i = -2; i <= 2; i++)
			for (int j = minY; j <= maxY; j++)
				for (int k = -2; k <= 2; k++)
					if (!(i == 0 && j == 0 && k == 0)) {
						int x = xCoord + i;
						int y = yCoord + j;
						int z = zCoord + k;
						list.add(worldObj.getBlockId(x, y, z));
					}
		return list;
	}

	private boolean checkMultiBlock() {
		ArrayList<Integer> list = getMultiBlockList();
		if (list.isEmpty())
			return false;

		int[] checkList;

		if (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.waterStill.blockID)
			checkList = multiBlocks;
		else
			checkList = multiBlocks2;

		for (int i = 0; i < checkList.length; i++) {
			int blockID = list.get(i);
			if (blockID >= Block.blocksList.length || blockID <= 0)
				return false;

			switch (checkList[i]) {
				case 1: // Outline
					if (!isOutline(Block.blocksList[blockID]))
						return false;
					break;
				case 9: // Water
					if (!isWater(Block.blocksList[blockID]))
						return false;
					break;
				case 20: // Walls
					if (!isWall(Block.blocksList[blockID]))
						return false;
					break;
			}
		}
		return true;
	}

	private boolean isOutline(Block block) {
		Material mat = block.blockMaterial;
		return mat == Material.iron || mat == Material.rock;
	}

	private boolean isWall(Block block) {
		return isOutline(block) || block.blockMaterial == Material.glass;
	}

	private boolean isWater(Block block) {
		return block == Block.waterStill;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		data.setInteger("coolDown", coolDown);
		data.setInteger("strikeCount", strikeCount);
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		coolDown = data.getInteger("coolDown");
		strikeCount = data.getInteger("strikeCount");
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot > 8 ? stack != null && stack.getItem() instanceof ItemFood : false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot < 9;
	}
}