package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerMarket;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class GuiMarket extends GuiGanysSurface {

	private final ResourceLocation TEXTURE = Utils.getResource(Utils.getGUITexture(Strings.MARKET));
	private final TileEntityMarket market;

	public GuiMarket(InventoryPlayer player, TileEntityMarket market) {
		super(new ContainerMarket(player, market));
		this.market = market;
		ySize = 192;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRendererObj.drawString(StatCollector.translateToLocalFormatted(Utils.getString("isselling"), market.getOwner()), 28, 6, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal(Utils.getString("for")), 35, 32, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);

		if (mc.thePlayer.getCommandSenderName().equals(market.getOwner()))
			fontRendererObj.drawString(StatCollector.translateToLocal(Utils.getString("stockyourenderchest")), 8, 58, Utils.getColour(255, 0, 0));
		else
			fontRendererObj.drawString(StatCollector.translateToLocal(Utils.getString("payhere")), 8, 58, BLACK);
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