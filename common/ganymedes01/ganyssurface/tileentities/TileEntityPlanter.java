package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketPlanter;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityPlanter extends GanysInventory {

	private float armExtension = 0.0F;
	private boolean isReturning;

	public TileEntityPlanter() {
		super(9, Strings.PLANTER_NAME);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (isReturning) {
			armExtension -= 0.01F;
			if (armExtension <= 0.0F) {
				armExtension = 0.0F;
				isReturning = false;
			}
			update();
		}
		if (!isReturning)
			if (worldObj.isAirBlock(xCoord, yCoord - 1, zCoord))
				if (!worldObj.isAirBlock(xCoord, yCoord - 2, zCoord))
					for (int i = 0; i < inventory.length; i++) {
						if (inventory[i] == null)
							continue;
						if (inventory[i].getItem() instanceof IPlantable) {
							IPlantable seed = (IPlantable) inventory[i].getItem();
							Block soil = Block.blocksList[worldObj.getBlockId(xCoord, yCoord - 2, zCoord)];
							if (soil.canSustainPlant(worldObj, xCoord, yCoord - 2, zCoord, ForgeDirection.UP, seed)) {
								armExtension += 0.01F;
								if (armExtension >= 0.5F) {
									worldObj.setBlock(xCoord, yCoord - 1, zCoord, seed.getPlantID(worldObj, xCoord, yCoord, zCoord));
									inventory[i].stackSize--;
									if (inventory[i].stackSize <= 0)
										inventory[i] = null;
									isReturning = true;
								}
								update();
							}
						}
					}
				else
					isReturning = true;
	}

	public float getArmExtension() {
		return armExtension;
	}

	public void setArmExtension(float value) {
		armExtension = value;
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketTypeHandler.populatePacket(new PacketPlanter(xCoord, yCoord, zCoord, armExtension));
	}

	private void update() {
		GanysSurface.proxy.handlePlanterPacket(xCoord, yCoord, zCoord, armExtension);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null ? stack.getItem() instanceof IPlantable : false;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		armExtension = data.getFloat("armExtension");
		isReturning = data.getBoolean("isReturning");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setFloat("armExtension", armExtension);
		data.setBoolean("isReturning", isReturning);
	}
}