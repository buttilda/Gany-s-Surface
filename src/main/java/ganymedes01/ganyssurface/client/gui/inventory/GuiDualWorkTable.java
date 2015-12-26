package ganymedes01.ganyssurface.client.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerDualWorkTable;
import ganymedes01.ganyssurface.inventory.INoConflictRecipeContainer;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
import net.minecraft.client.gui.GuiButton;
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
public class GuiDualWorkTable extends GuiGanysSurface {

	private boolean prevHasMultipleResults1 = false, prevHasMultipleResults2 = false;

	public GuiDualWorkTable(InventoryPlayer inventory, TileEntityDualWorkTable tile) {
		this(new ContainerDualWorkTable(inventory, tile));
	}

	public GuiDualWorkTable(ContainerDualWorkTable container) {
		super(container);
		xSize = 200;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!GanysSurface.enableNoRecipeConflict)
			return;

		INoConflictRecipeContainer container = (INoConflictRecipeContainer) inventorySlots;

		boolean hasMultipleResults = container.hasMultipleResults(true);
		if (hasMultipleResults != prevHasMultipleResults1) {
			addOrRemoveButtons(true, hasMultipleResults);
			prevHasMultipleResults1 = hasMultipleResults;
		}

		hasMultipleResults = container.hasMultipleResults(false);
		if (hasMultipleResults != prevHasMultipleResults2) {
			addOrRemoveButtons(false, hasMultipleResults);
			prevHasMultipleResults2 = hasMultipleResults;
		}
	}

	@SuppressWarnings("unchecked")
	private void addOrRemoveButtons(boolean isFirstMatrix, boolean add) {
		if (add) {
			if (isFirstMatrix) {
				buttonList.add(new GuiButton(0, guiLeft + 179 - 92, guiTop + 58, 15, 20, ">"));
				buttonList.add(new GuiButton(1, guiLeft + 159 - 92, guiTop + 58, 15, 20, "<"));
			} else {
				buttonList.add(new GuiButton(2, guiLeft + 179, guiTop + 58, 15, 20, ">"));
				buttonList.add(new GuiButton(3, guiLeft + 159, guiTop + 58, 15, 20, "<"));
			}
		} else {
			List<GuiButton> toRemove = new ArrayList<GuiButton>();
			for (Object obj : buttonList) {
				GuiButton button = (GuiButton) obj;
				if (isFirstMatrix && (button.id == 0 || button.id == 1))
					toRemove.add(button);
				if (!isFirstMatrix && (button.id == 2 || button.id == 3))
					toRemove.add(button);
			}
			buttonList.removeAll(toRemove);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (!GanysSurface.enableNoRecipeConflict)
			return;
		switch (button.id) {
			case 0:
				PacketHandler.sendToServer(new PacketGUINoRecipeConflict(true, 1));
				break;
			case 1:
				PacketHandler.sendToServer(new PacketGUINoRecipeConflict(true, -1));
				break;
			case 2:
				PacketHandler.sendToServer(new PacketGUINoRecipeConflict(false, 1));
				break;
			case 3:
				PacketHandler.sendToServer(new PacketGUINoRecipeConflict(false, -1));
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