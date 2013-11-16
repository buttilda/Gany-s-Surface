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
public class ModelDualWorkTable extends ModelBase {

	private final ModelRenderer block;
	private final ModelRenderer support;

	public ModelDualWorkTable() {
		block = new ModelRenderer(this, 0, 0).setTextureOffset(0, 0);
		block.addBox(-8.0F, 0.0F, -8.0F, 16, 5, 16);

		ModelRenderer base = new ModelRenderer(this, 0, 0).setTextureOffset(0, 0);
		base.addBox(-8.0F, 11.0F, -8.0F, 16, 5, 16);

		support = new ModelRenderer(this, 0, 19);
		support.addBox(-8.0F, 5.0F, -8.0F, 2, 6, 2);

		ModelRenderer support1 = new ModelRenderer(this, 0, 19);
		support1.addBox(14.0F - 8.0F, 5.0F, -8.0F, 2, 6, 2);

		ModelRenderer support2 = new ModelRenderer(this, 0, 19);
		support2.addBox(-8.0F, 5.0F, 14.0F - 8.0F, 2, 6, 2);

		ModelRenderer support3 = new ModelRenderer(this, 0, 19);
		support3.addBox(14.0F - 8.0F, 5.0F, 14.0F - 8.0F, 2, 6, 2);

		support.addChild(support1);
		support.addChild(support2);
		support.addChild(support3);
		block.addChild(base);
	}

	public void renderAll(int metadata) {
		double rotation = 0;
		switch (metadata) {
			case 2:
				rotation = 2 * Math.PI;
				break;
			case 3:
				rotation = -Math.PI;
				break;
			case 4:
				rotation = 3 * Math.PI / 2;
				break;
			case 5:
				rotation = Math.PI / 2;
				break;
		}

		block.setRotationPoint(8.0F, 0.0F, 8.0F);
		support.setRotationPoint(8.0F, 0.0F, 8.0F);
		block.rotateAngleY = (float) rotation;
		support.rotateAngleY = (float) rotation;

		block.render(0.0625F);
		support.render(0.0625F);
	}
}