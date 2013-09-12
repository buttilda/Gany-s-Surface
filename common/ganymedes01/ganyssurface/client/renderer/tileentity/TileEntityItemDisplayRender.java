package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.client.model.ModelItemDisplay;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityItemDisplayRender extends TileEntitySpecialRenderer {

	ModelItemDisplay model = new ModelItemDisplay();
	private RenderItem customRenderItem;

	public TileEntityItemDisplayRender() {
		customRenderItem = new RenderItem() {
			@Override
			public boolean shouldBob() {
				return false;
			};
		};
		customRenderItem.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		TileEntityItemDisplay itemDisplay = (TileEntityItemDisplay) tile;
		bindTexture(new ResourceLocation(Utils.getEntityTexture(Strings.ITEM_DISPLAY_NAME)));

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 1.0F, (float) y + 1.0F, (float) z + 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		model.renderAll();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		if (itemDisplay.getDisplayItem() != null) {

			float scaleFactor;
			float translate;
			if (itemDisplay.getDisplayItem().getItem() instanceof ItemBlock) {
				scaleFactor = 2.5F;
				translate = 0.4F;
			} else {
				scaleFactor = 2.5F * 0.6F;
				translate = 0.3F;
			}
			float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

			EntityItem ghostEntityItem = new EntityItem(itemDisplay.worldObj);
			ghostEntityItem.hoverStart = 0.0F;
			ghostEntityItem.setEntityItemStack(itemDisplay.getDisplayItem());
			GL11.glTranslatef((float) x + 0.5F, (float) (y + translate), (float) z + 0.5F);
			GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
			GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);

			customRenderItem.doRenderItem(ghostEntityItem, 0, 0, 0, 0, 0);
		}
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
