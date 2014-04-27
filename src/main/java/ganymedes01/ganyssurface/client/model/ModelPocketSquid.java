package ganymedes01.ganyssurface.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class ModelPocketSquid extends ModelBase {

	private final ModelRenderer squidBody;
	private final ModelRenderer[] squidTentacles = new ModelRenderer[8];

	public ModelPocketSquid() {
		squidBody = new ModelRenderer(this, 0, 0);
		squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
		squidBody.rotationPointY += 8;

		for (int i = 0; i < squidTentacles.length; i++) {
			squidTentacles[i] = new ModelRenderer(this, 48, 0);
			double d0 = i * Math.PI * 2.0D / squidTentacles.length;
			squidTentacles[i].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
			squidTentacles[i].rotationPointX = (float) Math.cos(d0) * 5.0F;
			squidTentacles[i].rotationPointZ = (float) Math.sin(d0) * 5.0F;
			squidTentacles[i].rotationPointY = 15;
			squidTentacles[i].rotateAngleY = (float) (i * Math.PI * -2.0D / squidTentacles.length + Math.PI / 2D);
		}
	}

	public void renderAll() {
		for (int i = 0; i < squidTentacles.length; i++)
			squidTentacles[i].rotateAngleX = 0;
		squidBody.render(1.0F / 16.0F);

		for (int i = 0; i < squidTentacles.length; ++i)
			squidTentacles[i].render(1.0F / 16.0F);
	}
}