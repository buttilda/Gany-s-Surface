package ganymedes01.ganyssurface.client.renderer.item;

import ganymedes01.ganyssurface.items.StorageCase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
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
public class ItemStorageCaseRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		GL11.glPushMatrix();
		double offset = -0.5;
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			offset = 0.0;
		else if (type == ItemRenderType.ENTITY)
			GL11.glScalef(0.5F, 0.5F, 0.5F);

		renderItemAsBlock((RenderBlocks) data[0], stack, offset, offset, offset, 0.0);
		GL11.glPopMatrix();

		if (!stack.hasTagCompound())
			return;

		GL11.glPushMatrix();
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			offset = 0.5;
		else if (type == ItemRenderType.ENTITY)
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		ItemStack s = StorageCase.getStoredStack(stack);

		int count = 0;
		int max = StorageCase.getStorageception(stack);
		while (count < max - 1) {
			s = StorageCase.getStoredStack(s);
			count++;
		}

		renderItemAsBlock((RenderBlocks) data[0], s, offset, offset, offset, 0.50005);
		GL11.glPopMatrix();
	}

	public static void renderItemAsBlock(RenderBlocks renderer, ItemStack stack, double x, double y, double z, double scale) {
		if (scale > 0)
			GL11.glScaled(scale, scale, scale);
		Tessellator tessellator = Tessellator.instance;

		Icon[] icons = new Icon[6];
		if (stack.getItem() instanceof ItemBlock) {
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
			Block block = Block.blocksList[stack.itemID];
			for (int i = 0; i < 6; i++)
				icons[i] = block.getIcon(i, stack.getItemDamage());
		} else
			for (int i = 0; i < 6; i++)
				icons[i] = stack.getIconIndex();

		renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
		GL11.glTranslated(x, y, z);
		tessellator.startDrawingQuads();
		if (icons[0] != null) {
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(null, 0.0, -scale, 0.0, icons[0]);
		}
		if (icons[1] != null) {
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(null, 0.0, scale, 0.0, icons[1]);
		}
		if (icons[2] != null) {
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(null, 0.0, 0.0, -scale, icons[2]);
		}
		if (icons[3] != null) {
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(null, 0.0, 0.0, scale, icons[3]);
		}
		if (icons[4] != null) {
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(null, -scale, 0.0, 0.0, icons[4]);
		}
		if (icons[5] != null) {
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(null, scale, 0.0, 0.0, icons[5]);
		}
		tessellator.draw();
	}
}