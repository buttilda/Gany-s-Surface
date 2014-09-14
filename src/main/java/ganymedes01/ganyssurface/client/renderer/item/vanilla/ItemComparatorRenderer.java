package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.renderer.block.BlockRendererHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

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
public class ItemComparatorRenderer extends ItemRepeaterRenderer {

	@Override
	protected void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		Tessellator tessellator = Tessellator.instance;

		GL11.glDisable(GL11.GL_LIGHTING);
		ItemTorchRenderer.renderTorch(Blocks.unpowered_comparator, 0.1875, -0.1875, 0.25, renderer, 0.2);
		ItemTorchRenderer.renderTorch(Blocks.unpowered_comparator, -0.1875, -0.1875, 0.25, renderer, 0.2);
		ItemTorchRenderer.renderTorch(Blocks.unpowered_comparator, 0, -0.375, -0.3125, renderer, 0.4);
		GL11.glEnable(GL11.GL_LIGHTING);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0, -1, 0);
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderFaceYNeg(Blocks.unpowered_comparator, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(Blocks.stone_slab, 0, 0));
		tessellator.draw();

		GL11.glTranslated(0.5, 0.5, 0.5);
		renderer.setRenderBounds(0, 0.001, 0, 1, 0.125D, 1);
		BlockRendererHelper.renderSimpleBlock(Blocks.unpowered_comparator, 0, renderer);

		GL11.glPopMatrix();
	}
}