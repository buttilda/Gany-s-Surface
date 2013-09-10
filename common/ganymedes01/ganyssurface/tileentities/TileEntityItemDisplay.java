package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketTileWithISingleDisplayItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class TileEntityItemDisplay extends TileEntity {

	private ItemStack itemDisplay;

	public ItemStack getDisplayItem() {
		return itemDisplay;
	}

	public void addItemToDisplay(ItemStack item) {
		if (item != null) {
			itemDisplay = item.copy();
			itemDisplay.stackSize = 1;
		} else
			itemDisplay = null;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		NBTTagList tagList = data.getTagList("Items");
		if (tagList.tagCount() > 0) {
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(0);
			byte slot = tagCompound.getByte("Slot");
			itemDisplay = ItemStack.loadItemStackFromNBT(tagCompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		NBTTagList tagList = new NBTTagList();
		if (itemDisplay != null) {
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setByte("Slot", (byte) 0);
			itemDisplay.writeToNBT(tagCompound);
			tagList.appendTag(tagCompound);
		}
		data.setTag("Items", tagList);
	}

	@Override
	public Packet getDescriptionPacket() {
		if (itemDisplay != null && itemDisplay.stackSize > 0)
			return PacketTypeHandler.populatePacket(new PacketTileWithISingleDisplayItem(xCoord, yCoord, zCoord, itemDisplay.itemID, itemDisplay.getItemDamage(), itemDisplay.stackSize));
		else
			return super.getDescriptionPacket();
	}
}
