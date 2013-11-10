package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerMarketPublic;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class GuiMarketPublic extends GuiGanysSurface {

	private final TileEntityMarket market;

	public GuiMarketPublic(InventoryPlayer inventory, TileEntityMarket tile, String username) {
		super(new ContainerMarketPublic(inventory, tile, username));
		market = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String title = StatCollector.translateToLocal(market.getInvName()) + ": " + market.getOwner();
		fontRenderer.drawString(title, 6, 6, BLACK);

		String price = StatCollector.translateToLocal("price");
		fontRenderer.drawString(price, 36 - fontRenderer.getStringWidth(price), 22, BLACK);
		String offer = StatCollector.translateToLocal("offer");
		fontRenderer.drawString(offer, 36 - fontRenderer.getStringWidth(offer), 51, BLACK);
		fontRenderer.drawString("x" + market.getQuantityOfferONE(), 36 - fontRenderer.getStringWidth(offer), 60, BLACK);

		price = StatCollector.translateToLocal("price");
		fontRenderer.drawString(price, 87 + 36 - fontRenderer.getStringWidth(price), 22, BLACK);
		offer = StatCollector.translateToLocal("offer");
		fontRenderer.drawString(offer, 87 + 36 - fontRenderer.getStringWidth(offer), 51, BLACK);
		fontRenderer.drawString("x" + market.getQuantityOfferTWO(), 87 + 36 - fontRenderer.getStringWidth(offer), 60, BLACK);

		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Utils.getGUITexture(Strings.MARKET_NAME + "_public")));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}
}