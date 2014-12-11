package ganymedes01.ganyssurface.client.renderer.item;

import ganymedes01.ganyssurface.GlStateManager;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.model.ModelPocketBat;
import ganymedes01.ganyssurface.client.model.ModelPocketSquid;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
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
public class ItemPocketCritterRenderer implements IItemRenderer {

	private final ModelPocketBat bat = new ModelPocketBat();
	private final ModelPocketSquid squid = new ModelPocketSquid();

	private final ResourceLocation batTex = new ResourceLocation("textures/entity/bat.png");
	private final ResourceLocation squidTex = new ResourceLocation("textures/entity/squid.png");
	private final ResourceLocation squidCookedTex = Utils.getResource(Utils.getEntityTexture(Strings.ROASTED_SQUID_NAME));

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		switch (type) {
			case ENTITY:
				GlStateManager.scale(0.5F, 0.5F, 0.5F);
				renderCritter(stack, 0.5F, 1.0F, 0.0F, type);
				break;
			case EQUIPPED:
				renderCritter(stack, 1.0F, 1.0F, 0.5F, type);
				break;
			case EQUIPPED_FIRST_PERSON:
				GlStateManager.translate(0.75F, 0.5F, 0.5F);
				GlStateManager.rotate(-90 - 45, 0, 1, 0);
				renderCritter(stack, 1.0F, 1.0F, 1.0F, type);
				break;
			case INVENTORY:
				GlStateManager.scale(0.75F, 0.75F, 0.75F);
				renderCritter(stack, 0.0F, 0.075F, 0.0F, type);
				break;
			default:
				break;
		}
	}

	private void renderCritter(ItemStack stack, float x, float y, float z, ItemRenderType type) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(180, 1, 0, 0);
		GlStateManager.rotate(-90, 0, 1, 0);
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();

		if (type == ItemRenderType.ENTITY)
			GlStateManager.translate(0.0F, 0.5F, 0.5F);
		else if (type == ItemRenderType.INVENTORY)
			GlStateManager.translate(0.0F, -0.5F, 0.0F);

		if (isBat(stack)) {
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(batTex);
			bat.renderAll();
		} else if (isSquid(stack)) {
			GlStateManager.scale(0.75F, 0.75F, 0.75F);
			GlStateManager.translate(0.0F, -0.25F, 0.0F);
			if (stack.getItemDamage() == 0)
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(squidCookedTex);
			else
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(squidTex);
			squid.renderAll();
		}

		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}

	private boolean isSquid(ItemStack stack) {
		return stack.getItem() == ModItems.roastedSquid || stack.getItemDamage() == 1;
	}

	private boolean isBat(ItemStack stack) {
		return stack.getItem() == ModItems.pocketCritter && stack.getItemDamage() == 0;
	}
}