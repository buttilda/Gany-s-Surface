package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class ItemBedRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		Object renderer = data[0];
		if (renderer instanceof RenderBlocks)
			switch (type) {
				case ENTITY:
					OpenGLHelper.scale(0.5, 0.5, 0.5);
					render(stack, -0.5F, -0.25F, -1.0F, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					OpenGLHelper.rotate(180, 0, 1, 0);
					render(stack, -1.4F, 0F, -1.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					OpenGLHelper.rotate(180, 0, 1, 0);
					render(stack, -0.75F, 0.5F, -2F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					OpenGLHelper.scale(0.75, 0.75, 0.75);
					render(stack, -0.5F, -0.45F, -1.0F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	private void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		renderBed(renderer, 0, false);
		OpenGLHelper.translate(0, 0, 1);
		renderBed(renderer, 8, true);

		OpenGLHelper.popMatrix();
	}

	private void renderBed(RenderBlocks renderer, int metadata, boolean flag) {
		Tessellator tessellator = Tessellator.instance;

		Block block = Blocks.bed;

		tessellator.startDrawingQuads();

		// BOTTOM
		renderer.setRenderBounds(0, 3.0 / 16.0, 0, 1, 1, 1);
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));

		// TOP
		renderer.setRenderBoundsFromBlock(block);
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		if (flag)
			renderer.uvRotateTop = 1;
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		if (flag)
			renderer.uvRotateTop = 0;

		// BACK
		if (flag) {
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		} else {
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		}

		// SIDES
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));

		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.flipTexture = true;
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		renderer.flipTexture = false;
		tessellator.draw();
	}
}