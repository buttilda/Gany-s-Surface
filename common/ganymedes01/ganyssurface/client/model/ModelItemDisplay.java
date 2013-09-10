package ganymedes01.ganyssurface.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelItemDisplay extends ModelBase {

	private ModelRenderer block;

	public ModelItemDisplay() {
		block = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		block.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, -0.005F);
	}

	public void renderAll() {
		block.render(0.0625F);
	}
}
