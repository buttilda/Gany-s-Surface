package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.renderer.block.BlockColouredRedstoneRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockLanternRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockSlimeBlockRender;
import ganymedes01.ganyssurface.client.renderer.item.ItemDualWorkTableRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPlanterRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPocketCritterRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemStorageCaseRenderer;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityChestPropellantRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityPlanterRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWorkTableRender;
import ganymedes01.ganyssurface.core.handlers.KeyBindingHandler;
import ganymedes01.ganyssurface.core.handlers.RenderCapeHandler;
import ganymedes01.ganyssurface.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.core.utils.VersionHelper;
import ganymedes01.ganyssurface.entities.EntityBatPoop;
import ganymedes01.ganyssurface.entities.EntityPoop;
import ganymedes01.ganyssurface.entities.EntityVillageFinder;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;

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
			FMLCommonHandler.instance().bus().register(new VersionCheckTickHandler());
		}

		FMLCommonHandler.instance().bus().register(new KeyBindingHandler());

		if (!Loader.isModLoaded("ganysend") && !Loader.isModLoaded("ganysnether"))
			MinecraftForge.EVENT_BUS.register(new RenderCapeHandler());

		if (GanysSurface.enableSpongeTexture)
			Blocks.sponge.setBlockTextureName(Utils.getBlockTexture("sponge"));
	}

	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemDisplay.class, new TileEntityItemDisplayRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWorkTable.class, new TileEntityWorkTableRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestPropellant.class, new TileEntityChestPropellantRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlanter.class, new TileEntityPlanterRender());
	}

	@Override
	public void registerRenderers() {
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.planter), new ItemPlanterRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.dualWorkTable), new ItemDualWorkTableRenderer());
		MinecraftForgeClient.registerItemRenderer(ModItems.storageCase, new ItemStorageCaseRenderer());
		MinecraftForgeClient.registerItemRenderer(ModItems.pocketCritter, new ItemPocketCritterRenderer());
		MinecraftForgeClient.registerItemRenderer(ModItems.roastedSquid, new ItemPocketCritterRenderer());

		RenderingRegistry.registerEntityRenderingHandler(EntityPoop.class, new RenderSnowball(ModItems.poop, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBatPoop.class, new RenderSnowball(ModItems.poop, 1));
		RenderingRegistry.registerEntityRenderingHandler(EntityVillageFinder.class, new RenderSnowball(ModItems.villageFinder));

		RenderingRegistry.registerBlockHandler(new BlockLanternRender());
		RenderingRegistry.registerBlockHandler(new BlockSlimeBlockRender());
		RenderingRegistry.registerBlockHandler(new BlockColouredRedstoneRender());
		RenderingRegistry.registerBlockHandler(new BlockItemDisplayRender());
	}
}