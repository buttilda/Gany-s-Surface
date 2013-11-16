package ganymedes01.ganyssurface.client.renderer.item;

import ganymedes01.ganyssurface.client.model.ModelDualWorkTable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class ItemDualWorkTableRenderer implements IItemRenderer {

	private ModelDualWorkTable model = new ModelDualWorkTable();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY: {
				renderPlanter(0.5F, 0.5F, 0.5F);
				break;
			}
			case EQUIPPED: {
				renderPlanter(1.0F, 1.0F, 1.0F);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderPlanter(1.0F, 1.0F, 1.0F);
				break;
			}
			case INVENTORY: {
				renderPlanter(0.0F, 0.075F, 0.0F);
				break;
			}
		}
	}

	private void renderPlanter(float x, float y, float z) {
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(Utils.getResource(Utils.getEntityTexture(Strings.DUAL_WORK_TABLE_NAME)));
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		model.renderAll(2);
		GL11.glPopMatrix();
	}
}