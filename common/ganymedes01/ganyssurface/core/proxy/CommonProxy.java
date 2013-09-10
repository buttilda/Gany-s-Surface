package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.client.gui.inventory.GuiBlockDetector;
import ganymedes01.ganyssurface.client.gui.inventory.GuiOrganicMatterCompressor;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.inventory.ContainerBlockDetector;
import ganymedes01.ganyssurface.inventory.ContainerOrganicMatterCompressor;
import ganymedes01.ganyssurface.inventory.ContainerWorkTable;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityBlockDetector;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import ganymedes01.ganyssurface.tileentities.TileEntityCubicSensoringDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import ganymedes01.ganyssurface.tileentities.TileEntityOrganicMatterCompressor;
import ganymedes01.ganyssurface.tileentities.TileEntityRainDetector;
import ganymedes01.ganyssurface.tileentities.TileEntitySensoringDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler {

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityRainDetector.class, Strings.RAIN_DETECTOR_NAME);
		GameRegistry.registerTileEntity(TileEntityBlockDetector.class, Strings.BLOCK_DETECTOR_NAME);
		GameRegistry.registerTileEntity(TileEntityDislocator.class, Strings.DISLOCATOR_NAME);
		GameRegistry.registerTileEntity(TileEntitySensoringDislocator.class, Strings.SENSORING_DISLOCATOR_NAME);
		GameRegistry.registerTileEntity(TileEntityCubicSensoringDislocator.class, Strings.CUBIC_SENSORING_DISLOCATOR_NAME);
		GameRegistry.registerTileEntity(TileEntityWorkTable.class, Strings.WORK_TABLE_NAME);
		GameRegistry.registerTileEntity(TileEntityOrganicMatterCompressor.class, Strings.ORGANIC_MATTER_COMPRESSOR_NAME);
		GameRegistry.registerTileEntity(TileEntityItemDisplay.class, Strings.ITEM_DISPLAY_NAME);
		GameRegistry.registerTileEntity(TileEntityChestPropellant.class, Strings.CHEST_PROPELLANT_NAME);
	}

	public void registerRenderers() {

	}

	public void handleTileWithSingleDisplayItemPacket(int x, int y, int z, int itemID, int meta, int stackSize) {

	}

	public void handleTileWithMultipleDisplayItemsPacket(int x, int y, int z, int[] itemID, int[] meta, int[] stackSize, int invSize) {

	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUIsID.BLOCK_DETECTOR) {
			TileEntityBlockDetector tileBlockDetector = (TileEntityBlockDetector) world.getBlockTileEntity(x, y, z);
			return new ContainerBlockDetector(player.inventory, tileBlockDetector);
		} else if (ID == GUIsID.WORK_TABLE) {
			TileEntityWorkTable tileWorkTable = (TileEntityWorkTable) world.getBlockTileEntity(x, y, z);
			return new ContainerWorkTable(player.inventory, tileWorkTable);
		} else if (ID == GUIsID.ORGANIC_MATTER_COMPRESSOR) {
			TileEntityOrganicMatterCompressor tileCompressor = (TileEntityOrganicMatterCompressor) world.getBlockTileEntity(x, y, z);
			return new ContainerOrganicMatterCompressor(player.inventory, tileCompressor);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUIsID.BLOCK_DETECTOR) {
			TileEntityBlockDetector tileBlockDetector = (TileEntityBlockDetector) world.getBlockTileEntity(x, y, z);
			return new GuiBlockDetector(player.inventory, tileBlockDetector);
		} else if (ID == GUIsID.WORK_TABLE) {
			TileEntityWorkTable tileWorkTable = (TileEntityWorkTable) world.getBlockTileEntity(x, y, z);
			return new GuiWorkTable(player.inventory, tileWorkTable);
		} else if (ID == GUIsID.ORGANIC_MATTER_COMPRESSOR) {
			TileEntityOrganicMatterCompressor tileCompressor = (TileEntityOrganicMatterCompressor) world.getBlockTileEntity(x, y, z);
			return new GuiOrganicMatterCompressor(player.inventory, tileCompressor);
		}
		return null;
	}
}
