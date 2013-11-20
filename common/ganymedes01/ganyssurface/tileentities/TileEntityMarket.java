package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.ItemStackMap;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.PacketTypeHandler;
import ganymedes01.ganyssurface.network.packet.PacketMarket;
import ganymedes01.ganyssurface.recipes.MarketSales;

import java.util.ArrayList;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityMarket extends GanysInventory implements ISidedInventory, IPipeConnection {

	private String owner = null;
	private ArrayList<ItemStack> extraInventory = new ArrayList<ItemStack>();

	public static final int OFFER_ONE = 24;
	public static final int OFFER_TWO = 25;

	public static final int PRICE_ONE = 26;
	public static final int PRICE_TWO = 27;

	public static final int PAYMENT_ONE = 28;
	public static final int PAYMENT_TWO = 29;

	public static final int RETRIEVE_ONE = 30;
	public static final int RETRIEVE_TWO = 31;

	public TileEntityMarket() {
		super(32, Strings.MARKET_NAME);
	}

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
		MarketSales.addMarket(this);
	}

	public void setExtraInventory(ArrayList<ItemStack> extraInventory) {
		this.extraInventory = extraInventory;
	}

	public ArrayList<ItemStack> getExtraInventory() {
		return extraInventory;
	}

	private int getOfferQuantity(int offer) {
		if (inventory[offer] == null)
			return 0;

		int count = 0;
		for (int i = 0; i < 12; i++)
			if (ItemStackMap.areItemsEqual(inventory[i], inventory[offer]))
				count += inventory[i].stackSize;
		return count / inventory[offer].stackSize;
	}

	public int getQuantityOfferONE() {
		return getOfferQuantity(OFFER_ONE);
	}

	public int getQuantityOfferTWO() {
		return getOfferQuantity(OFFER_TWO);
	}

	public void makePayment(ItemStack stack) {
		for (int i = 12; i < 24; i++)
			if (inventory[i] == null) {
				inventory[i] = stack;
				return;
			} else if (ItemStackMap.areItemsEqual(inventory[i], stack))
				if (inventory[i].stackSize + stack.stackSize <= inventory[i].getMaxStackSize()) {
					inventory[i].stackSize += stack.stackSize;
					stack = null;
					return;
				} else {
					inventory[i].stackSize += stack.stackSize;
					stack.stackSize = inventory[i].stackSize - inventory[i].getMaxStackSize();
					inventory[i].stackSize = inventory[i].getMaxStackSize();
					continue;
				}

		if (stack != null && stack.stackSize > 0)
			extraInventory.add(stack);
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();

		for (int i = 0; i < extraInventory.size(); i++)
			for (int j = 12; j < 24; j++)
				if (inventory[j] == null) {
					inventory[j] = extraInventory.get(i).copy();
					extraInventory.remove(i);
				}
	}

	@Override
	public Packet getDescriptionPacket() {
		ItemStack[] stacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < stacks.length; i++)
			stacks[i] = inventory[i];

		return PacketTypeHandler.populatePacket(new PacketMarket(xCoord, yCoord, zCoord, owner, stacks, extraInventory));
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		owner = data.getString("owner");

		NBTTagList tagList = data.getTagList("ExtraItems");
		extraInventory = new ArrayList<ItemStack>();
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < inventory.length)
				extraInventory.add(ItemStack.loadItemStackFromNBT(tagCompound));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setString("owner", owner);

		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < extraInventory.size(); i++)
			if (extraInventory.get(i) != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				extraInventory.get(i).writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		data.setTag("ExtraItems", tagList);
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

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return ConnectOverride.DISCONNECT;
	}
}