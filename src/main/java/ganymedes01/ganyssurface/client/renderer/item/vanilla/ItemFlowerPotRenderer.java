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
public class ItemFlowerPotRenderer implements IItemRenderer {

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
					render(stack, -0.5F, 0.0F, -0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					render(stack, 0.0F, 0.25F, 0.0F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					OpenGLHelper.scale(2, 2, 2);
					render(stack, 0.0F, 0.0F, -0.25F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					OpenGLHelper.scale(1.5, 1.5, 1.5);
					render(stack, -0.5F, -0.25F, -0.5F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	private void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		OpenGLHelper.translate(0.5F, 0.5F, 0.5F);
		float f = 0.375F;
		float f1 = f / 2.0F;
		renderer.setRenderBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
		BlockRendererHelper.renderSimpleBlock(Blocks.flower_pot, 0, renderer);

		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);
		OpenGLHelper.disableLighting();
		float f3 = 0.1865F;
		IIcon iicon = renderer.getBlockIconFromSide(Blocks.flower_pot, 0);
		Tessellator.instance.startDrawingQuads();
		renderer.renderFaceXPos(Blocks.flower_pot, 0 - 0.5F + f3, 0, 0, iicon);
		renderer.renderFaceXNeg(Blocks.flower_pot, 0 + 0.5F - f3, 0, 0, iicon);
		renderer.renderFaceZPos(Blocks.flower_pot, 0, 0, 0 - 0.5F + f3, iicon);
		renderer.renderFaceZNeg(Blocks.flower_pot, 0, 0, 0 + 0.5F - f3, iicon);
		renderer.renderFaceYPos(Blocks.flower_pot, 0, 0 - 0.5F + f3 + 0.1875F, 0, renderer.getBlockIcon(Blocks.dirt));
		Tessellator.instance.draw();
		OpenGLHelper.enableLighting();

		OpenGLHelper.popMatrix();
	}
}