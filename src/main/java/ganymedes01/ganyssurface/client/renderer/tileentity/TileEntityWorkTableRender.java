package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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
public class TileEntityWorkTableRender extends TileEntitySpecialRenderer {

	private final RenderItem itemRenderer;

	public TileEntityWorkTableRender() {
		itemRenderer = new RenderItem() {

			@Override
			public boolean shouldBob() {
				return false;
			}

			@Override
			public byte getMiniBlockCount(ItemStack stack, byte original) {
				return 1;
			}

			@Override
			public byte getMiniItemCount(ItemStack stack, byte original) {
				return 1;
			}
		};
		itemRenderer.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		renderCraftingGrid((TileEntityWorkTable) tile, (float) x, (float) y, (float) z, 0);

		if (tile instanceof TileEntityDualWorkTable)
			renderCraftingGrid((TileEntityDualWorkTable) tile, (float) x, (float) y - 0.74F, (float) z, 9);
	}

	private void renderCraftingGrid(TileEntityWorkTable workTable, float x, float y, float z, int start) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				OpenGLHelper.pushMatrix();
				EntityItem entityItem = workTable.getEntityItem(start + j + i * 3);
				if (entityItem != null) {
					float scaleFactor;
					float rotateAngle;
					if (entityItem.getEntityItem().getItem() instanceof ItemBlock) {
						scaleFactor = 0.5F;
						rotateAngle = 0.0F;
					} else {
						scaleFactor = 0.5F * 0.6F;
						rotateAngle = -90.0F;
					}

					switch (workTable.getBlockMetadata()) {
						case 2:
							OpenGLHelper.translate(x - j * 0.19F + 0.69F, y + 1.05F, z + 0.69F - i * 0.19F);
							OpenGLHelper.rotate(rotateAngle + 90.0F, 0.0F, 1.0F, 0.0F);
							break;
						case 3:
							OpenGLHelper.translate(x + 0.313F + j * 0.19F, y + 1.05F, z + 0.313F + i * 0.19F);
							OpenGLHelper.rotate(rotateAngle + 270.0F, 0.0F, 1.0F, 0.0F);
							break;
						case 4:
							OpenGLHelper.translate(x + 0.69F - i * 0.19F, y + 1.05F, z + 0.313F + j * 0.19F);
							OpenGLHelper.rotate(rotateAngle + 180.0F, 0.0F, 1.0F, 0.0F);
							break;
						case 5:
							OpenGLHelper.translate(x + 0.313F + i * 0.19F, y + 1.05F, z + 0.69F - j * 0.19F);
							OpenGLHelper.rotate(rotateAngle + 0.0F, 0.0F, 1.0F, 0.0F);
							break;
						default:
							OpenGLHelper.translate(x - j * 0.19F + 0.69F, y + 1.05F, z + 0.69F - i * 0.19F);
							OpenGLHelper.rotate(rotateAngle + 90.0F, 0.0F, 1.0F, 0.0F);
							break;
					}
					OpenGLHelper.scale(scaleFactor, scaleFactor, scaleFactor);

					itemRenderer.doRender(entityItem, 0, 0, 0, 0, 0);
				}
				OpenGLHelper.popMatrix();
			}
	}
}