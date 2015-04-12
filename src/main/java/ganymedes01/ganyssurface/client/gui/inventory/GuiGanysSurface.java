package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public abstract class GuiGanysSurface extends GuiContainer {

	protected final int BLACK = Utils.getColour(0, 0, 0);

	public GuiGanysSurface(Container container) {
		super(container);
	}

	@Override
	protected abstract void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY);

	protected void drawToolTip(int mouseX, int mouseY, String text) {
		OpenGLHelper.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		OpenGLHelper.disableLighting();
		OpenGLHelper.disableDepth();
		int k = 0;
		int l = fontRendererObj.getStringWidth(text);

		if (l > k)
			k = l;

		int i1 = mouseX + 12;
		int j1 = mouseY - 12;
		int k1 = 8;

		if (i1 + k > width)
			i1 -= 28 + k;

		if (j1 + k1 + 6 > height)
			j1 = height - k1 - 6;

		zLevel = 300.0F;
		itemRender.zLevel = 300.0F;
		int l1 = -267386864;
		drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
		drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
		drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
		drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
		drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
		int i2 = 1347420415;
		int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
		drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
		drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
		drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

		fontRendererObj.drawStringWithShadow(text, i1, j1, -1);

		zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		OpenGLHelper.enableLighting();
		OpenGLHelper.enableDepth();
		RenderHelper.enableStandardItemLighting();
		OpenGLHelper.enableRescaleNormal();
	}

	protected void displayGauge(Fluid fluid, int j, int k, int u, int v, int scaled) {
		int start = 0;
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		while (true) {
			int x;
			if (scaled > 16) {
				x = 16;
				scaled -= 16;
			} else {
				x = scaled;
				scaled = 0;
			}
			drawTexturedModelRectFromIcon(j + u, k + v + 52 - x - start, fluid.getStillIcon(), 16, 16 - (16 - x));
			start += 16;
			if (x == 0 || scaled == 0)
				break;
		}
	}
}