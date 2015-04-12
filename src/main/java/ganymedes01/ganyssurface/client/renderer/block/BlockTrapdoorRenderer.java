package ganymedes01.ganyssurface.client.renderer.block;

import ganymedes01.ganyssurface.blocks.BlockWoodTrapdoor;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
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
public class BlockTrapdoorRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		BlockWoodTrapdoor trapdoor = (BlockWoodTrapdoor) block;

		Tessellator tessellator = Tessellator.instance;
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

		float f = 0.1875F;
		renderer.setRenderBounds(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, trapdoor.getIcon(0, meta));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, trapdoor.getIcon(1, meta));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, Blocks.planks.getIcon(2, trapdoor.woodMeta));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, Blocks.planks.getIcon(3, trapdoor.woodMeta));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, Blocks.planks.getIcon(4, trapdoor.woodMeta));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, Blocks.planks.getIcon(5, trapdoor.woodMeta));
		tessellator.draw();

		OpenGLHelper.disableBlend();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		int meta = world.getBlockMetadata(x, y, z);
		boolean isOpen = BlockTrapDoor.func_150118_d(meta);

		if (!isOpen)
			switch (meta & 3) {
				case 0:
					renderer.uvRotateTop = 3;
					renderer.uvRotateBottom = 3;
					renderer.uvRotateNorth = 3;
					renderer.uvRotateSouth = 3;
					renderer.uvRotateEast = 3;
					break;
				case 1:
					renderer.uvRotateTop = 0;
					renderer.uvRotateBottom = 0;
					renderer.uvRotateNorth = 3;
					renderer.uvRotateSouth = 3;
					renderer.uvRotateWest = 3;
					break;
				case 2:
					renderer.uvRotateTop = 1;
					renderer.uvRotateBottom = 2;
					renderer.uvRotateNorth = 3;
					renderer.uvRotateEast = 3;
					renderer.uvRotateWest = 3;
					break;
				case 3:
					renderer.uvRotateTop = 2;
					renderer.uvRotateBottom = 1;
					renderer.uvRotateSouth = 3;
					renderer.uvRotateEast = 3;
					renderer.uvRotateWest = 3;
					break;
			}
		else
			switch (meta & 3) {
				case 0:
					renderer.uvRotateTop = 0;
					renderer.uvRotateNorth = 2;
					renderer.uvRotateSouth = 1;
					renderer.uvRotateBottom = 0;
					break;
				case 1:
					renderer.uvRotateTop = 3;
					renderer.uvRotateNorth = 1;
					renderer.uvRotateSouth = 2;
					renderer.uvRotateBottom = 3;
					break;
				case 2:
					renderer.uvRotateTop = 2;
					renderer.uvRotateEast = 1;
					renderer.uvRotateWest = 2;
					renderer.uvRotateBottom = 1;
					break;
				case 3:
					renderer.uvRotateTop = 1;
					renderer.uvRotateEast = 2;
					renderer.uvRotateWest = 1;
					renderer.uvRotateBottom = 2;
					break;
			}

		boolean result = renderer.renderStandardBlock(block, x, y, z);
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		return result;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.TRAPDOOR;
	}
}