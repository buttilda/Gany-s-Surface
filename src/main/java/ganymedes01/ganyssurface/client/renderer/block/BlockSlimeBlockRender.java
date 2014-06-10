package ganymedes01.ganyssurface.client.renderer.block;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

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
public class BlockSlimeBlockRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if (MinecraftForgeClient.getRenderPass() == 0) {
			float f = 0.0625F;

			renderer.setRenderBounds(f * 2, f * 2, f * 2, f * 14, f * 14, f * 14);

			renderer.renderFaceXPos(block, x, y, z, ModBlocks.slimeBlock.getIcon(5, 1));
			renderer.renderFaceYPos(block, x, y, z, ModBlocks.slimeBlock.getIcon(1, 1));
			renderer.renderFaceZPos(block, x, y, z, ModBlocks.slimeBlock.getIcon(3, 1));

			renderer.renderFaceXNeg(block, x, y, z, ModBlocks.slimeBlock.getIcon(4, 1));
			renderer.renderFaceYNeg(block, x, y, z, ModBlocks.slimeBlock.getIcon(0, 1));
			renderer.renderFaceZNeg(block, x, y, z, ModBlocks.slimeBlock.getIcon(2, 1));
			return true;
		} else
			return renderer.renderStandardBlock(ModBlocks.slimeBlock, x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.SLIME_BLOCK;
	}
}