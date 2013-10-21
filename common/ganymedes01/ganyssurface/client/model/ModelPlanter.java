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
public class ModelPlanter extends ModelBase {

	private ModelRenderer block, arm, arm1, arm2;

	public ModelPlanter() {
		block = new ModelRenderer(this, 0, 0);
		block.addBox(0.0F, 8.0F, 0.0F, 16, 8, 16);

		arm = new ModelRenderer(this, 0, 0);
		arm.addBox(4.0F, 8.0F, 4.0F, 8, 8, 8);
		arm1 = new ModelRenderer(this, 0, 0);
		arm1.addBox(5.0F, 8.0F, 5.0F, 6, 4, 6);
		arm.addChild(arm1);
		arm2 = new ModelRenderer(this, 0, 0);
		arm2.addBox(6.0F, 8.0F, 6.0F, 4, 4, 4);
		arm.addChild(arm2);
	}

	public void moveArm(float value) {
		arm.offsetY = value;
		arm1.offsetY = value;
		arm2.offsetY = value;
	}

	public void renderAll() {
		block.render(0.0625F);
		arm.render(0.0625F);
	}
}