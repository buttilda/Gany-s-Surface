package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.inventory.ContainerCraftingTable;
import ganymedes01.ganyssurface.inventory.INoConflictRecipeContainer;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
public class GuiCraftingTable extends GuiContainer {

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
	private boolean prevHasMultipleResults = false;

	public GuiCraftingTable(InventoryPlayer playerInventory, World world, int x, int y, int z) {
		super(new ContainerCraftingTable(playerInventory, world, x, y, z));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();

		INoConflictRecipeContainer container = (INoConflictRecipeContainer) inventorySlots;
		boolean hasMultipleResults = container.hasMultipleResults(true);

		if (hasMultipleResults != prevHasMultipleResults) {
			addOrRemoveButtons(hasMultipleResults);
			prevHasMultipleResults = hasMultipleResults;
		}
	}

	@SuppressWarnings("unchecked")
	private void addOrRemoveButtons(boolean add) {
		if (add) {
			buttonList.add(new GuiButton(0, guiLeft + 135, guiTop + 60, 15, 20, ">"));
			buttonList.add(new GuiButton(1, guiLeft + 115, guiTop + 60, 15, 20, "<"));
		} else
			buttonList.clear();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 0:
				PacketHandler.sendToServer(new PacketGUINoRecipeConflict(true, 1));
				break;
			case 1:
				PacketHandler.sendToServer(new PacketGUINoRecipeConflict(true, -1));
				break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(TEXTURE);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}