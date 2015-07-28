package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.Spiral;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

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
					if (inventory[j] != null)
						slots.add(j);
				if (!slots.isEmpty()) {
					int slot = slots.get(worldObj.rand.nextInt(slots.size()));
					if (inventory[slot].tryPlaceItemIntoWorld(Utils.getPlayer(worldObj), worldObj, xCoord + p.x, yCoord - i, zCoord + p.y, 1, 0, 0, 0)) {
						if (inventory[slot].stackSize <= 0)
							inventory[slot] = null;
						break;
					}
				}
				break;
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