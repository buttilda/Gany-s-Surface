package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerDualWorkTable;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketGUIDualWorkTable;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
import net.minecraft.client.gui.GuiButton;
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
public class GuiDualWorkTable extends GuiGanysSurface {

	public GuiDualWorkTable(InventoryPlayer inventory, TileEntityDualWorkTable tile) {
		this(new ContainerDualWorkTable(inventory, tile));
	}

	public GuiDualWorkTable(ContainerDualWorkTable container) {
		super(container);
		xSize = 200;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();
		if (GanysSurface.enableNoRecipeConflict) {
			buttonList.add(new GuiButton(0, 200, 95, 15, 20, ">"));
			buttonList.add(new GuiButton(1, 180, 95, 15, 20, "<"));
			buttonList.add(new GuiButton(2, 292, 95, 15, 20, ">"));
			buttonList.add(new GuiButton(3, 272, 95, 15, 20, "<"));
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (!GanysSurface.enableNoRecipeConflict)
			return;
		switch (button.id) {
			case 0:
				PacketHandler.sendToServer(new PacketGUIDualWorkTable(true, 1));
				break;
			case 1:
				PacketHandler.sendToServer(new PacketGUIDualWorkTable(true, -1));
				break;
			case 2:
				PacketHandler.sendToServer(new PacketGUIDualWorkTable(false, 1));
				break;
			case 3:
				PacketHandler.sendToServer(new PacketGUIDualWorkTable(false, -1));
				break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRendererObj.drawString(StatCollector.translateToLocal(Utils.getConainerName(Strings.DUAL_WORK_TABLE_NAME)), 28, 6, BLACK);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, BLACK);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Utils.getGUITexture(Strings.DUAL_WORK_TABLE_NAME)));
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}