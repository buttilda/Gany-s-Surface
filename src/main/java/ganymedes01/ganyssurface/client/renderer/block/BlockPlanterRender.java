package ganymedes01.ganyssurface.client.renderer.block;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.model.ModelPlanter;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.RenderIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.FMLClientHandler;
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
public class BlockPlanterRender implements ISimpleBlockRenderingHandler {

	private final ModelPlanter model = new ModelPlanter();
	private final ResourceLocation texture = Utils.getResource(Utils.getEntityTexture(Strings.PLANTER_NAME));

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		OpenGLHelper.translate(-0.5F, 1.0F, 0.5F);
		OpenGLHelper.rotate(180, 1, 0, 0);
		model.moveArm(0.3F);
		model.renderAll();
		OpenGLHelper.popMatrix();

		OpenGLHelper.pushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		OpenGLHelper.translate(0, 0.5F, 0);
		BlockRendererHelper.renderSimpleBlock(block, metadata, renderer);
		OpenGLHelper.popMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return renderer.renderStandardBlock(block, x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.PLANTER;
	}
}