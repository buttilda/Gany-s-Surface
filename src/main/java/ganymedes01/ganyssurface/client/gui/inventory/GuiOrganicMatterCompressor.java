package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerOrganicMatterCompressor;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityOrganicMatterCompressor;
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
public class GuiOrganicMatterCompressor extends GuiGanysSurface {

	private final TileEntityOrganicMatterCompressor compressor;

	public GuiOrganicMatterCompressor(InventoryPlayer inventory, TileEntityOrganicMatterCompressor tile) {
		super(new ContainerOrganicMatterCompressor(inventory, tile));
		compressor = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRendererObj.drawString(StatCollector.translateToLocal(compressor.getInventoryName()), xSize / 2 - fontRendererObj.getStringWidth(StatCollector.translateToLocal(compressor.getInventoryName())) / 2, 6, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal("matter"), 89, ySize - 94, BLACK);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Utils.getGUITexture(Strings.ORGANIC_MATTER_COMPRESSOR_NAME)));
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		drawTexturedModalRect(j + 95, k + 36, 177, 14, compressor.getScaledWorkTime(24), 16);
		fontRendererObj.drawString(Integer.toString(Math.round(compressor.getOrganicMatter() * 100)) + "%", fontRendererObj.getStringWidth(StatCollector.translateToLocal("matter")) + j + 94, k + 72, 4210752);
	}
}
