package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketPlanter;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityPlanter extends TileEntity implements IInventory {

	private ItemStack[] inventory = new ItemStack[9];
	private float armExtension = 0.0F;
	private boolean isReturning;

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
						if (inventory[i].getItem() instanceof ItemSeeds) {
							ItemSeeds seed = (ItemSeeds) inventory[i].getItem();
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
							} else if (armExtension != 0.0F)
								armExtension = 0.0F;
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
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (inventory[slot] != null) {
			ItemStack itemstack;
			if (inventory[slot].stackSize <= size) {
				itemstack = inventory[slot];
				inventory[slot] = null;
				return itemstack;
			} else {
				itemstack = inventory[slot].splitStack(size);
				if (inventory[slot].stackSize == 0)
					inventory[slot] = null;
				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (inventory[slot] != null) {
			ItemStack itemstack = inventory[slot];
			inventory[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInvName() {
		return Utils.getConainerName(Strings.PLANTER_NAME);
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null ? stack.getItem() instanceof ItemSeeds : false;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		NBTTagList tagList = data.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < inventory.length)
				inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < inventory.length; i++)
			if (inventory[i] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		data.setTag("Items", tagList);
	}
}