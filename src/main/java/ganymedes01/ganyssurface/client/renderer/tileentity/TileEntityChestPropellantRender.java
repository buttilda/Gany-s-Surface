package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

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
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);

			if (chestPropellant.getInventoryToRender() != null) {
				EntityItem ghostEntityItem = new EntityItem(chestPropellant.getWorldObj());
				ghostEntityItem.hoverStart = 0.0F;
				ItemStack stack = chestPropellant.getInventoryToRender();
				if (stack.getItem() == Item.getItemFromBlock(Blocks.furnace))
					ghostEntityItem.setEntityItemStack(new ItemStack(Blocks.furnace));
				else
					ghostEntityItem.setEntityItemStack(stack);

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
					if (Block.getBlockFromItem(stack.getItem()) == Blocks.brewing_stand)
						ghostEntityItem.setEntityItemStack(new ItemStack(Items.brewing_stand));
				}

				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.8F + offset, (float) (y + posY), (float) z + 0.5F);
				GL11.glScalef(scale, scale, scale);
				GL11.glRotatef(rot1, 0.0F, 1.0F, 0.0F);
				customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.5F, (float) (y + posY), (float) z + 0.2F - offset);
				GL11.glScalef(scale, scale, scale);
				GL11.glRotatef(rot2, 0.0F, 1.0F, 0.0F);
				customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.2F - offset, (float) (y + posY), (float) z + 0.5F);
				GL11.glScalef(scale, scale, scale);
				GL11.glRotatef(rot3, 0.0F, 1.0F, 0.0F);
				customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.5F, (float) (y + posY), (float) z + 0.8F + offset);
				GL11.glScalef(scale, scale, scale);
				GL11.glRotatef(rot4, 0.0F, 1.0F, 0.0F);
				customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix();
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}
}