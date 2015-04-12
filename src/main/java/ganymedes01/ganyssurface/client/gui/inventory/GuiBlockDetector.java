package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerBlockDetector;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityBlockDetector;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class GuiBlockDetector extends GuiGanysSurface {

	private final TileEntityBlockDetector detector;

	public GuiBlockDetector(InventoryPlayer inventory, TileEntityBlockDetector tile) {
		super(new ContainerBlockDetector(inventory, tile));
		detector = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRendererObj.drawString(StatCollector.translateToLocal(detector.getInventoryName()), xSize / 2 - fontRendererObj.getStringWidth(StatCollector.translateToLocal(detector.getInventoryName())) / 2, 6, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Utils.getGUITexture(Strings.BLOCK_DETECTOR_NAME)));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}
}