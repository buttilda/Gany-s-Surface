package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.renderer.block.BlockRendererHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
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
public class ItemLeverRenderer implements IItemRenderer {

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
					render(stack, 0, 0, 0, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					render(stack, 0.5F, 1.0F, 0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					OpenGLHelper.rotate(180, 0, 1, 0);
					render(stack, -0.5F, 1.0F, -0.5F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					OpenGLHelper.rotate(90, 0, 1, 0);
					OpenGLHelper.scale(1.4, 1.4, 1.4);
					render(stack, 0, 0.25F, 0, (RenderBlocks) renderer);
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

		Tessellator tessellator = Tessellator.instance;
		renderer.setOverrideBlockTexture(renderer.getBlockIcon(Blocks.cobblestone));

		float f = 0.25F;
		float f1 = 0.1875F;
		float f2 = 0.1875F;

		renderer.setRenderBounds(0.5F - f1, 0.0D, 0.5F - f, 0.5F + f1, f2, 0.5F + f);
		BlockRendererHelper.renderSimpleBlock(Blocks.lever, 0, renderer);
		OpenGLHelper.translate(-0.5, -0.5, -0.5);

		renderer.clearOverrideBlockTexture();

		IIcon icon = renderer.getBlockIconFromSide(Blocks.lever, 0);

		OpenGLHelper.translate(-0.1, 0.5, 0.1);
		OpenGLHelper.scale(1.2, 1.2, 1.2);
		OpenGLHelper.rotate(45, 1, 0, 0);

		f = 0.05F;
		tessellator.startDrawingQuads();
		renderer.setRenderBounds(0.5F - f, 0.0F, 0.4F - 0.04 * 2, 0.5F + f, 0.6F, 0.4F + 0.04 * 2);
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(Blocks.lever, 0, 0, 0 + f + f / 2, icon);
		renderer.setRenderBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(Blocks.lever, 0, 0, 0, icon);
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(Blocks.lever, 0, 0, 0, icon);
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(Blocks.lever, 0, 0, 0, icon);
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(Blocks.lever, 0, 0, 0, icon);
		tessellator.draw();

		OpenGLHelper.popMatrix();
	}
}