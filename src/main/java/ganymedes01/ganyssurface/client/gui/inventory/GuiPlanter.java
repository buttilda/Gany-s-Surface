package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.inventory.ContainerPlanter;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
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
public class GuiPlanter extends GuiGanysSurface {

	private final TileEntityPlanter planter;

	public GuiPlanter(InventoryPlayer inventory, TileEntityPlanter tile) {
		super(new ContainerPlanter(inventory, tile));
		planter = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRendererObj.drawString(StatCollector.translateToLocal(planter.getInventoryName()), xSize / 2 - fontRendererObj.getStringWidth(StatCollector.translateToLocal(planter.getInventoryName())) / 2, 6, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/dispenser.png"));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}
}