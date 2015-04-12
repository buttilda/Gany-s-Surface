package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ItemMinecartChestRenderer extends ItemMinecartRenderer {

	@Override
	protected void renderBlock(RenderBlocks renderer) {
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		OpenGLHelper.pushMatrix();
		OpenGLHelper.scale(0.75 * 0.75, 0.75 * 0.75, 0.75 * 0.75);
		OpenGLHelper.rotate(270, 0, 1, 0);
		OpenGLHelper.translate(0.0F, 6 / 16.0F, 0.0F);
		renderer.renderBlockAsItem(Blocks.chest, 0, 1);
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		OpenGLHelper.popMatrix();
	}
}