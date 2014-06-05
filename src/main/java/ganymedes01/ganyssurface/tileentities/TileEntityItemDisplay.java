package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.IPacketHandlingTile;
import ganymedes01.ganyssurface.network.packet.CustomPacket;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity;
import ganymedes01.ganyssurface.network.packet.PacketTileEntity.TileData;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;
import cpw.mods.fml.common.network.ByteBufUtils;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityItemDisplay extends GanysInventory implements IPipeConnection, IPacketHandlingTile {

	public TileEntityItemDisplay() {
		super(1, Strings.ITEM_DISPLAY_NAME);
	}

	public ItemStack getDisplayItem() {
		return inventory[0];
	}

	public void addItemToDisplay(ItemStack item) {
		if (item != null) {
			inventory[0] = item.copy();
			inventory[0].stackSize = 1;
		} else
			inventory[0] = null;
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
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		return ConnectOverride.DISCONNECT;
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
}