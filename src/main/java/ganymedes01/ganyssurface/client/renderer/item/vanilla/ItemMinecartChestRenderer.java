package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;

import org.lwjgl.opengl.GL11;

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

		GL11.glPushMatrix();
		GL11.glScaled(0.75 * 0.75, 0.75 * 0.75, 0.75 * 0.75);
		GL11.glRotated(270, 0, 1, 0);
		GL11.glTranslatef(0.0F, 6 / 16.0F, 0.0F);
		renderer.renderBlockAsItem(Blocks.chest, 0, 1);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
}