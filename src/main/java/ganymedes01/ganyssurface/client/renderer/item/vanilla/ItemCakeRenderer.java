package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.renderer.block.BlockRendererHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
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
public class ItemCakeRenderer implements IItemRenderer {

	private final Block cake;

	public ItemCakeRenderer(Block cake) {
		this.cake = cake;
	}

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
		Object renderer = data[0];
		if (renderer instanceof RenderBlocks)
			switch (type) {
				case ENTITY:
					if (cake != ModBlocks.chocolateCake)
						OpenGLHelper.scale(0.5, 0.5, 0.5);
					render(stack, 0, 0, 0, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					render(stack, 0.5F, 0.75F, 0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					render(stack, 0.5F, 0.75F, 0.5F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					OpenGLHelper.scale(1.1, 1.1, 1.1);
					render(stack, -0.5F, -0.3F, -0.5F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	private void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		int meta = stack.getItemDamage();
		float f = 0.0625F;
		float f1 = (1 + meta * 2) / 16.0F;
		float f2 = 0.5F;
		renderer.setRenderBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);

		BlockRendererHelper.renderSimpleBlock(cake, stack.getItemDamage(), renderer);

		OpenGLHelper.popMatrix();
	}
}