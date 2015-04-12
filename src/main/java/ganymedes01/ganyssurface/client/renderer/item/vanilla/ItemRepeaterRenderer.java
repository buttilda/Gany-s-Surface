package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.renderer.block.BlockRendererHelper;
import net.minecraft.block.BlockRedstoneRepeater;
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
public class ItemRepeaterRenderer implements IItemRenderer {

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
					render(stack, -0.5F, -0.5F, -0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					render(stack, 0.0F, 0.5F, 0.0F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					render(stack, 0.0F, 0.5F, 0.0F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					OpenGLHelper.scale(1.1, 1.1, 1.1);
					render(stack, -0.5F, -0.25F, -0.5F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	protected void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		Tessellator tessellator = Tessellator.instance;

		double torchHeight = -0.1875;
		double torchOffset = BlockRedstoneRepeater.repeaterTorchOffset[0];
		double torch2Offset = -0.3125;

		OpenGLHelper.enableCull();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		ItemTorchRenderer.renderTorch(Blocks.unpowered_repeater, 0, torchHeight, torchOffset, renderer, 0.2);
		ItemTorchRenderer.renderTorch(Blocks.unpowered_repeater, 0, torchHeight, torch2Offset, renderer, 0.2);
		OpenGLHelper.enableLighting();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0, -1, 0);
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderFaceYNeg(Blocks.unpowered_repeater, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(Blocks.stone_slab, 0, 0));
		tessellator.draw();

		OpenGLHelper.translate(0.5, 0.5, 0.5);
		renderer.setRenderBounds(0, 0.001, 0, 1, 0.125, 1);
		BlockRendererHelper.renderSimpleBlock(Blocks.unpowered_repeater, 0, renderer);

		OpenGLHelper.popMatrix();
	}
}