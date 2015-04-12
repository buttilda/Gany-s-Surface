package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
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
public class TileEntityChestPropellantRender extends TileEntitySpecialRenderer {

	private RenderItem customRenderItem;

	public TileEntityChestPropellantRender() {
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
		TileEntityChestPropellant chestPropellant = (TileEntityChestPropellant) tile;
		if (chestPropellant.getWorldObj().getBlock(chestPropellant.xCoord, chestPropellant.yCoord + 1, chestPropellant.zCoord) != ModBlocks.chestPropellant) {
			EntityItem entityItem = chestPropellant.getEntityItem();
			if (entityItem != null) {
				ItemStack stack = entityItem.getEntityItem();
				float rot1, rot2, rot3, rot4, scale, offset, posY;
				rot1 = 0.0F;
				rot2 = 90.0F;
				rot3 = 180.0F;
				rot4 = 270.0F;
				scale = 2.0F;
				offset = 0.0F;
				posY = 0.5F;

				if (Block.getBlockFromItem(stack.getItem()).getItemIconName() != null || Block.getBlockFromItem(stack.getItem()) == Blocks.brewing_stand) {
					rot1 -= 90.0F;
					rot2 -= 90.0F;
					rot3 -= 90.0F;
					rot4 -= 90.0F;
					scale -= 1.0F;
					offset += 0.14F;
					posY -= 0.1F;
				}

				renderEntityItem(entityItem, (float) x + 0.8F + offset, (float) (y + posY), (float) z + 0.5F, scale, rot1);

				renderEntityItem(entityItem, (float) x + 0.5F, (float) (y + posY), (float) z + 0.2F - offset, scale, rot2);

				renderEntityItem(entityItem, (float) x + 0.2F - offset, (float) (y + posY), (float) z + 0.5F, scale, rot3);

				renderEntityItem(entityItem, (float) x + 0.5F, (float) (y + posY), (float) z + 0.8F + offset, scale, rot4);
			}
		}
	}

	private void renderEntityItem(EntityItem entityItem, float x, float y, float z, float scale, float rotation) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		OpenGLHelper.scale(scale, scale, scale);
		OpenGLHelper.rotate(rotation, 0.0F, 1.0F, 0.0F);
		customRenderItem.doRender(entityItem, 0, 0, 0, 0, 0);
		OpenGLHelper.popMatrix();
	}
}