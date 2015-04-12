package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.blocks.BlockWoodDoor;
import ganymedes01.ganyssurface.blocks.BlockWoodSign;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.tileentities.TileEntityWoodSign;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
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

		OpenGLHelper.pushMatrix();
		float f1 = 0.6666667F;
		float f3;

		if (sign.isStanding) {
			OpenGLHelper.translate((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
			float f2 = sign.getBlockMetadata() * 360 / 16.0F;
			OpenGLHelper.rotate(-f2, 0.0F, 1.0F, 0.0F);
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

			OpenGLHelper.translate((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
			OpenGLHelper.rotate(-f3, 0.0F, 1.0F, 0.0F);
			OpenGLHelper.translate(0.0F, -0.3125F, -0.4375F);
			model.signStick.showModel = false;
		}

		bindTexture(textures[block.woodMeta - 1]);

		OpenGLHelper.pushMatrix();
		OpenGLHelper.scale(f1, -f1, -f1);
		model.renderSign();
		OpenGLHelper.popMatrix();

		FontRenderer fontrenderer = func_147498_b();
		f3 = 0.016666668F * f1;
		OpenGLHelper.translate(0.0F, 0.5F * f1, 0.07F * f1);
		OpenGLHelper.scale(f3, -f3, f3);
		OpenGLHelper.normal(0.0F, 0.0F, -1.0F * f3);
		OpenGLHelper.depthMask(false);

		int textColour = sign.getTextColour();
		for (int i = 0; i < sign.signText.length; i++) {
			String s = sign.signText[i];

			if (i == sign.lineBeingEdited) {
				s = "> " + s + " <";
				fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, i * 10 - sign.signText.length * 5, textColour, sign.textHasShadow());
			} else
				fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, i * 10 - sign.signText.length * 5, textColour, sign.textHasShadow());
		}

		OpenGLHelper.depthMask(true);
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		OpenGLHelper.popMatrix();
	}
}