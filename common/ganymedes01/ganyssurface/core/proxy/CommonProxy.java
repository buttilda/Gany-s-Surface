package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.client.gui.inventory.GuiBlockDetector;
import ganymedes01.ganyssurface.client.gui.inventory.GuiOrganicMatterCompressor;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerBlockDetector;
import ganymedes01.ganyssurface.inventory.ContainerOrganicMatterCompressor;
import ganymedes01.ganyssurface.inventory.ContainerVanillaBrewingStand;
import ganymedes01.ganyssurface.inventory.ContainerVanillaChest;
import ganymedes01.ganyssurface.inventory.ContainerVanillaDispenser;
import ganymedes01.ganyssurface.inventory.ContainerVanillaFurnace;
import ganymedes01.ganyssurface.inventory.ContainerVanillaHopper;
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
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class CommonProxy implements IGuiHandler {

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityRainDetector.class, Utils.getUnlocalizedName(Strings.RAIN_DETECTOR_NAME));
		GameRegistry.registerTileEntity(TileEntityBlockDetector.class, Utils.getUnlocalizedName(Strings.BLOCK_DETECTOR_NAME));
		GameRegistry.registerTileEntity(TileEntityDislocator.class, Utils.getUnlocalizedName(Strings.DISLOCATOR_NAME));
		GameRegistry.registerTileEntity(TileEntitySensoringDislocator.class, Utils.getUnlocalizedName(Strings.SENSORING_DISLOCATOR_NAME));
		GameRegistry.registerTileEntity(TileEntityCubicSensoringDislocator.class, Utils.getUnlocalizedName(Strings.CUBIC_SENSORING_DISLOCATOR_NAME));
		GameRegistry.registerTileEntity(TileEntityWorkTable.class, Utils.getUnlocalizedName(Strings.WORK_TABLE_NAME));
		GameRegistry.registerTileEntity(TileEntityOrganicMatterCompressor.class, Utils.getUnlocalizedName(Strings.ORGANIC_MATTER_COMPRESSOR_NAME));
		GameRegistry.registerTileEntity(TileEntityItemDisplay.class, Utils.getUnlocalizedName(Strings.ITEM_DISPLAY_NAME));
		GameRegistry.registerTileEntity(TileEntityChestPropellant.class, Utils.getUnlocalizedName(Strings.CHEST_PROPELLANT_NAME));
	}

	public void registerRenderers() {

	}

	public void handleTileWithSingleDisplayItemPacket(int x, int y, int z, int itemID, int meta, int stackSize) {

	}

	public void handleTileWithMultipleDisplayItemsPacket(int x, int y, int z, int[] itemID, int[] meta, int[] stackSize, int invSize) {

	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		switch (ID) {
			case GUIsID.BLOCK_DETECTOR:
				TileEntityBlockDetector tileBlockDetector = (TileEntityBlockDetector) tile;
				return new ContainerBlockDetector(player.inventory, tileBlockDetector);
			case GUIsID.WORK_TABLE:
				TileEntityWorkTable tileWorkTable = (TileEntityWorkTable) tile;
				return new ContainerWorkTable(player.inventory, tileWorkTable);
			case GUIsID.ORGANIC_MATTER_COMPRESSOR:
				TileEntityOrganicMatterCompressor tileCompressor = (TileEntityOrganicMatterCompressor) tile;
				return new ContainerOrganicMatterCompressor(player.inventory, tileCompressor);
			case GUIsID.VANILLA_CHEST:
				TileEntityChest tileChest = (TileEntityChest) tile;
				return new ContainerVanillaChest(player.inventory, tileChest);
			case GUIsID.VANILLA_FURNACE:
				TileEntityFurnace tileFurnace = (TileEntityFurnace) tile;
				return new ContainerVanillaFurnace(player.inventory, tileFurnace);
			case GUIsID.VANILLA_BREW_STAND:
				TileEntityBrewingStand tileStand = (TileEntityBrewingStand) tile;
				return new ContainerVanillaBrewingStand(player.inventory, tileStand);
			case GUIsID.VANILLA_HOPPER:
				TileEntityHopper tileHopper = (TileEntityHopper) tile;
				return new ContainerVanillaHopper(player.inventory, tileHopper);
			case GUIsID.VANILLA_DISPENSER:
			case GUIsID.VANILLA_DROPPER:
				TileEntityDispenser tileDispenser = (TileEntityDispenser) tile;
				return new ContainerVanillaDispenser(player.inventory, tileDispenser);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		switch (ID) {
			case GUIsID.BLOCK_DETECTOR:
				TileEntityBlockDetector tileBlockDetector = (TileEntityBlockDetector) tile;
				return new GuiBlockDetector(player.inventory, tileBlockDetector);
			case GUIsID.WORK_TABLE:
				TileEntityWorkTable tileWorkTable = (TileEntityWorkTable) tile;
				return new GuiWorkTable(player.inventory, tileWorkTable);
			case GUIsID.ORGANIC_MATTER_COMPRESSOR:
				TileEntityOrganicMatterCompressor tileCompressor = (TileEntityOrganicMatterCompressor) tile;
				return new GuiOrganicMatterCompressor(player.inventory, tileCompressor);
			case GUIsID.VANILLA_CHEST:
				TileEntityChest tileChest = (TileEntityChest) tile;
				return new GuiChest(player.inventory, tileChest);
			case GUIsID.VANILLA_FURNACE:
				TileEntityFurnace tileFurnace = (TileEntityFurnace) tile;
				return new GuiFurnace(player.inventory, tileFurnace);
			case GUIsID.VANILLA_BREW_STAND:
				TileEntityBrewingStand tileStand = (TileEntityBrewingStand) tile;
				return new GuiBrewingStand(player.inventory, tileStand);
			case GUIsID.VANILLA_HOPPER:
				TileEntityHopper tileHopper = (TileEntityHopper) tile;
				return new GuiHopper(player.inventory, tileHopper);
			case GUIsID.VANILLA_DISPENSER:
			case GUIsID.VANILLA_DROPPER:
				TileEntityDispenser tileDispenser = (TileEntityDispenser) tile;
				return new GuiDispenser(player.inventory, tileDispenser);
		}
		return null;
	}
}
