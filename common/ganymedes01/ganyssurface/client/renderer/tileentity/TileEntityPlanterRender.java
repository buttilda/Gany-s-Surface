package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.client.model.ModelPlanter;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

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
public class TileEntityPlanterRender extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float angle) {
		TileEntityPlanter planter = (TileEntityPlanter) tile;
		ModelPlanter model = new ModelPlanter();
		bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.PLANTER_NAME)));

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 1.0F, (float) y + 1.0F, (float) z + 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		model.moveArm(planter.getArmExtension());
		model.renderAll();
		GL11.glPopMatrix();
	}
}