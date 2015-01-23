package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.Spiral;
import ganymedes01.ganyssurface.lib.Strings;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

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
		for (int i = 1; i < 5; i++) {
			if (worldObj.isAirBlock(xCoord + p.x, yCoord - i, zCoord + p.y))
				continue;

			if (worldObj.isAirBlock(xCoord + p.x, yCoord - i + 1, zCoord + p.y)) {
				List<Integer> slots = new LinkedList<Integer>();
				for (int j = 0; j < inventory.length; j++)
					if (inventory[j] != null)
						slots.add(j);
				if (!slots.isEmpty()) {
					int slot = slots.get(worldObj.rand.nextInt(slots.size()));
					if (inventory[slot].getItem() instanceof IPlantable) {
						IPlantable seed = (IPlantable) inventory[slot].getItem();
						Block soil = worldObj.getBlock(xCoord + p.x, yCoord - i, zCoord + p.y);
						if (soil.canSustainPlant(worldObj, xCoord + p.x, yCoord - i, zCoord + p.y, ForgeDirection.UP, seed)) {
							Block crop = seed.getPlant(worldObj, xCoord, yCoord, zCoord);
							worldObj.setBlock(xCoord + p.x, yCoord - i + 1, zCoord + p.y, crop);
							worldObj.playSoundEffect(xCoord + p.x + 0.5F, yCoord - i + 1, zCoord + p.y + 0.5F, crop.stepSound.getBreakSound(), (crop.stepSound.getVolume() + 1.0F) / 2.0F, crop.stepSound.getPitch() * 0.8F);
							if (--inventory[slot].stackSize <= 0)
								inventory[slot] = null;
							break;
						}
					}
				}
				break;
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