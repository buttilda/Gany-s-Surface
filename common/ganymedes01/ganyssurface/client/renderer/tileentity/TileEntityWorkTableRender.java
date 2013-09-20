package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityWorkTableRender extends TileEntitySpecialRenderer {

	private RenderItem customRenderItem;

	public TileEntityWorkTableRender() {
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
		TileEntityWorkTable workTable = (TileEntityWorkTable) tile;
		if (workTable.worldObj.isAirBlock(workTable.xCoord, workTable.yCoord + 1, workTable.zCoord)) {

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					GL11.glPushMatrix();
					if (workTable.getStackInSlot(j + i * 3) != null) {

						float scaleFactor;
						if (workTable.getStackInSlot(j + i * 3).getItem() instanceof ItemBlock)
							scaleFactor = 0.5F;
						else
							scaleFactor = 0.5F * 0.6F;

						EntityItem ghostEntityItem = new EntityItem(workTable.worldObj);
						ghostEntityItem.hoverStart = 0.0F;
						ItemStack stack = workTable.getStackInSlot(j + i * 3).copy();
						stack.stackSize = 1;
						ghostEntityItem.setEntityItemStack(stack);
						switch (workTable.blockMetadata) {
							case 2:
							case 3:
								GL11.glTranslatef((float) x + 0.69F - i * 0.19F, (float) (y + 1.05F), (float) z - j * 0.19F + 0.69F);
								break;
							case 4:
							case 5:
								GL11.glTranslatef((float) x - j * 0.19F + 0.69F, (float) (y + 1.05F), (float) z + 0.69F - i * 0.19F);
								break;
							default:
								GL11.glTranslatef((float) x - j * 0.19F + 0.69F, (float) (y + 1.05F), (float) z + 0.69F - i * 0.19F);
								break;
						}
						GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);

						customRenderItem.doRenderItem(ghostEntityItem, 0, 0, 0, 0, 0);
					}
					GL11.glPopMatrix();
				}

			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}
}
