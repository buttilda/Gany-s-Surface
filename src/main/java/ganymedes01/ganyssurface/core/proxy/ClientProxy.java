package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.renderer.block.BlockChestRenderer;
import ganymedes01.ganyssurface.client.renderer.block.BlockColouredRedstoneRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockDoorRenderer;
import ganymedes01.ganyssurface.client.renderer.block.BlockDualWorkTableRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockLanternRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockPlanterRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockSlimeBlockRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockTrapdoorRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemBannerRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemIcyPickaxeRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPaintingRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPocketCritterRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemStorageCaseRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemWoodChestRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemBedRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemBoatRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemBrewingStandRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemCakeRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemCauldronRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemComparatorRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemFlowerPotRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemHopperRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemLeverRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemMinecartChestRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemMinecartCommandBlockRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemMinecartFurnaceRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemMinecartHopperRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemMinecartRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemMinecartTNTRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemRepeaterRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemSignRenderer;
import ganymedes01.ganyssurface.client.renderer.item.vanilla.ItemTorchRenderer;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityBannerRenderer;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityChestPropellantRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityPlanterRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWoodChestRenderer;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWoodSignRenderer;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWorkTableRender;
import ganymedes01.ganyssurface.core.handlers.KeyBindingHandler;
import ganymedes01.ganyssurface.core.handlers.RenderCapeHandler;
import ganymedes01.ganyssurface.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganyssurface.core.utils.VersionHelper;
import ganymedes01.ganyssurface.entities.EntityBatPoop;
import ganymedes01.ganyssurface.entities.EntityPoop;
import ganymedes01.ganyssurface.entities.EntityVillageFinder;
import ganymedes01.ganyssurface.tileentities.TileEntityBanner;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
import ganymedes01.ganyssurface.tileentities.TileEntityWoodChest;
import ganymedes01.ganyssurface.tileentities.TileEntityWoodSign;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
	}

	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemDisplay.class, new TileEntityItemDisplayRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWorkTable.class, new TileEntityWorkTableRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestPropellant.class, new TileEntityChestPropellantRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlanter.class, new TileEntityPlanterRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodChest.class, new TileEntityWoodChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodSign.class, new TileEntityWoodSignRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new TileEntityBannerRenderer());
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

		if (GanysSurface.enableChests)
			for (Block chest : ModBlocks.chests)
				MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(chest), ItemWoodChestRenderer.INSTANCE);

		if (GanysSurface.enable3DRendering) {
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.hopper), new ItemHopperRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.cauldron, new ItemCauldronRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.brewing_stand, new ItemBrewingStandRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.repeater, new ItemRepeaterRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.comparator, new ItemComparatorRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.minecart, new ItemMinecartRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.chest_minecart, new ItemMinecartChestRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.furnace_minecart, new ItemMinecartFurnaceRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.tnt_minecart, new ItemMinecartTNTRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.hopper_minecart, new ItemMinecartHopperRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.command_block_minecart, new ItemMinecartCommandBlockRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.flower_pot, new ItemFlowerPotRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.boat, new ItemBoatRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.bed, new ItemBedRenderer());
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.lever), new ItemLeverRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.cake, new ItemCakeRenderer());
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.torch), new ItemTorchRenderer());
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.redstone_torch), new ItemTorchRenderer());
			MinecraftForgeClient.registerItemRenderer(Items.sign, ItemSignRenderer.INSTANCE);
			if (GanysSurface.enableWoodenSigns)
				for (Block sign : ModBlocks.signs)
					MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(sign), ItemSignRenderer.INSTANCE);
		}

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.banner), new ItemBannerRenderer());
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

		if (GanysSurface.enableSlimeBlock)
			RenderingRegistry.registerBlockHandler(new BlockSlimeBlockRender());

		if (GanysSurface.enableColouredRedstone)
			RenderingRegistry.registerBlockHandler(new BlockColouredRedstoneRender());

		if (GanysSurface.enableItemDisplay)
			RenderingRegistry.registerBlockHandler(new BlockItemDisplayRender());

		if (GanysSurface.enableWorkTables)
			RenderingRegistry.registerBlockHandler(new BlockDualWorkTableRender());

		if (GanysSurface.enablePlanter)
			RenderingRegistry.registerBlockHandler(new BlockPlanterRender());

		if (GanysSurface.enableDoors)
			RenderingRegistry.registerBlockHandler(new BlockDoorRenderer());

		if (GanysSurface.enableWoodenTrapdoors)
			RenderingRegistry.registerBlockHandler(new BlockTrapdoorRenderer());

		RenderingRegistry.registerBlockHandler(new BlockChestRenderer());
	}
}