package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.blocks.BlockWoodSign;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWoodSignRenderer;
import ganymedes01.ganyssurface.items.block.ItemWoodSign;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.RenderBlocks;
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
public class ItemSignRenderer implements IItemRenderer {

	public static final ItemSignRenderer INSTANCE = new ItemSignRenderer();

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sign.png");
	private final ModelSign model = new ModelSign();

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
					if (!(stack.getItem() instanceof ItemWoodSign))
						OpenGLHelper.scale(0.5, 0.5, 0.5);
					render(stack, 0.0F, 0.0F, 0.0F, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					render(stack, 0.5F, 0.75F, 0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					render(stack, -0.5F, 1.0F, 0.7F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					render(stack, -0.5F, -0.45F, -0.5F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	private void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);

		OpenGLHelper.rotate(90, 0, 1, 0);
		OpenGLHelper.scale(0.75, 0.75, 0.75);
		OpenGLHelper.scale(-1, -1, 1);
		if (stack.getItem() instanceof ItemWoodSign) {
			int meta = ((BlockWoodSign) Block.getBlockFromItem(stack.getItem())).woodMeta - 1;
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(TileEntityWoodSignRenderer.textures[meta]);
		} else
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);

		model.renderSign();

		OpenGLHelper.popMatrix();
	}
}