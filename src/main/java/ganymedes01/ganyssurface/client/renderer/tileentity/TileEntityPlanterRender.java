package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.model.ModelPlanter;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
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
public class TileEntityPlanterRender extends TileEntitySpecialRenderer {

	private final ModelPlanter model = new ModelPlanter();
	private final ResourceLocation texture = Utils.getResource(Utils.getEntityTexture(Strings.PLANTER_NAME));

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		TileEntityPlanter planter = (TileEntityPlanter) tile;
		bindTexture(texture);

		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate((float) x + 1.0F, (float) y + 1.0F, (float) z + 0.0F);
		OpenGLHelper.scale(-1.0F, -1.0F, 1.0F);
		model.moveArm(planter.getArmExtension());
		model.renderAll();
		OpenGLHelper.popMatrix();
	}
}