package ganymedes01.ganyssurface.tileentities;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import ganymedes01.ganyssurface.core.utils.Spiral;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IPlantable;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityFarmManager extends GanysInventory {

	private static final List<Point> spiral = new Spiral(9, 9).spiral();
	private int index = 0;
	public boolean redstoneActive;

	public TileEntityFarmManager() {
		super(18, Strings.FARM_MANAGER_NAME);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (redstoneActive) {
			index = 0;
			return;
		}

		Point p = getNextPoint();
		for (int i = 1; i < 5; i++)
			if (worldObj.isAirBlock(xCoord + p.x, yCoord - i + 1, zCoord + p.y)) {
				List<Integer> slots = new LinkedList<Integer>();
				for (int j = 0; j < inventory.length; j++)
					if (inventory[j] != null && inventory[j].stackSize > 1)
						slots.add(j);
				if (!slots.isEmpty()) {
					int slot = slots.get(worldObj.rand.nextInt(slots.size()));
					Block plantable = ((IPlantable) inventory[slot].getItem()).getPlant(worldObj, xCoord + p.x, yCoord - i, zCoord + p.y);
					if (plantable != null &&
							worldObj.getBlock(xCoord + p.x, yCoord - i - 1, zCoord + p.y).isFertile(worldObj,xCoord + p.x, yCoord - i - 1, zCoord + p.y)) {
						if (worldObj.setBlock(xCoord + p.x, yCoord - i, zCoord + p.y, plantable)) {
							inventory[slot].stackSize--;
							if (inventory[slot].stackSize <= 0)
								inventory[slot] = null;
							break;
						}
					}
				}
			}
	}

	private Point getNextPoint() {
		index++;
		if (index >= spiral.size())
			index = 0;

		return spiral.get(index);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null ? stack.getItem() instanceof IPlantable : false;
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("index", index);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		index = data.getInteger("index");
	}
}