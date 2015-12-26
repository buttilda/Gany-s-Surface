package ganymedes01.ganyssurface.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class ModelPlanter extends ModelBase {

	private final ModelRenderer arm, arm1, arm2;

	public ModelPlanter() {
		textureHeight = 36;
		textureWidth = 32;

		arm = new ModelRenderer(this, 0, 20);
		arm.addBox(4.0F, 8.0F, 4.0F, 8, 8, 8);
		arm1 = new ModelRenderer(this, 0, 8);
		arm1.addBox(5.0F, 8.0F, 5.0F, 6, 6, 6);
		arm2 = new ModelRenderer(this, 0, 0);
		arm2.addBox(6.0F, 8.0F, 6.0F, 4, 4, 4);
		arm1.addChild(arm2);
		arm.addChild(arm1);
	}

	public void moveArm(float value) {
		if (value > 0.5F) {
			arm.offsetY = 0.5F;
			arm1.offsetY = 0.5F;
		} else {
			arm.offsetY = value;
			arm1.offsetY = value;
		}

		if (value > 0.3F)
			arm2.offsetY = 0.3F;
		else
			arm2.offsetY = value;
	}

	public void renderAll() {
		if (arm.offsetY > 0.0F)
			arm.render(0.0625F);
	}
}