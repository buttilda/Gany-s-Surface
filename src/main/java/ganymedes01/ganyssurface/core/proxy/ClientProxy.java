package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.renderer.block.BlockChestRenderer;
import ganymedes01.ganyssurface.client.renderer.block.BlockColouredRedstoneRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockDoorRenderer;
import ganymedes01.ganyssurface.client.renderer.block.BlockDualWorkTableRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockLanternRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockPlanterRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockSlimeBlockRender;
import ganymedes01.ganyssurface.client.renderer.item.ItemIcyPickaxeRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPaintingRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPocketCritterRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemStorageCaseRenderer;
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
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityChestPropellantRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityPlanterRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWorkTableRender;
import ganymedes01.ganyssurface.core.handlers.ClientEventHandler;
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
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

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
		MinecraftForgeClient.registerItemRenderer(ModItems.storageCase, new ItemStorageCaseRenderer());
		MinecraftForgeClient.registerItemRenderer(ModItems.pocketCritter, new ItemPocketCritterRenderer());
		MinecraftForgeClient.registerItemRenderer(ModItems.roastedSquid, new ItemPocketCritterRenderer());
		MinecraftForgeClient.registerItemRenderer(ModItems.icyPickaxe, new ItemIcyPickaxeRenderer());
		MinecraftForgeClient.registerItemRenderer(ModItems.painting, new ItemPaintingRenderer());

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
		}

		RenderingRegistry.registerEntityRenderingHandler(EntityPoop.class, new RenderSnowball(ModItems.poop, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBatPoop.class, new RenderSnowball(ModItems.poop, 1));
		RenderingRegistry.registerEntityRenderingHandler(EntityVillageFinder.class, new RenderSnowball(ModItems.villageFinder));

		RenderingRegistry.registerBlockHandler(new BlockLanternRender());
		RenderingRegistry.registerBlockHandler(new BlockSlimeBlockRender());
		RenderingRegistry.registerBlockHandler(new BlockColouredRedstoneRender());
		RenderingRegistry.registerBlockHandler(new BlockItemDisplayRender());
		RenderingRegistry.registerBlockHandler(new BlockChestRenderer());
		RenderingRegistry.registerBlockHandler(new BlockDualWorkTableRender());
		RenderingRegistry.registerBlockHandler(new BlockPlanterRender());
		RenderingRegistry.registerBlockHandler(new BlockDoorRenderer());
	}
}