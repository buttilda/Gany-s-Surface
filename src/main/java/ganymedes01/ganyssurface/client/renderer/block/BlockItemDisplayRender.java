package ganymedes01.ganyssurface.client.renderer.block;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class BlockItemDisplayRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);
		float x = 0.0F;
		float y = 0.0F;
		float z = 0.0F;

		IIcon icon = block.getIcon(0, 0);
		float pixel = 1.0F / 16.0F;

		// INSIDE

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, x, y + 1.0F, z, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y - 1.0F, z, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, x, y, z + 1.0F, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, x, y, z - 1.0F, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x - 1.0F, y, z, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x + 1.0F, y, z, icon);
		tessellator.draw();

		// OUTSIDE

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y, z, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYNeg(block, x, y, z, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZPos(block, x, y, z, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZNeg(block, x, y, z, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x, y, z, icon);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x, y, z, icon);
		tessellator.draw();

		// BOTTOM

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y - 1.0F + pixel, z, Blocks.carpet.getIcon(0, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYNeg(block, x, y + pixel, z, Blocks.carpet.getIcon(0, metadata));
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		int colour = block.colorMultiplier(world, x, y, z);
		float r = (colour >> 16 & 255) / 255.0F;
		float g = (colour >> 8 & 255) / 255.0F;
		float b = (colour & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable) {
			r = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			g = (r * 30.0F + g * 70.0F) / 100.0F;
			b = (r * 30.0F + b * 70.0F) / 100.0F;
		}

		tessellator.setColorOpaque_F(r, g, b);
		IIcon icon = block.getIcon(0, 0);

		renderer.renderFaceXPos(block, x - 1.0F, y, z, icon);
		renderer.renderFaceXNeg(block, x + 1.0F, y, z, icon);
		renderer.renderFaceYPos(block, x, y - 1.0F, z, icon);
		renderer.renderFaceYNeg(block, x, y + 1.0F, z, icon);
		renderer.renderFaceZPos(block, x, y, z - 1.0F, icon);
		renderer.renderFaceZNeg(block, x, y, z + 1.0F, icon);

		icon = Blocks.carpet.getIcon(0, world.getBlockMetadata(x, y, z));
		renderer.renderFaceYPos(block, x, y - 1.0F, z, icon);
		renderer.renderFaceYNeg(block, x, y, z, icon);

		return renderer.renderStandardBlock(block, x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.ITEM_DISPLAY;
	}
}