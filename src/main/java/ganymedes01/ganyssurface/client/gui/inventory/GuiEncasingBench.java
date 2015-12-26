package ganymedes01.ganyssurface.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerEncasingBench;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class GuiEncasingBench extends GuiGanysSurface {

	private static final ResourceLocation TEXTURE = Utils.getResource(Utils.getGUITexture("encasing_bench"));

	public GuiEncasingBench(InventoryPlayer player) {
		super(new ContainerEncasingBench(player));
		xSize = 176;
		ySize = 172;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String name = StatCollector.translateToLocal(Utils.getConainerName(Strings.ENCASING_BENCH_NAME));
		fontRendererObj.drawString(name, (xSize - fontRendererObj.getStringWidth(name)) / 2, 6, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(TEXTURE);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}