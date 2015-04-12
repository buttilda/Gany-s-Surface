package ganymedes01.ganyssurface.client.renderer.item;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.items.Painting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityPainting.EnumArt;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class ItemPaintingRenderer implements IItemRenderer {

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		OpenGLHelper.pushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		OpenGLHelper.scale(0.0625F, 0.0625F, 0.0625F);
		OpenGLHelper.disableLighting();
		OpenGLHelper.rotate(225, 0, 1, 0);

		EnumArt art = EnumArt.values()[Painting.getMeta(stack)];
		if (art.sizeX > 26)
			OpenGLHelper.scale(26.0 / art.sizeX, 26.0 / art.sizeX, 1);
		else if (art.sizeY > 26)
			OpenGLHelper.scale(26.0 / art.sizeY, 26.0 / art.sizeY, 1);
		renderPainting(art.sizeX, art.sizeY, art.offsetX, art.offsetY);

		OpenGLHelper.enableLighting();
		OpenGLHelper.popMatrix();
	}

	private void renderPainting(int width, int height, int offsetX, int offsetY) {
		float f = -width / 2.0F;
		float f1 = -height / 2.0F;
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		for (int w = 0; w < width / 16; w++)
			for (int h = 0; h < height / 16; h++) {
				float f2 = f + (w + 1) * 16;
				float f3 = f + w * 16;
				float f4 = f1 + (h + 1) * 16;
				float f5 = f1 + h * 16;
				float f6 = (offsetX + width - w * 16) / 256.0F;
				float f7 = (offsetX + width - (w + 1) * 16) / 256.0F;
				float f8 = (offsetY + height - h * 16) / 256.0F;
				float f9 = (offsetY + height - (h + 1) * 16) / 256.0F;

				tess.addVertexWithUV(f2, f5, 0, f7, f8);
				tess.addVertexWithUV(f3, f5, 0, f6, f8);
				tess.addVertexWithUV(f3, f4, 0, f6, f9);
				tess.addVertexWithUV(f2, f4, 0, f7, f9);
			}
		tess.draw();
	}
}