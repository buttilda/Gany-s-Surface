package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.renderer.block.BlockRendererHelper;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class ItemBrewingStandRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		Object renderer = data[0];
		if (renderer instanceof RenderBlocks)
			switch (type) {
				case ENTITY:
					OpenGLHelper.scale(0.5, 0.5, 0.5);
					render(stack, -0.5F, -0.5F, -0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					render(stack, 0.0F, 0.0F, 0.0F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					render(stack, 0.0F, 0.0F, 0.0F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					OpenGLHelper.scale(1.2, 1.2, 1.2);
					render(stack, -0.5F, -0.5F, -0.5F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	private void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		OpenGLHelper.translate(0.5, 0.5, 0.5);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		// CENTRAL POLE
		renderer.setRenderBounds(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.875D, 0.5625D);
		BlockRendererHelper.renderSimpleBlock(Blocks.brewing_stand, 0, renderer);

		// BASE
		renderer.setOverrideBlockTexture(((BlockBrewingStand) Blocks.brewing_stand).getIconBrewingStandBase());
		renderer.renderAllFaces = true;
		renderer.setRenderBounds(0.5625D, 0.0D, 0.3125D, 0.9375D, 0.125D, 0.6875D);
		BlockRendererHelper.renderSimpleBlock(Blocks.brewing_stand, 0, renderer);
		renderer.setRenderBounds(0.125D, 0.0D, 0.0625D, 0.5D, 0.125D, 0.4375D);
		BlockRendererHelper.renderSimpleBlock(Blocks.brewing_stand, 0, renderer);
		renderer.setRenderBounds(0.125D, 0.0D, 0.5625D, 0.5D, 0.125D, 0.9375D);
		BlockRendererHelper.renderSimpleBlock(Blocks.brewing_stand, 0, renderer);
		renderer.renderAllFaces = false;
		renderer.clearOverrideBlockTexture();

		// POTIONS
		OpenGLHelper.translate(-0.5, -0.5, -0.5);
		OpenGLHelper.disableLighting();
		IIcon iicon = renderer.getBlockIconFromSideAndMetadata(Blocks.brewing_stand, 0, 0);

		if (renderer.hasOverrideBlockTexture())
			iicon = renderer.overrideBlockTexture;

		double minV = iicon.getMinV();
		double maxV = iicon.getMaxV();
		int meta = stack.getItemDamage();

		for (int i = 0; i < 3; ++i) {
			double d1 = i * Math.PI * 2.0D / 3.0D + Math.PI / 2D;
			double d2 = iicon.getInterpolatedU(8.0D);
			double d3 = iicon.getMaxU();

			if ((meta & 1 << i) != 0)
				d3 = iicon.getMinU();

			double d4 = 0 + 0.5D;
			double d5 = 0 + 0.5D + Math.sin(d1) * 8.0D / 16.0D;
			double d6 = 0 + 0.5D;
			double d7 = 0 + 0.5D + Math.cos(d1) * 8.0D / 16.0D;
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(d4, 0 + 1, d6, d2, minV);
			tessellator.addVertexWithUV(d4, 0 + 0, d6, d2, maxV);
			tessellator.addVertexWithUV(d5, 0 + 0, d7, d3, maxV);
			tessellator.addVertexWithUV(d5, 0 + 1, d7, d3, minV);
			tessellator.addVertexWithUV(d5, 0 + 1, d7, d3, minV);
			tessellator.addVertexWithUV(d5, 0 + 0, d7, d3, maxV);
			tessellator.addVertexWithUV(d4, 0 + 0, d6, d2, maxV);
			tessellator.addVertexWithUV(d4, 0 + 1, d6, d2, minV);
			tessellator.draw();
		}

		OpenGLHelper.enableLighting();
		OpenGLHelper.popMatrix();
	}
}