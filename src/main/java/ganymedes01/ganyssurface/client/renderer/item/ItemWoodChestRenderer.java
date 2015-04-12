package ganymedes01.ganyssurface.client.renderer.item;

import ganymedes01.ganyssurface.blocks.BlockWoodChest;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWoodChestRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
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
public class ItemWoodChestRenderer implements IItemRenderer {

	public static final ItemWoodChestRenderer INSTANCE = new ItemWoodChestRenderer();

	private final ModelChest model = new ModelChest();

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
		BlockWoodChest chest = (BlockWoodChest) Block.getBlockFromItem(stack.getItem());

		switch (type) {
			case ENTITY:
				renderChest(chest, 0.5F, 0.5F, 0.5F);
				break;
			case EQUIPPED:
				renderChest(chest, 1.0F, 1.0F, 1.0F);
				break;
			case EQUIPPED_FIRST_PERSON:
				renderChest(chest, 1.0F, 1.0F, 1.0F);
				break;
			case INVENTORY:
				renderChest(chest, 0.0F, 0.075F, 0.0F);
				break;
			default:
				break;
		}
	}

	private void renderChest(BlockWoodChest block, float x, float y, float z) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.translate(x, y, z);
		OpenGLHelper.rotate(180, 1, 0, 0);
		OpenGLHelper.rotate(-90, 0, 1, 0);

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TileEntityWoodChestRenderer.makeChestTexture(block.getType(), true));

		model.renderAll();

		OpenGLHelper.popMatrix();
	}
}