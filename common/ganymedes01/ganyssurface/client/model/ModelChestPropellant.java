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
public class ModelChestPropellant extends ModelBase {

	private ModelRenderer block;

	public ModelChestPropellant() {
		block = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		block.addBox(0.0F, 0.0F, 0.0F, 15, 16, 15, 0.0F);
	}

	public void renderAll() {
		block.render(0.0625F);
	}
}
