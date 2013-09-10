package ganymedes01.ganyssurface.client.renderer.item;

import ganymedes01.ganyssurface.client.model.ModelChestPropellant;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemChestPropellantRender implements IItemRenderer {

	private ModelChestPropellant model;

	public ItemChestPropellantRender() {
		model = new ModelChestPropellant();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY: {
				renderChestPropellant(0.5F, 0.5F, 0.5F);
				break;
			}
			case EQUIPPED: {
				renderChestPropellant(1.0F, 1.0F, 1.0F);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderChestPropellant(1.0F, 1.0F, 1.0F);
				break;
			}
			case INVENTORY: {
				renderChestPropellant(0.0F, 0.075F, 0.0F);
				break;
			}
			default:
				break;
		}
	}

	private void renderChestPropellant(float x, float y, float z) {
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, Utils.getEntityItemTexture(Strings.CHEST_PROPELLANT_NAME)));
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
