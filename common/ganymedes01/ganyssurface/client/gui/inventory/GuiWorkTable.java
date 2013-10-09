package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerWorkTable;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.client.gui.inventory.GuiContainer;
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
public class GuiWorkTable extends GuiContainer {

	public GuiWorkTable(InventoryPlayer inventory, TileEntityWorkTable tile) {
		super(new ContainerWorkTable(inventory, tile));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRenderer.drawString(StatCollector.translateToLocal(Utils.getConainerName(Strings.WORK_TABLE_NAME)), 28, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/crafting_table.png"));
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
