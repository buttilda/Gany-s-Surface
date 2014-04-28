package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.client.model.ModelDualWorkTable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

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
public class TileEntityWorkTableRender extends TileEntitySpecialRenderer {

	private RenderItem customRenderItem;
	private final ModelDualWorkTable model = new ModelDualWorkTable();

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
		renderCraftingGrid((TileEntityWorkTable) tile, (float) x, (float) y, (float) z, 0);
		if (tile instanceof TileEntityDualWorkTable) {
			GL11.glPushMatrix();
			bindTexture(new ResourceLocation(Utils.getEntityTexture(Strings.DUAL_WORK_TABLE_NAME)));
			GL11.glTranslatef((float) x + 1.0F, (float) y + 1.0F, (float) z);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			model.renderAll(tile.getBlockMetadata());
			GL11.glPopMatrix();
			renderCraftingGrid((TileEntityDualWorkTable) tile, (float) x, (float) y - 0.6875F, (float) z, 9);
		}
	}

	private void renderCraftingGrid(TileEntityWorkTable workTable, float x, float y, float z, int start) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				GL11.glPushMatrix();
				ItemStack stack = workTable.getStackInSlot(start + j + i * 3);
				if (stack != null) {
					float scaleFactor;
					float rotateAngle;
					if (stack.getItem() instanceof ItemBlock) {
						scaleFactor = 0.5F;
						rotateAngle = 0.0F;
					} else {
						scaleFactor = 0.5F * 0.6F;
						rotateAngle = -90.0F;
					}

					EntityItem ghostEntityItem = new EntityItem(workTable.getWorldObj());
					ghostEntityItem.hoverStart = 0.0F;
					ItemStack stackToRender = stack.copy();
					stackToRender.stackSize = 1;
					ghostEntityItem.setEntityItemStack(stackToRender);
					switch (workTable.getBlockMetadata()) {
						case 2:
							GL11.glTranslatef(x - j * 0.19F + 0.69F, y + 1.05F, z + 0.69F - i * 0.19F);
							GL11.glRotatef(rotateAngle + 90.0F, 0.0F, 1.0F, 0.0F);
							break;
						case 3:
							GL11.glTranslatef(x + 0.313F + j * 0.19F, y + 1.05F, z + 0.313F + i * 0.19F);
							GL11.glRotatef(rotateAngle + 270.0F, 0.0F, 1.0F, 0.0F);
							break;
						case 4:
							GL11.glTranslatef(x + 0.69F - i * 0.19F, y + 1.05F, z + 0.313F + j * 0.19F);
							GL11.glRotatef(rotateAngle + 180.0F, 0.0F, 1.0F, 0.0F);
							break;
						case 5:
							GL11.glTranslatef(x + 0.313F + i * 0.19F, y + 1.05F, z + 0.69F - j * 0.19F);
							GL11.glRotatef(rotateAngle + 0.0F, 0.0F, 1.0F, 0.0F);
							break;
						default:
							GL11.glTranslatef(x - j * 0.19F + 0.69F, y + 1.05F, z + 0.69F - i * 0.19F);
							GL11.glRotatef(rotateAngle + 90.0F, 0.0F, 1.0F, 0.0F);
							break;
					}
					GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);

					customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
				}
				GL11.glPopMatrix();
			}

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}