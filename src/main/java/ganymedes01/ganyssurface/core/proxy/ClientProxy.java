package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.renderer.block.BlockColouredRedstoneRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockDualWorkTableRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockLanternRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockPlanterRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockTrapdoorRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemIcyPickaxeRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPaintingRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPocketCritterRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemStorageCaseRenderer;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityChestPropellantRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityPlanterRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWoodSignRenderer;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWorkTableRender;
import ganymedes01.ganyssurface.core.handlers.KeyBindingHandler;
import ganymedes01.ganyssurface.core.handlers.RenderCapeHandler;
import ganymedes01.ganyssurface.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganyssurface.core.utils.VersionHelper;
import ganymedes01.ganyssurface.entities.EntityBatPoop;
import ganymedes01.ganyssurface.entities.EntityPoop;
import ganymedes01.ganyssurface.entities.EntityVillageFinder;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
import ganymedes01.ganyssurface.tileentities.TileEntityWoodSign;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEvents() {
		super.registerEvents();

		if (GanysSurface.shouldDoVersionCheck) {
			VersionHelper.execute();
			MinecraftForge.EVENT_BUS.register(new VersionCheckTickHandler());
		}

		MinecraftForge.EVENT_BUS.register(new KeyBindingHandler());

		if (!Loader.isModLoaded("ganysend") && !Loader.isModLoaded("ganysnether"))
			MinecraftForge.EVENT_BUS.register(new RenderCapeHandler());
	}

	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemDisplay.class, new TileEntityItemDisplayRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWorkTable.class, new TileEntityWorkTableRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestPropellant.class, new TileEntityChestPropellantRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlanter.class, new TileEntityPlanterRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodSign.class, new TileEntityWoodSignRenderer());
	}

	@Override
	public void registerRenderers() {
		registerItemRenderers();
		registerEntityRenderers();
		registerBlockRenderers();
	}

	private void registerItemRenderers() {
		if (GanysSurface.enableEncasers)
			MinecraftForgeClient.registerItemRenderer(ModItems.storageCase, new ItemStorageCaseRenderer());

		if (GanysSurface.enablePocketCritters) {
			MinecraftForgeClient.registerItemRenderer(ModItems.pocketCritter, new ItemPocketCritterRenderer());
			MinecraftForgeClient.registerItemRenderer(ModItems.roastedSquid, new ItemPocketCritterRenderer());
		}

		if (GanysSurface.enableIcyPick)
			MinecraftForgeClient.registerItemRenderer(ModItems.icyPickaxe, new ItemIcyPickaxeRenderer());

		if (GanysSurface.enablePaintings)
			MinecraftForgeClient.registerItemRenderer(ModItems.painting, new ItemPaintingRenderer());

		if (GanysSurface.enableEatenCake)
			MinecraftForgeClient.registerItemRenderer(ModItems.eatenCake, new ItemCakeRenderer(Blocks.cake));

		if (GanysSurface.enableChocolate)
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.chocolateCake), new ItemCakeRenderer(ModBlocks.chocolateCake));
	}

	private void registerEntityRenderers() {
		if (GanysSurface.enablePoop) {
			RenderingRegistry.registerEntityRenderingHandler(EntityPoop.class, new RenderSnowball(ModItems.poop, 0));
			RenderingRegistry.registerEntityRenderingHandler(EntityBatPoop.class, new RenderSnowball(ModItems.poop, 1));
		}

		if (GanysSurface.enableVillageFinder)
			RenderingRegistry.registerEntityRenderingHandler(EntityVillageFinder.class, new RenderSnowball(ModItems.villageFinder));
	}

	private void registerBlockRenderers() {
		if (GanysSurface.enableLantern)
			RenderingRegistry.registerBlockHandler(new BlockLanternRender());

		if (GanysSurface.enableColouredRedstone)
			RenderingRegistry.registerBlockHandler(new BlockColouredRedstoneRender());

		if (GanysSurface.enableItemDisplay)
			RenderingRegistry.registerBlockHandler(new BlockItemDisplayRender());

		if (GanysSurface.enableWorkTables)
			RenderingRegistry.registerBlockHandler(new BlockDualWorkTableRender());

		if (GanysSurface.enablePlanter)
			RenderingRegistry.registerBlockHandler(new BlockPlanterRender());

		if (GanysSurface.enableWoodenTrapdoors)
			RenderingRegistry.registerBlockHandler(new BlockTrapdoorRenderer());
	}
}