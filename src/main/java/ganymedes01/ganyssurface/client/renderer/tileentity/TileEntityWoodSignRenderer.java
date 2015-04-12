package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.blocks.BlockWoodDoor;
import ganymedes01.ganyssurface.blocks.BlockWoodSign;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.tileentities.TileEntityWoodSign;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

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
public class TileEntityWoodSignRenderer extends TileEntitySpecialRenderer {

	public static final ResourceLocation[] textures = new ResourceLocation[ModBlocks.signs.length];
	private final ModelSign model = new ModelSign();

	public TileEntityWoodSignRenderer() {
		for (int i = 0; i < textures.length; i++)
			textures[i] = Utils.getResource(Utils.getEntityTexture("sign_" + BlockWoodDoor.names[i + 1]));
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
		TileEntityWoodSign sign = (TileEntityWoodSign) tile;
		BlockWoodSign block = (BlockWoodSign) sign.getBlockType();

		GL11.glPushMatrix();
		float f1 = 0.6666667F;
		float f3;

		if (sign.isStanding) {
			GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
			float f2 = sign.getBlockMetadata() * 360 / 16.0F;
			GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
			model.signStick.showModel = true;
		} else {
			int j = sign.getBlockMetadata();
			f3 = 0.0F;

			if (j == 2)
				f3 = 180.0F;
			if (j == 4)
				f3 = 90.0F;
			if (j == 5)
				f3 = -90.0F;

			GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
			GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
			model.signStick.showModel = false;
		}

		bindTexture(textures[block.woodMeta - 1]);
		GL11.glPushMatrix();
		GL11.glScalef(f1, -f1, -f1);
		model.renderSign();
		GL11.glPopMatrix();
		FontRenderer fontrenderer = func_147498_b();
		f3 = 0.016666668F * f1;
		GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
		GL11.glScalef(f3, -f3, f3);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
		GL11.glDepthMask(false);
		byte b0 = 0;

		for (int i = 0; i < sign.signText.length; ++i) {
			String s = sign.signText[i];

			if (i == sign.lineBeingEdited) {
				s = "> " + s + " <";
				fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, i * 10 - sign.signText.length * 5, b0);
			} else
				fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, i * 10 - sign.signText.length * 5, b0);
		}

		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
}