package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class TileEntityItemDisplayRender extends TileEntitySpecialRenderer {

	private RenderItem customRenderItem;

	public TileEntityItemDisplayRender() {
		customRenderItem = new RenderItem() {
			@Override
			public boolean shouldBob() {
				return false;
			}
		};
		customRenderItem.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		TileEntityItemDisplay itemDisplay = (TileEntityItemDisplay) tile;

		if (itemDisplay.getDisplayItem() != null) {
			float scaleFactor, translate;
			if (itemDisplay.getDisplayItem().getItem() instanceof ItemBlock || itemDisplay.getDisplayItem().getItem() == ModItems.storageCase) {
				scaleFactor = 2.5F;
				translate = 0.4F;
			} else {
				scaleFactor = 2.5F * 0.6F;
				translate = 0.3F;
			}
			float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

			OpenGLHelper.pushMatrix();
			OpenGLHelper.translate((float) x + 0.5F, (float) (y + translate), (float) z + 0.5F);
			OpenGLHelper.scale(scaleFactor, scaleFactor, scaleFactor);
			OpenGLHelper.rotate(rotationAngle, 0.0F, 1.0F, 0.0F);
			customRenderItem.doRender(itemDisplay.getItemForRendering(), 0, 0, 0, 0, 0);
			OpenGLHelper.popMatrix();
		}
	}
}