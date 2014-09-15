package ganymedes01.ganyssurface.tileentities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityWorkTable extends GanysInventory implements ISidedInventory {

	public final WorkTableCrafting craftMatrix = new WorkTableCrafting(this);
	@SideOnly(Side.CLIENT)
	private EntityItem entityItem;

	public TileEntityWorkTable() {
		this(9);
	}

	public TileEntityWorkTable(int size) {
		super(size, null);
	}

	@SideOnly(Side.CLIENT)
	public EntityItem getEntityItem() {
		if (entityItem == null) {
			entityItem = new EntityItem(worldObj);
			entityItem.hoverStart = 0;
		}
		return entityItem;
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
	public void markDirty() {
		if (worldObj != null) {
			blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
		}
	}

	public static class WorkTableCrafting extends InventoryCrafting {

		protected final TileEntityWorkTable tile;
		protected final int offset;
		protected Container container;

		public WorkTableCrafting(TileEntityWorkTable tile) {
			this(tile, 0);
		}

		public WorkTableCrafting(TileEntityWorkTable tile, int offset) {
			super(null, 0, 0);
			this.tile = tile;
			this.offset = offset;
		}

		public void setContainer(Container container) {
			this.container = container;
		}

		private void onCraftingChanged() {
			container.onCraftMatrixChanged(this);
		}

		@Override
		public int getSizeInventory() {
			return 9;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return tile.getStackInSlot(slot + offset);
		}

		@Override
		public ItemStack getStackInRowAndColumn(int row, int column) {
			if (row >= 0 && row < 3) {
				int k = row + column * 3;
				return getStackInSlot(k);
			} else
				return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			return tile.getStackInSlotOnClosing(slot + offset);
		}

		@Override
		public ItemStack decrStackSize(int slot, int size) {
			ItemStack stack = tile.decrStackSize(slot + offset, size);
			onCraftingChanged();
			return stack;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			tile.setInventorySlotContents(slot + offset, stack);
			onCraftingChanged();
		}
	}
}