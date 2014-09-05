package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.client.renderer.block.BlockRendererHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneRepeater;
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
					GL11.glScaled(0.5, 0.5, 0.5);
					render(stack, -0.5F, -0.5F, -0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					render(stack, 0.0F, 0.5F, 0.0F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					render(stack, 0.0F, 0.5F, 0.0F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					GL11.glScaled(1.1, 1.1, 1.1);
					render(stack, -0.5F, -0.25F, -0.5F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	protected void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		Tessellator tessellator = Tessellator.instance;

		double torchHeight = -0.1875;
		double torchOffset = BlockRedstoneRepeater.repeaterTorchOffset[0];
		double torch2Offset = -0.3125;

		GL11.glDisable(GL11.GL_LIGHTING);
		renderTorch(Blocks.unpowered_repeater, 0, torchHeight, torchOffset, renderer, 0.2);
		renderTorch(Blocks.unpowered_repeater, 0, torchHeight, torch2Offset, renderer, 0.2);
		GL11.glEnable(GL11.GL_LIGHTING);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0, -1, 0);
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderFaceYNeg(Blocks.unpowered_repeater, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(Blocks.stone_slab, 0, 0));
		tessellator.draw();

		GL11.glTranslated(0.5, 0.5, 0.5);
		renderer.setRenderBounds(0, 0.001, 0, 1, 0.125, 1);
		BlockRendererHelper.renderSimpleBlock(Blocks.unpowered_repeater, 0, renderer);

		GL11.glPopMatrix();
	}

	protected void renderTorch(Block block, double x, double y, double z, RenderBlocks renderer, double size) {
		Tessellator tessellator = Tessellator.instance;
		IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 0, 0);

		if (renderer.hasOverrideBlockTexture())
			icon = renderer.overrideBlockTexture;

		float f = 0.05F;
		tessellator.startDrawingQuads();
		renderer.setRenderBounds(0.5F - f, 0.0F, 0.4F - 0.04 * 2, 0.5F + f, 0.6F, 0.4F + 0.04 * 2);
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y, z + f + f / 2, icon);
		renderer.setRenderBounds(0.5F - f, size, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, x, y, z, icon);
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, x, y, z, icon);
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x, y, z, icon);
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x, y, z, icon);
		tessellator.draw();
	}
}