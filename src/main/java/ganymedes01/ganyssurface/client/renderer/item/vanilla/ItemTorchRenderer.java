package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
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
public class ItemTorchRenderer implements IItemRenderer {

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
					render(stack, -0.5F, -0.25F, -0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					OpenGLHelper.rotate(45, -1, 0, 1);
					render(stack, 0.25F, -0.5F, 0.25F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					render(stack, 0.0F, 0.5F, 0.15F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					OpenGLHelper.scale(2, 2, 2);
					render(stack, -0.5F, -0.3F, -0.5F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	public static void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		OpenGLHelper.disableLighting();
		renderTorch(Block.getBlockFromItem(stack.getItem()), 0, 0, 0, renderer, 0);
		OpenGLHelper.enableLighting();

		OpenGLHelper.popMatrix();
	}

	public static void renderTorch(Block block, double x, double y, double z, RenderBlocks renderer, double size) {
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		Tessellator tessellator = Tessellator.instance;
		IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 0, 0);

		if (renderer.hasOverrideBlockTexture())
			icon = renderer.overrideBlockTexture;

		tessellator.startDrawingQuads();

		renderer.setRenderBounds(7F / 16F, 0, 6F / 16F, 9 / 16F, 10F / 16F, 8F / 16F);
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y, z + 1F / 16F, icon);

		renderer.setRenderBounds(6F / 16F, size, 6F / 16F, 10F / 16F, 1, 10F / 16F);
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, x, y, z + 1F / 16F, icon);
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, x, y, z - 1F / 16F, icon);
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x + 1F / 16F, y, z, icon);
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x - 1F / 16F, y, z, icon);

		tessellator.draw();
	}
}