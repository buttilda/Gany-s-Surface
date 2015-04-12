package ganymedes01.ganyssurface.client.renderer.item;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

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
public class ItemIcyPickaxeRenderer implements IItemRenderer {

	private static final ResourceLocation GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		switch (type) {
			case ENTITY:
				OpenGLHelper.pushMatrix();
				OpenGLHelper.translate(-0.5F, 0F, 0F);
				if (stack.isOnItemFrame())
					OpenGLHelper.translate(0F, -0.3F, 0.01F);
				renderPickaxe(stack);
				OpenGLHelper.popMatrix();
				break;
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
				renderPickaxe(stack);
				break;
			default:
				break;
		}
	}

	private void renderPickaxe(ItemStack stack) {
		IIcon icon = stack.getItem().getIcon(stack, 0);

		OpenGLHelper.pushMatrix();
		OpenGLHelper.enableBlend();
		OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OpenGLHelper.colour(1F, 1F, 1F, 1F);
		ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
		OpenGLHelper.disableBlend();
		OpenGLHelper.popMatrix();

		if (stack.hasEffect(0)) {
			OpenGLHelper.pushMatrix();
			GL11.glDepthFunc(GL11.GL_EQUAL);
			OpenGLHelper.disableLighting();
			Minecraft.getMinecraft().getTextureManager().bindTexture(GLINT);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
			OpenGLHelper.colour(0.5F * 0.76F, 0.25F * 0.76F, 0.8F * 0.76F, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);

			OpenGLHelper.pushMatrix();
			OpenGLHelper.scale(0.125F, 0.125F, 0.125F);
			OpenGLHelper.translate(Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F, 0.0F, 0.0F);
			OpenGLHelper.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			OpenGLHelper.popMatrix();

			OpenGLHelper.pushMatrix();
			OpenGLHelper.scale(0.125F, 0.125F, 0.125F);
			OpenGLHelper.translate(-(Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F), 0.0F, 0.0F);
			OpenGLHelper.rotate(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			OpenGLHelper.popMatrix();

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			OpenGLHelper.enableLighting();
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			OpenGLHelper.popMatrix();
		}

		OpenGLHelper.colour(1F, 1F, 1F, 1F);
	}
}