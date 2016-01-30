package ganymedes01.ganyssurface.tileentities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

	private final int[] multiBlocks = new int[] { 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1 };

	private final int[] multiBlocks2 = new int[] { 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 20, 1, 1, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 20, 9, 9, 9, 20, 20, 9, 9, 9, 20, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 1, 1, 20, 20, 20, 1, 1, 1, 1, 1, 1 };

	private int coolDown = 300, strikeCount = 0, foodCoolDown = 300, coolDownModifier = -1;

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

		foodCoolDown--;
		if (foodCoolDown <= 0)
			if (worldObj.rand.nextInt(6) == 3) {
				if (!consumeFoodItem()) {
					strikeCount++;
					return;
				}
			} else
				foodCoolDown = 300;

		if (coolDownModifier >= 0)
			coolDownModifier = -1;
		coolDown += coolDownModifier;
		if (coolDown <= worldObj.rand.nextInt(10)) {
			if (isFormed())
				makeInkSac();
			coolDown = 300;
		}
	}

	private void makeInkSac() {
		for (int i = 0; i < 9; i++)
			if (inventory[i] == null) {
				inventory[i] = new ItemStack(Items.dye);
				return;
			} else if (inventory[i].getItem() == Items.dye && inventory[i].stackSize < inventory[i].getMaxStackSize()) {
				inventory[i].stackSize++;
				return;
			}
	}

	private void killOneSquid() {
		List<Entity> list = getSquids();
		if (!list.isEmpty())
			((EntitySquid) list.iterator().next()).attackEntityFrom(DamageSource.starve, 11.0F);

		isFormed();
	}

	private boolean consumeFoodItem() {
		int saturationModifier = 300;
		try {
			for (int i = 9; i < 13; i++) {
				if (inventory[i] == null)
					continue;
				Item item = inventory[i].getItem();
				if (item instanceof ItemFood) {
					ItemFood food = (ItemFood) item;
					saturationModifier = (int) (5000 * food.func_150906_h(inventory[i]));
					coolDownModifier = -(int) (10 * food.func_150906_h(inventory[i]));
				}
				inventory[i].splitStack(1);
				if (inventory[i].stackSize <= 0)
					inventory[i] = null;
				return true;
			}
			coolDownModifier = -1;
			return false;
		} finally {
			foodCoolDown = saturationModifier;
		}
	}

	private boolean hasSquids() {
		List<Entity> list = getSquids();
		if (!list.isEmpty()) {
			Iterator<Entity> iterator = list.iterator();
			while (iterator.hasNext()) {
				EntitySquid squid = (EntitySquid) iterator.next();
				if (!squid.isNoDespawnRequired())
					squid.func_110163_bv();
			}
		}

		return list.size() >= 2;
	}

	@SuppressWarnings("unchecked")
	private List<Entity> getSquids() {
		int minY, maxY;
		if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.water) {
			minY = -3;
			maxY = 0;
		} else {
			minY = 0;
			maxY = 3;
		}

		return worldObj.selectEntitiesWithinAABB(EntitySquid.class, AxisAlignedBB.getBoundingBox(xCoord - 2.0D, yCoord + minY, zCoord - 2.0D, xCoord + 2.0D, yCoord + maxY, zCoord + 2.0D), IEntitySelector.selectAnything);
	}

	public boolean isFormed() {
		boolean formed = checkMultiBlock() && hasSquids();
		if (formed)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
		else
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
		return formed;
	}

	private ArrayList<Block> getMultiBlockList() {
		ArrayList<Block> list = new ArrayList<Block>();

		int minY, maxY;
		if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.water) {
			minY = -3;
			maxY = 0;
		} else if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) == Blocks.water) {
			minY = 0;
			maxY = 3;
		} else
			return list;

		for (int i = -2; i <= 2; i++)
			for (int j = minY; j <= maxY; j++)
				for (int k = -2; k <= 2; k++)
					if (!(i == 0 && j == 0 && k == 0)) {
						int x = xCoord + i;
						int y = yCoord + j;
						int z = zCoord + k;
						list.add(worldObj.getBlock(x, y, z));
					}
		return list;
	}

	private boolean checkMultiBlock() {
		ArrayList<Block> list = getMultiBlockList();
		if (list.isEmpty())
			return false;

		int[] checkList;

		if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.water)
			checkList = multiBlocks;
		else
			checkList = multiBlocks2;

		for (int i = 0; i < checkList.length; i++) {
			Block block = list.get(i);
			switch (checkList[i]) {
				case 1: // Outline
					if (!isOutline(block))
						return false;
					break;
				case 9: // Water
					if (!isWater(block))
						return false;
					break;
				case 20: // Walls
					if (!isWall(block))
						return false;
					break;
			}
		}
		return true;
	}

	private boolean isOutline(Block block) {
		Material mat = block.getMaterial();
		return mat == Material.iron || mat == Material.rock;
	}

	private boolean isWall(Block block) {
		return isOutline(block) || block.getMaterial() == Material.glass;
	}

	private boolean isWater(Block block) {
		return block == Blocks.water;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		coolDown = data.getInteger("coolDown");
		strikeCount = data.getInteger("strikeCount");
		foodCoolDown = data.getInteger("foodCoolDown");
		coolDownModifier = data.getInteger("coolDownModifier");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("coolDown", coolDown);
		data.setInteger("strikeCount", strikeCount);
		data.setInteger("foodCoolDown", foodCoolDown);
		data.setInteger("coolDownModifier", coolDownModifier);
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