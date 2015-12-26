package ganymedes01.ganyssurface.tileentities;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.IPacketHandlingTile;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.CustomPacket;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity.TileData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityItemDisplay extends GanysInventory implements IPacketHandlingTile {

	@SideOnly(Side.CLIENT)
	private EntityItem displayItem;

	public TileEntityItemDisplay() {
		super(1, Strings.ITEM_DISPLAY_NAME);
	}

	public ItemStack getDisplayItem() {
		return inventory[0];
	}

	@SideOnly(Side.CLIENT)
	public EntityItem getItemForRendering() {
		if (displayItem == null) {
			displayItem = new EntityItem(worldObj);
			displayItem.hoverStart = 0.0F;
		}
		displayItem.setEntityItemStack(inventory[0]);
		return displayItem;
	}

	public void addItemToDisplay(ItemStack item) {
		if (item != null) {
			inventory[0] = item.copy();
			inventory[0].stackSize = 1;
		} else
			inventory[0] = null;

		markDirty();
	}

	@Override
	public void markDirty() {
		super.markDirty();
		PacketHandler.sendToAll(getPacket());
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
	public boolean canUpdate() {
		return false;
	}

	public CustomPacket getPacket() {
		return new PacketTileEntity(this, new TileData() {

			@Override
			public void writeData(ByteBuf buffer) {
				ByteBufUtils.writeItemStack(buffer, inventory[0]);
			}
		});
	}

	@Override
	public void readPacketData(ByteBuf buffer) {
		inventory[0] = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return false;
	}
}