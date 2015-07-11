package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

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

	public static final Map<TileEntityMarket, Object> markets = new WeakHashMap<TileEntityMarket, Object>();

	private static final int PRODUCT_SLOT = 0;
	private static final int PURCHASE_SLOT = 13;

	private boolean isSleeping = false;
	private int tries = 0;
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
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		if (pkt.func_148853_f() == 0)
			readFromNBT(pkt.func_148857_g());
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
		isSleeping = false;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			// Complete purchases
			if (!isSleeping || worldObj.getWorldTime() % 60 == 0)
				if (checkPurchase()) {
					makePurchase();
					tries = 0;
					isSleeping = false;
				} else {
					tries++;
					// If all check attempts failed for the last minute enter sleep mode
					if (tries >= 20 * 60)
						isSleeping = true;
				}

			// Try to add buffer to Ender Chest
			addBufferToEnderChest();
		} else if (!markets.containsKey(this))
			markets.put(this, null);
	}

	private void addBufferToEnderChest() {
		if (!profits.isEmpty()) {
			IInventory boundInvt = getBoundInventory();
			if (boundInvt != null)
				for (ItemStack profit : profits)
					if (InventoryUtils.addStackToInventory(boundInvt, profit)) {
						profits.remove(profit);
						return;
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

	private boolean checkPurchase() {
		// If there's no product set...
		if (inventory[PRODUCT_SLOT] == null)
			return false;
		// If there's no stock...
		if (!hasStock())
			return false;
		// If there's already something in the output slot...
		if (inventory[PURCHASE_SLOT] != null)
			// If the item currently being sold is not the same as the one in the output...
			if (!InventoryUtils.areStacksSameOre(inventory[PURCHASE_SLOT], inventory[PRODUCT_SLOT]))
				return false;
			else {
				// If they are the same but the stack sizes are too big...
				int size = inventory[PURCHASE_SLOT].stackSize + inventory[PRODUCT_SLOT].stackSize;
				if (size > getInventoryStackLimit() || size > inventory[PURCHASE_SLOT].getMaxStackSize())
					return false;
			}

		// Check if the payment slots match the price slots
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

		// If they match and the price slots aren't all null...
		return matched && !allNull;
	}

	private void makePurchase() {
		// Add profits to buffer
		for (int i = 7; i < 13; i++) {
			ItemStack payment = inventory[i];
			if (payment != null) {
				payment.stackSize -= inventory[i - 6].stackSize;
				if (payment.stackSize <= 0)
					inventory[i] = null;
				profits.add(inventory[i - 6].copy());
			}
		}

		// Deduct item sold from ender chest
		int totalCount = 0;
		int targetCount = inventory[PRODUCT_SLOT].stackSize;
		IInventory boundInvt = getBoundInventory();
		if (boundInvt != null)
			for (int i = 0; i < boundInvt.getSizeInventory(); i++) {
				ItemStack invtStack = boundInvt.getStackInSlot(i);
				if (InventoryUtils.areStacksSameOre(invtStack, inventory[PRODUCT_SLOT])) {
					int oldCount = totalCount;
					totalCount = Math.min(invtStack.stackSize + totalCount, targetCount);
					if ((invtStack.stackSize -= totalCount - oldCount) <= 0)
						boundInvt.setInventorySlotContents(i, null);
					if (totalCount >= targetCount)
						break;
				}
			}

		// Add the sold item to the output slot
		if (inventory[PURCHASE_SLOT] == null)
			inventory[PURCHASE_SLOT] = inventory[PRODUCT_SLOT].copy();
		else
			inventory[PURCHASE_SLOT].stackSize += inventory[PRODUCT_SLOT].stackSize;
	}

	private boolean hasStock() {
		IInventory boundInvt = getBoundInventory();
		return boundInvt != null && InventoryUtils.inventoryContains(boundInvt, inventory[PRODUCT_SLOT]);
	}

	public boolean isItemPayment(ItemStack stack) {
		for (int i = 1; i < 7; i++) {
			ItemStack payment = inventory[i];
			if (InventoryUtils.areStacksSameOre(payment, stack))
				return true;
		}
		return false;
	}

	public boolean isItemForSale(ItemStack stack) {
		return InventoryUtils.areStacksSameOre(inventory[PRODUCT_SLOT], stack);
	}

	public ItemStack getProduct() {
		return inventory[PRODUCT_SLOT];
	}

	public ItemStack[] getPrice() {
		ItemStack[] price = new ItemStack[6];
		for (int i = 1; i < 7; i++)
			price[i - 1] = inventory[i];
		return price;
	}

	private IInventory getBoundInventory() {
		EntityPlayer player = worldObj.getPlayerEntityByName(owner);
		return player != null ? player.getInventoryEnderChest() : null;
	}
}