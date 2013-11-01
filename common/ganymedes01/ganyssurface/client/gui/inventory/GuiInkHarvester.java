package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.inventory.ContainerInkHarvester;
import ganymedes01.ganyssurface.tileentities.TileEntityInkHarvester;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class GuiInkHarvester extends GuiGanysSurface {

	private final TileEntityInkHarvester harvester;

	public GuiInkHarvester(InventoryPlayer inventory, TileEntityInkHarvester tile) {
		super(new ContainerInkHarvester(inventory, tile));
		harvester = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRenderer.drawString(StatCollector.translateToLocal(harvester.getInvName()), xSize / 2 - fontRenderer.getStringWidth(StatCollector.translateToLocal(harvester.getInvName())) / 2, 6, BLACK);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/dispenser.png"));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}
}