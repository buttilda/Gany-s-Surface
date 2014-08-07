package ganymedes01.ganyssurface.client.renderer.item;

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
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.5F, 0F, 0F);
				if (stack.isOnItemFrame())
					GL11.glTranslatef(0F, -0.3F, 0.01F);
				renderPickaxe(stack);
				GL11.glPopMatrix();
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

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

		if (stack.hasEffect(0)) {
			GL11.glPushMatrix();
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			Minecraft.getMinecraft().getTextureManager().bindTexture(GLINT);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
			GL11.glColor4f(0.5F * 0.76F, 0.25F * 0.76F, 0.8F * 0.76F, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);

			GL11.glPushMatrix();
			GL11.glScalef(0.125F, 0.125F, 0.125F);
			GL11.glTranslatef(Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glScalef(0.125F, 0.125F, 0.125F);
			GL11.glTranslatef(-(Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F), 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glPopMatrix();
		}

		GL11.glColor4f(1F, 1F, 1F, 1F);
	}
}