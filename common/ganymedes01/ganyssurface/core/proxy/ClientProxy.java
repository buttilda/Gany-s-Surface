package ganymedes01.ganyssurface.core.proxy;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.client.renderer.block.BlockColouredRedstoneRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockLanternRender;
import ganymedes01.ganyssurface.client.renderer.block.BlockSlimeBlockRender;
import ganymedes01.ganyssurface.client.renderer.item.ItemDualWorkTableRenderer;
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

import java.util.ArrayList;

import net.minecraft.block.BlockColored;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.passive.EntitySheep;
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
		MinecraftForgeClient.registerItemRenderer(ModBlocks.dualWorkTable.blockID, new ItemDualWorkTableRenderer());

		RenderingRegistry.registerEntityRenderingHandler(EntityPoop.class, new RenderSnowball(ModItems.poop, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBatPoop.class, new RenderSnowball(ModItems.poop, 1));

		RenderingRegistry.registerBlockHandler(RenderIDs.LANTERN, new BlockLanternRender());
		RenderingRegistry.registerBlockHandler(RenderIDs.SLIME_BLOCK, new BlockSlimeBlockRender());
		RenderingRegistry.registerBlockHandler(RenderIDs.COLOURED_REDSTONE, new BlockColouredRedstoneRender());
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
	public void handleMarketPacket(int x, int y, int z, String owner, ItemStack[] inventory, ArrayList<ItemStack> extraInventory) {
		World world = FMLClientHandler.instance().getClient().theWorld;
		if (world != null) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if (tileEntity != null)
				if (tileEntity instanceof TileEntityMarket) {
					for (int i = 0; i < inventory.length; i++)
						((TileEntityMarket) tileEntity).setInventorySlotContents(i, inventory[i]);
					((TileEntityMarket) tileEntity).setOwner(owner);
					((TileEntityMarket) tileEntity).setExtraInventory(extraInventory);
				}
		}
	}

	@Override
	public void handleParticleEffects(World world, double x, double y, double z, int id, int meta) {
		switch (id) {
			case ParticleEffectsID.POOP:
				world.spawnParticle("iconcrack_" + ModItems.poop.itemID + "_" + meta, x, y, z, (Math.random() * 2.0D - 1.0D) * 0.2D, (Math.random() * 2.0D - 1.0D) * 0.2D, (Math.random() * 2.0D - 1.0D) * 0.2D);
				break;
			case ParticleEffectsID.COLOURED_REDSTONE:
				EntityReddustFX fx = getParticle(world, x, y, z, meta);
				if (fx != null)
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				break;
		}
	}

	private EntityReddustFX getParticle(World world, double x, double y, double z, int colourIndex) {
		int meta = world.getBlockMetadata((int) x, (int) y, (int) z);
		if (meta > 0) {
			double d0 = x + 0.5D + (world.rand.nextFloat() - 0.5D) * 0.2D;
			double d1 = y + 0.0625F;
			double d2 = z + 0.5D + (world.rand.nextFloat() - 0.5D) * 0.2D;
			float f = meta / 15.0F;
			float f1 = f * 0.6F + 0.4F;
			float f2 = f * f * 0.7F - 0.5F;
			float f3 = f * f * 0.6F - 0.7F;

			if (f2 < 0.0F)
				f2 = 0.0F;

			if (f3 < 0.0F)
				f3 = 0.0F;

			EntityReddustFX fx = new EntityReddustFX(world, d0, d1, d2, f1, f2, f3);
			float[] colour = EntitySheep.fleeceColorTable[BlockColored.getBlockFromDye(colourIndex)];
			fx.setRBGColorF(colour[0], colour[1], colour[2]);

			return fx;
		}
		return null;
	}
}