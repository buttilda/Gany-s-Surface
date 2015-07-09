package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityMarket extends GanysInventory implements ISidedInventory {

	private static final int PRODUCT_SLOT = 0;
	private static final int PURCHASE_SLOT = 13;

	private String owner;
	private final List<ItemStack> profits = new ArrayList<ItemStack>();

	public TileEntityMarket() {
		super(14, Strings.MARKET);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 7, 8, 9, 10, 11, 12, 13 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 13;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot >= 7 && slot <= 12) {
			int filterSlot = slot - 6;
			ItemStack filter = getStackInSlot(filterSlot);
			return InventoryUtils.areStacksSameOre(filter, stack);
		}
		return false;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("owner", owner);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		if (pkt.func_148853_f() == 0) {
			NBTTagCompound nbt = pkt.func_148857_g();
			owner = nbt.getString("owner");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("owner", owner);

		NBTTagList tags = new NBTTagList();
		for (int i = 0; i < profits.size(); i++) {
			NBTTagCompound data = new NBTTagCompound();
			tags.appendTag(profits.get(i).writeToNBT(data));
		}
		nbt.setTag("profits", tags);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		owner = nbt.getString("owner");

		NBTTagList tags = nbt.getTagList("profits", 10);
		profits.clear();
		for (int i = 0; i < tags.tagCount(); i++)
			profits.add(ItemStack.loadItemStackFromNBT(tags.getCompoundTagAt(i)));
	}

	@Override
	public void markDirty() {
		super.markDirty();
		checkPurchase();
	}

	@Override
	public void updateEntity() {
		if (!profits.isEmpty()) {
			EntityPlayer player = worldObj.getPlayerEntityByName(owner);
			if (player != null) {
				IInventory enderChest = player.getInventoryEnderChest();
				for (ItemStack profit : profits)
					if (InventoryUtils.addStackToInventory(enderChest, profit)) {
						profits.remove(profit);
						return;
					}
			}
		}
	}

	public void setOwner(EntityPlayer player) {
		owner = player.getCommandSenderName();
		markDirty();
	}

	public String getOwner() {
		return owner;
	}

	public void checkPurchase() {
		if (inventory[PRODUCT_SLOT] == null)
			return;

		boolean matched = true;
		boolean allNull = true;
		for (int i = 1; i < 7; i++) {
			ItemStack payment = inventory[i + 6];
			ItemStack price = inventory[i];
			if (price == null && payment == null)
				continue;
			if (price == null != (payment == null)) {
				matched = false;
				break;
			}
			if (price != null || payment != null)
				allNull = false;

			if (price != null && payment != null)
				if (price.stackSize > payment.stackSize || !InventoryUtils.areStacksSameOre(price, payment)) {
					matched = false;
					break;
				}
		}
		if (matched && !allNull)
			inventory[PURCHASE_SLOT] = inventory[PRODUCT_SLOT].copy();
		else
			inventory[PURCHASE_SLOT] = null;
	}

	public void makePurchase() {
		for (int i = 7; i < 13; i++) {
			ItemStack payment = inventory[i];
			if (payment != null) {
				payment.stackSize -= inventory[i - 6].stackSize;
				if (payment.stackSize <= 0)
					inventory[i] = null;
				profits.add(inventory[i - 6].copy());
			}
		}
	}
}