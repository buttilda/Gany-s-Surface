package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.client.gui.inventory.GuiAutoEncaser;
import ganymedes01.ganyssurface.client.gui.inventory.GuiBlockDetector;
import ganymedes01.ganyssurface.client.gui.inventory.GuiDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiEncasingBench;
import ganymedes01.ganyssurface.client.gui.inventory.GuiFarmManager;
import ganymedes01.ganyssurface.client.gui.inventory.GuiGearalyser;
import ganymedes01.ganyssurface.client.gui.inventory.GuiInkHarvester;
import ganymedes01.ganyssurface.client.gui.inventory.GuiOrganicMatterCompressor;
import ganymedes01.ganyssurface.client.gui.inventory.GuiPlanter;
import ganymedes01.ganyssurface.client.gui.inventory.GuiPortableDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.configuration.ConfigurationHandler;
import ganymedes01.ganyssurface.core.handlers.EntityDropEvent;
import ganymedes01.ganyssurface.core.handlers.OpenContainerHandler;
import ganymedes01.ganyssurface.core.handlers.PoopHandler;
import ganymedes01.ganyssurface.core.handlers.SnowTickHandler;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.entities.EntityBatPoop;
import ganymedes01.ganyssurface.entities.EntityPoop;
import ganymedes01.ganyssurface.entities.EntityVillageFinder;
import ganymedes01.ganyssurface.inventory.ContainerAutoEncaser;
import ganymedes01.ganyssurface.inventory.ContainerBlockDetector;
import ganymedes01.ganyssurface.inventory.ContainerDualWorkTable;
import ganymedes01.ganyssurface.inventory.ContainerEncasingBench;
import ganymedes01.ganyssurface.inventory.ContainerFarmManager;
import ganymedes01.ganyssurface.inventory.ContainerGearalyser;
import ganymedes01.ganyssurface.inventory.ContainerInkHarvester;
import ganymedes01.ganyssurface.inventory.ContainerOrganicMatterCompressor;
import ganymedes01.ganyssurface.inventory.ContainerPlanter;
import ganymedes01.ganyssurface.inventory.ContainerPortableDualWorkTable;
import ganymedes01.ganyssurface.inventory.ContainerWorkTable;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityAutoEncaser;
import ganymedes01.ganyssurface.tileentities.TileEntityBlockDetector;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import ganymedes01.ganyssurface.tileentities.TileEntityCubicSensoringDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
import ganymedes01.ganyssurface.tileentities.TileEntityFarmManager;
import ganymedes01.ganyssurface.tileentities.TileEntityInkHarvester;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import ganymedes01.ganyssurface.tileentities.TileEntityOrganicMatterCompressor;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
import ganymedes01.ganyssurface.tileentities.TileEntityRainDetector;
import ganymedes01.ganyssurface.tileentities.TileEntitySensoringDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class CommonProxy implements IGuiHandler {

	public void registerEvents() {
		FMLCommonHandler.instance().bus().register(ConfigurationHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(new SnowTickHandler());
		MinecraftForge.EVENT_BUS.register(new OpenContainerHandler());
		if (GanysSurface.enableMutton)
			MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
		if (GanysSurface.mobsShouldPoop)
			MinecraftForge.EVENT_BUS.register(new PoopHandler());
	}

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
		GameRegistry.registerTileEntity(TileEntityPlanter.class, Utils.getUnlocalizedName(Strings.PLANTER_NAME));
		GameRegistry.registerTileEntity(TileEntityInkHarvester.class, Utils.getUnlocalizedName(Strings.INK_HARVESTER_NAME));
		GameRegistry.registerTileEntity(TileEntityDualWorkTable.class, Utils.getUnlocalizedName(Strings.DUAL_WORK_TABLE_NAME));
		GameRegistry.registerTileEntity(TileEntityFarmManager.class, Utils.getUnlocalizedName(Strings.FARM_MANAGER_NAME));
		GameRegistry.registerTileEntity(TileEntityAutoEncaser.class, Utils.getUnlocalizedName(Strings.AUTO_ENCASER_NAME));
	}

	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityPoop.class, Utils.getUnlocalizedName(Strings.ENTITY_POOP_NAME), ModIDs.ENTITY_POOP_ID, GanysSurface.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityBatPoop.class, Utils.getUnlocalizedName(Strings.ENTITY_BAT_POOP_NAME), ModIDs.ENTITY_BAT_POOP_ID, GanysSurface.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityVillageFinder.class, Utils.getUnlocalizedName(Strings.VILLAGE_FINDER), ModIDs.ENTITY_VILLAGE_FINDER_ID, GanysSurface.instance, 64, 1, true);
	}

	public void registerRenderers() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (ID) {
			case GUIsID.BLOCK_DETECTOR:
				return new ContainerBlockDetector(player.inventory, (TileEntityBlockDetector) tile);
			case GUIsID.WORK_TABLE:
				return new ContainerWorkTable(player.inventory, (TileEntityWorkTable) tile);
			case GUIsID.ORGANIC_MATTER_COMPRESSOR:
				return new ContainerOrganicMatterCompressor(player.inventory, (TileEntityOrganicMatterCompressor) tile);
			case GUIsID.PLANTER:
				return new ContainerPlanter(player.inventory, (TileEntityPlanter) tile);
			case GUIsID.INK_HARVESTER:
				return new ContainerInkHarvester(player.inventory, (TileEntityInkHarvester) tile);
			case GUIsID.DUAL_WORK_TABLE:
				return new ContainerDualWorkTable(player.inventory, (TileEntityDualWorkTable) tile);
			case GUIsID.FARM_MANAGER:
				return new ContainerFarmManager(player.inventory, (TileEntityFarmManager) tile);
			case GUIsID.PORTABLE_DUAL_WORK_TABLE:
				return new ContainerPortableDualWorkTable(player, x);
			case GUIsID.ENCASING_BENCH:
				return new ContainerEncasingBench(player.inventory);
			case GUIsID.AUTO_ENCASER:
				return new ContainerAutoEncaser(player.inventory, (TileEntityAutoEncaser) tile);
			case GUIsID.GEARALYSER:
				return new ContainerGearalyser(player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (ID) {
			case GUIsID.BLOCK_DETECTOR:
				return new GuiBlockDetector(player.inventory, (TileEntityBlockDetector) tile);
			case GUIsID.WORK_TABLE:
				return new GuiWorkTable(player.inventory, (TileEntityWorkTable) tile);
			case GUIsID.ORGANIC_MATTER_COMPRESSOR:
				return new GuiOrganicMatterCompressor(player.inventory, (TileEntityOrganicMatterCompressor) tile);
			case GUIsID.PLANTER:
				return new GuiPlanter(player.inventory, (TileEntityPlanter) tile);
			case GUIsID.INK_HARVESTER:
				return new GuiInkHarvester(player.inventory, (TileEntityInkHarvester) tile);
			case GUIsID.DUAL_WORK_TABLE:
				return new GuiDualWorkTable(player.inventory, (TileEntityDualWorkTable) tile);
			case GUIsID.FARM_MANAGER:
				return new GuiFarmManager(player.inventory, (TileEntityFarmManager) tile);
			case GUIsID.PORTABLE_DUAL_WORK_TABLE:
				return new GuiPortableDualWorkTable(player, x);
			case GUIsID.ENCASING_BENCH:
				return new GuiEncasingBench(player.inventory);
			case GUIsID.AUTO_ENCASER:
				return new GuiAutoEncaser(player.inventory, (TileEntityAutoEncaser) tile);
			case GUIsID.GEARALYSER:
				return new GuiGearalyser(player.inventory);
		}
		return null;
	}
}