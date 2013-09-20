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
public class ModelItemDisplay extends ModelBase {

	private ModelRenderer block;

	public ModelItemDisplay() {
		block = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		block.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, -0.005F);
		ModelRenderer base = new ModelRenderer(this, 0, 32).setTextureSize(64, 64);
		base.addBox(0.0F, 15.0F, 0.0F, 16, 1, 16, -0.11F);
		block.addChild(base);
	}

	public void renderAll() {
		block.render(0.0625F);
	}
}
