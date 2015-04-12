package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.renderer.block.BlockRendererHelper;
import net.minecraft.block.BlockCauldron;
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
public class ItemCauldronRenderer implements IItemRenderer {

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
					render(stack, 0.0F, 0.0F, 0.0F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					render(stack, 0.0F, 0.0F, 0.0F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					render(stack, -0.5F, -0.5F, -0.5F, (RenderBlocks) renderer);
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

		IIcon walls = Blocks.cauldron.getBlockTextureFromSide(2);
		float thickness = 0.125F;
		Tessellator.instance.startDrawingQuads();
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderFaceXPos(Blocks.cauldron, 0 - 1.0F + thickness, 0, 0, walls);
		renderer.renderFaceXNeg(Blocks.cauldron, 0 + 1.0F - thickness, 0, 0, walls);
		renderer.renderFaceZPos(Blocks.cauldron, 0, 0, 0 - 1.0F + thickness, walls);
		renderer.renderFaceZNeg(Blocks.cauldron, 0, 0, 0 + 1.0F - thickness, walls);
		IIcon inner = BlockCauldron.getCauldronIcon("inner");
		renderer.renderFaceYPos(Blocks.cauldron, 0, 0 - 1.0F + 0.25F, 0, inner);
		renderer.renderFaceYNeg(Blocks.cauldron, 0, 0 + 1.0F - 0.75F, 0, inner);
		Tessellator.instance.draw();

		OpenGLHelper.translate(0.5F, 0.5F, 0.5F);
		BlockRendererHelper.renderSimpleBlock(Blocks.cauldron, 0, renderer);

		OpenGLHelper.popMatrix();
	}
}