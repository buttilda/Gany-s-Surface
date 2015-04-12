package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerFarmManager;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityFarmManager;
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
public class GuiFarmManager extends GuiGanysSurface {

	private final TileEntityFarmManager farmManager;

	public GuiFarmManager(InventoryPlayer inventory, TileEntityFarmManager tile) {
		super(new ContainerFarmManager(inventory, tile));
		farmManager = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRendererObj.drawString(StatCollector.translateToLocal(farmManager.getInventoryName()), xSize / 2 - fontRendererObj.getStringWidth(StatCollector.translateToLocal(farmManager.getInventoryName())) / 2, 6, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Utils.getGUITexture(Strings.FARM_MANAGER_NAME)));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}
}