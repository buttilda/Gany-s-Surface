package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketMarket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityMarket extends GanysInventory {

	private String owner = null;

	public static final int OFFER_ONE = 24;
	public static final int OFFER_TWO = 25;

	public static final int PRICE_ONE = 26;
	public static final int PRICE_TWO = 27;

	public TileEntityMarket() {
		super(28, Strings.MARKET_NAME);
	}

//	@Override
//	public ItemStack getStackInSlot(int slot) {
//		switch (slot) {
//			case OFFER_ONE:
//				return null;
//			case OFFER_TWO:
//				return null;
//			case PRICE_ONE:
//				return null;
//			case PRICE_TWO:
//				return null;
//			default:
//				return super.getStackInSlot(slot);
//		}
//	}
//
//	@Override
//	public void setInventorySlotContents(int slot, ItemStack stack) {
//		switch (slot) {
//			case OFFER_ONE:
//				break;
//			case OFFER_TWO:
//				break;
//			case PRICE_ONE:
//				break;
//			case PRICE_TWO:
//				break;
//			default:
//				super.setInventorySlotContents(slot, stack);
//				break;
//		}
//	}

	public boolean isOwner(String username) {
		if (owner == null || username == null)
			return false;
		else
			return username.compareTo(owner) == 0;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketTypeHandler.populatePacket(new PacketMarket(xCoord, yCoord, zCoord, owner));
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		owner = data.getString("owner");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setString("owner", owner);
	}
}