package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.IPacketHandlingTile;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.CustomPacket;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity.TileData;
import ganymedes01.ganyssurface.recipes.MarketSales;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityMarket extends GanysInventory implements ISidedInventory, IPacketHandlingTile {

	private String owner = null;

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
		return owner != null && username != null && username.equals(owner);
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
		MarketSales.addMarket(this);

		PacketHandler.INSTANCE.sendToAll(new PacketTileEntity(this, new TileData() {

			@Override
			public void writeData(ByteBuf buffer) {
				buffer.writeByte((byte) 0);
				ByteBufUtils.writeUTF8String(buffer, owner);
			}
		}));
	}

	private int getOfferQuantity(int offer) {
		if (inventory[offer] == null)
			return 0;

		int count = 0;
		for (int i = 0; i < 12; i++)
			if (InventoryUtils.areStacksTheSame(inventory[i], inventory[offer], false))
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
			} else if (InventoryUtils.areStacksTheSame(inventory[i], stack, false))
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
	public boolean canUpdate() {
		return false;
	}

	@Override
	public void readPacketData(ByteBuf buffer) {
		switch (buffer.readByte()) {
			case 0:
				owner = ByteBufUtils.readUTF8String(buffer);
				break;
			case 1:
				owner = ByteBufUtils.readUTF8String(buffer);
				for (int i = 0; i < getSizeInventory(); i++)
					inventory[i] = ByteBufUtils.readItemStack(buffer);
				break;
		}
	}

	public CustomPacket getPacket() {
		return new PacketTileEntity(this, new TileData() {

			@Override
			public void writeData(ByteBuf buffer) {
				buffer.writeByte((byte) 1);
				ByteBufUtils.writeUTF8String(buffer, owner);
				for (int i = 0; i < TileEntityMarket.this.getSizeInventory(); i++)
					ByteBufUtils.writeItemStack(buffer, inventory[i]);
			}
		});
	}
}