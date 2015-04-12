package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.renderer.block.BlockRendererHelper;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.RenderBlocks;
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
public class ItemHopperRenderer implements IItemRenderer {

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
		renderer.renderBlockHopperMetadata(Blocks.hopper, 0, 0, 0, 0, true);

		OpenGLHelper.translate(0.5F, 0.5F, 0.5F);
		double width = 0.375D;
		IIcon icon = BlockHopper.getHopperIcon("hopper_outside");
		renderer.setOverrideBlockTexture(icon);
		renderer.setRenderBounds(width, 0.0D, width, 1.0D - width, 0.25D, 1.0D - width);
		BlockRendererHelper.renderSimpleBlock(Blocks.hopper, 1, renderer);
		renderer.clearOverrideBlockTexture();

		OpenGLHelper.popMatrix();
	}
}