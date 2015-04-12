package ganymedes01.ganyssurface.client.renderer.item;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.items.StorageCase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class ItemStorageCaseRenderer implements IItemRenderer {

	protected static RenderItem itemRender = new RenderItem();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		// Render crate
		OpenGLHelper.pushMatrix();
		double offset = -0.5;
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			offset = 0;
		else if (type == ItemRenderType.ENTITY)
			OpenGLHelper.scale(0.5F, 0.5F, 0.5F);

		renderItemAsBlock((RenderBlocks) data[0], stack, offset, offset, offset);
		OpenGLHelper.popMatrix();

		if (!stack.hasTagCompound())
			return;

		// Render stored item on the crate's faces
		offset = 0;
		OpenGLHelper.pushMatrix();
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			OpenGLHelper.translate(0.5, 0.5, 0.5);
			offset = 0.001;
		} else if (type == ItemRenderType.ENTITY) {
			OpenGLHelper.scale(0.5F, 0.5F, 0.5F);
			offset = 0.001;
		} else if (type == ItemRenderType.INVENTORY) {

		}

		ItemStack s = StorageCase.getStoredStack(stack);

		int count = 0;
		int max = StorageCase.getStorageception(stack);
		while (count < max - 1) {
			s = StorageCase.getStoredStack(s);
			count++;
		}

		if (s == null)
			return;

		renderStacksOnBlock(type, s, offset);

		OpenGLHelper.popMatrix();
	}

	private void renderStacksOnBlock(ItemRenderType type, ItemStack stack, double offset) {
		if (type != ItemRenderType.INVENTORY) {
			OpenGLHelper.pushMatrix();
			OpenGLHelper.translate(-offset, -offset, -offset);
			OpenGLHelper.translate(0.25, 0.25, -0.5);
			renderItemStack(stack);
			OpenGLHelper.popMatrix();
		}

		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(offset, offset, offset);
		OpenGLHelper.rotate(180, 0, 1, 0);
		OpenGLHelper.translate(0.25, 0.25, -0.5);
		renderItemStack(stack);
		OpenGLHelper.popMatrix();

		if (type != ItemRenderType.INVENTORY) {
			OpenGLHelper.pushMatrix();
			OpenGLHelper.translate(-offset, -offset, -offset);
			OpenGLHelper.rotate(90, 0, 1, 0);
			OpenGLHelper.translate(0.25, 0.25, -0.5);
			renderItemStack(stack);
			OpenGLHelper.popMatrix();
		}

		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(offset, offset, offset);
		OpenGLHelper.rotate(270, 0, 1, 0);
		OpenGLHelper.translate(0.25, 0.25, -0.5);
		renderItemStack(stack);
		OpenGLHelper.popMatrix();

		if (type != ItemRenderType.INVENTORY) {
			OpenGLHelper.pushMatrix();
			OpenGLHelper.translate(-offset, -offset, -offset);
			OpenGLHelper.rotate(270, 1, 0, 0);
			OpenGLHelper.translate(0.25, 0.25, -0.5);
			renderItemStack(stack);
			OpenGLHelper.popMatrix();
		}

		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(offset, offset, offset);
		OpenGLHelper.rotate(90, 1, 0, 0);
		OpenGLHelper.translate(0.25, 0.25, -0.5);
		renderItemStack(stack);
		OpenGLHelper.popMatrix();
	}

	private void renderItemStack(ItemStack stack) {
		if (stack != null) {
			FontRenderer font = stack.getItem().getFontRenderer(stack);
			OpenGLHelper.rotate(180, 0, 0, 1);
			OpenGLHelper.scale(1F / 16, 1F / 16, 1F / 16);

			OpenGLHelper.pushMatrix();
			OpenGLHelper.scale(1, 1, -0.02F);
			OpenGLHelper.scale(0.5, 0.5, 0.5);
			if (!ForgeHooksClient.renderInventoryItem(new RenderBlocks(), Minecraft.getMinecraft().getTextureManager(), stack, true, 0.0f, 0.0f, 0.0f))
				itemRender.renderItemIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), stack, 0, 0);

			OpenGLHelper.popMatrix();
		}
	}

	public static void renderItemAsBlock(RenderBlocks renderer, ItemStack stack, double x, double y, double z) {
		Tessellator tessellator = Tessellator.instance;

		int colour = stack.getItem().getColorFromItemStack(stack, 0);
		IIcon icon = stack.getIconIndex();

		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		OpenGLHelper.translate(x, y, z);

		float R = (colour >> 16 & 255) / 255.0F;
		float G = (colour >> 8 & 255) / 255.0F;
		float B = (colour & 255) / 255.0F;
		OpenGLHelper.colour(R, G, B);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0, -1, 0);
		renderer.renderFaceYNeg(null, 0, 0, 0, icon);

		tessellator.setNormal(0, 1, 0);
		renderer.renderFaceYPos(null, 0, 0, 0, icon);

		tessellator.setNormal(0, 0, -1);
		renderer.renderFaceZNeg(null, 0, 0, 0, icon);

		tessellator.setNormal(0, 0, 1);
		renderer.renderFaceZPos(null, 0, 0, 0, icon);

		tessellator.setNormal(-1, 0, 0);
		renderer.renderFaceXNeg(null, 0, 0, 0, icon);

		tessellator.setNormal(1, 0, 0);
		renderer.renderFaceXPos(null, 0, 0, 0, icon);

		tessellator.draw();
	}
}