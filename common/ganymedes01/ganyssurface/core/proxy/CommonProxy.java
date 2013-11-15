package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.client.gui.inventory.GuiBlockDetector;
import ganymedes01.ganyssurface.client.gui.inventory.GuiDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiInkHarvester;
import ganymedes01.ganyssurface.client.gui.inventory.GuiMarketPrivate;
import ganymedes01.ganyssurface.client.gui.inventory.GuiMarketPublic;
import ganymedes01.ganyssurface.client.gui.inventory.GuiOrganicMatterCompressor;
import ganymedes01.ganyssurface.client.gui.inventory.GuiPlanter;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.entities.EntityBatPoop;
import ganymedes01.ganyssurface.entities.EntityPoop;
import ganymedes01.ganyssurface.inventory.ContainerBlockDetector;
import ganymedes01.ganyssurface.inventory.ContainerDualWorkTable;
import ganymedes01.ganyssurface.inventory.ContainerInkHarvester;
import ganymedes01.ganyssurface.inventory.ContainerMarketPrivate;
import ganymedes01.ganyssurface.inventory.ContainerMarketPublic;
import ganymedes01.ganyssurface.inventory.ContainerOrganicMatterCompressor;
import ganymedes01.ganyssurface.inventory.ContainerPlanter;
import ganymedes01.ganyssurface.inventory.ContainerWorkTable;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityBlockDetector;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import ganymedes01.ganyssurface.tileentities.TileEntityCubicSensoringDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
import ganymedes01.ganyssurface.tileentities.TileEntityInkHarvester;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import ganymedes01.ganyssurface.tileentities.TileEntityOrganicMatterCompressor;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
import ganymedes01.ganyssurface.tileentities.TileEntityRainDetector;
import ganymedes01.ganyssurface.tileentities.TileEntitySensoringDislocator;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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
		GameRegistry.registerTileEntity(TileEntityMarket.class, Utils.getUnlocalizedName(Strings.MARKET_NAME));
		GameRegistry.registerTileEntity(TileEntityDualWorkTable.class, Utils.getUnlocalizedName(Strings.DUAL_WORK_TABLE_NAME));
	}

	public void registerEntities() {
		EntityRegistry.registerGlobalEntityID(EntityPoop.class, Utils.getUnlocalizedName("EntityPoop"), EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityPoop.class, Utils.getUnlocalizedName("EntityPoop"), ModIDs.ENTITY_POOP_ID, GanysSurface.instance, 64, 1, true);

		EntityRegistry.registerGlobalEntityID(EntityBatPoop.class, Utils.getUnlocalizedName("EntityBatPoop"), EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityBatPoop.class, Utils.getUnlocalizedName("EntityBatPoop"), ModIDs.ENTITY_BAT_POOP_ID, GanysSurface.instance, 64, 1, true);
	}

	public void registerRenderers() {

	}

	public void handleItemDisplayPacket(int x, int y, int z, ItemStack stack) {

	}

	public void handleWorkTablePacket(int x, int y, int z, ItemStack[] inventory) {

	}

	public void handlePlanterPacket(int x, int y, int z, float armExtension) {

	}

	public void handleParticleEffects(World world, double x, double y, double z, int id, int meta) {

	}

	public void handleMarketPacket(int x, int y, int z, String owner, ItemStack[] inventory) {

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
			case GUIsID.PLANTER:
				TileEntityPlanter tilePlanter = (TileEntityPlanter) tile;
				return new ContainerPlanter(player.inventory, tilePlanter);
			case GUIsID.INK_HARVESTER:
				TileEntityInkHarvester tileHarvester = (TileEntityInkHarvester) tile;
				return new ContainerInkHarvester(player.inventory, tileHarvester);
			case GUIsID.MARKET_PUBLIC:
				TileEntityMarket tileMarketPublic = (TileEntityMarket) tile;
				return new ContainerMarketPublic(player.inventory, tileMarketPublic, player.username);
			case GUIsID.MARKET_PRIVATE:
				TileEntityMarket tileMarketPrivate = (TileEntityMarket) tile;
				return new ContainerMarketPrivate(player.inventory, tileMarketPrivate);
			case GUIsID.DUAL_WORK_TABLE:
				TileEntityDualWorkTable tileDualWorkTable = (TileEntityDualWorkTable) tile;
				return new ContainerDualWorkTable(player.inventory, tileDualWorkTable);
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
			case GUIsID.PLANTER:
				TileEntityPlanter tilePlanter = (TileEntityPlanter) tile;
				return new GuiPlanter(player.inventory, tilePlanter);
			case GUIsID.INK_HARVESTER:
				TileEntityInkHarvester tileHarvester = (TileEntityInkHarvester) tile;
				return new GuiInkHarvester(player.inventory, tileHarvester);
			case GUIsID.MARKET_PUBLIC:
				TileEntityMarket tileMarketPublic = (TileEntityMarket) tile;
				return new GuiMarketPublic(player.inventory, tileMarketPublic, player.username);
			case GUIsID.MARKET_PRIVATE:
				TileEntityMarket tileMarketPrivate = (TileEntityMarket) tile;
				return new GuiMarketPrivate(player.inventory, tileMarketPrivate);
			case GUIsID.DUAL_WORK_TABLE:
				TileEntityDualWorkTable tileDualWorkTable = (TileEntityDualWorkTable) tile;
				return new GuiDualWorkTable(player.inventory, tileDualWorkTable);
		}
		return null;
	}
}