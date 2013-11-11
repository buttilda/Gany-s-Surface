package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.client.renderer.block.BlockLanternRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockSlimeBlockRender;
import ganymedes01.ganyssurface.client.renderer.item.ItemItemDisplayRenderer;
import ganymedes01.ganyssurface.client.renderer.item.ItemPlanterRenderer;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityChestPropellantRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityItemDisplayRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityPlanterRender;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWorkTableRender;
import ganymedes01.ganyssurface.entities.EntityBatPoop;
import ganymedes01.ganyssurface.entities.EntityPoop;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.ParticleEffectsID;
import ganymedes01.ganyssurface.lib.RenderIDs;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ClientProxy extends CommonProxy {

	public static int renderPass;

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
		MinecraftForgeClient.registerItemRenderer(ModBlocks.itemDisplay.blockID, new ItemItemDisplayRenderer());
		MinecraftForgeClient.registerItemRenderer(ModBlocks.planter.blockID, new ItemPlanterRenderer());

		RenderingRegistry.registerEntityRenderingHandler(EntityPoop.class, new RenderSnowball(ModItems.poop, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBatPoop.class, new RenderSnowball(ModItems.poop, 1));

		RenderingRegistry.registerBlockHandler(RenderIDs.LANTERN, new BlockLanternRender());
		RenderingRegistry.registerBlockHandler(RenderIDs.SLIME_BLOCK, new BlockSlimeBlockRender());
	}

	@Override
	public void handleItemDisplayPacket(int x, int y, int z, ItemStack stack) {
		World world = FMLClientHandler.instance().getClient().theWorld;
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity != null)
			if (tileEntity instanceof TileEntityItemDisplay)
				((TileEntityItemDisplay) tileEntity).addItemToDisplay(stack);
	}

	@Override
	public void handleWorkTablePacket(int x, int y, int z, ItemStack[] inventory) {
		World world = FMLClientHandler.instance().getClient().theWorld;
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity != null && inventory != null)
			if (tileEntity instanceof TileEntityWorkTable)
				for (int i = 0; i < inventory.length; i++)
					((TileEntityWorkTable) tileEntity).addToCraftMatrix(i, inventory[i]);
	}

	@Override
	public void handlePlanterPacket(int x, int y, int z, float armExtension) {
		World world = FMLClientHandler.instance().getClient().theWorld;
		if (world != null) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if (tileEntity != null)
				if (tileEntity instanceof TileEntityPlanter)
					((TileEntityPlanter) tileEntity).setArmExtension(armExtension);
		}
	}

	@Override
	public void handleMarketPacket(int x, int y, int z, String owner, ItemStack[] inventory) {
		World world = FMLClientHandler.instance().getClient().theWorld;
		if (world != null) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if (tileEntity != null)
				if (tileEntity instanceof TileEntityMarket) {
					for (int i = 0; i < inventory.length; i++)
						((TileEntityMarket) tileEntity).setInventorySlotContents(i, inventory[i]);
					((TileEntityMarket) tileEntity).setOwner(owner);
				}
		}
	}

	@Override
	public void handleParticleEffects(World world, double x, double y, double z, int id) {
		switch (id) {
			case ParticleEffectsID.POOP:
				Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBreakingFX(world, x, y, z, ModItems.poop, 0));
				break;
			case ParticleEffectsID.BAT_POOP:
				Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBreakingFX(world, x, y, z, ModItems.poop, 1));
				break;
		}
	}
}