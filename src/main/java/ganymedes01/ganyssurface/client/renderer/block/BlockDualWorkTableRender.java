package ganymedes01.ganyssurface.client.renderer.block;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
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
public class BlockDualWorkTableRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		renderer.setRenderBounds(0, 12F / 16F, 0, 1, 1, 1);
		renderSimpleBlock(block, metadata, renderer);

		renderer.setRenderBounds(0, 0, 0, 1, 4F / 16F, 1);
		renderSimpleBlock(block, metadata, renderer);

		renderer.setRenderBounds(0, 4F / 16F, 0, 2F / 16F, 12F / 16F, 2F / 16F);
		renderSimpleBlock(block, metadata, renderer);

		renderer.setRenderBounds(0, 4F / 16F, 14F / 16F, 2F / 16F, 12F / 16F, 1);
		renderSimpleBlock(block, metadata, renderer);

		renderer.setRenderBounds(14F / 16F, 4F / 16F, 0, 1, 12F / 16F, 2F / 16F);
		renderSimpleBlock(block, metadata, renderer);

		renderer.setRenderBounds(14 / 16F, 4F / 16F, 14 / 16F, 1, 12F / 16F, 1);
		renderSimpleBlock(block, metadata, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderAllFaces = true;

		renderer.setRenderBounds(0, 12F / 16F, 0, 1, 1, 1);
		boolean top = renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0, 0, 0, 1, 4F / 16F, 1);
		boolean bottom = renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0, 4F / 16F, 0, 2F / 16F, 12F / 16F, 2F / 16F);
		boolean side0 = renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0, 4F / 16F, 14F / 16F, 2F / 16F, 12F / 16F, 1);
		boolean side1 = renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(14F / 16F, 4F / 16F, 0, 1, 12F / 16F, 2F / 16F);
		boolean side2 = renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(14 / 16F, 4F / 16F, 14 / 16F, 1, 12F / 16F, 1);
		boolean side3 = renderer.renderStandardBlock(block, x, y, z);

		renderer.renderAllFaces = false;
		return top | bottom | side0 | side1 | side2 | side3;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.DUAL_WORK_TABLE;
	}

	private void renderSimpleBlock(Block block, int metadata, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		tessellator.draw();

		OpenGLHelper.translate(0.5F, 0.5F, 0.5F);
	}
}