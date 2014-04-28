package ganymedes01.ganyssurface.client.renderer.block;

import ganymedes01.ganyssurface.blocks.ColouredRedstone;
import ganymedes01.ganyssurface.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class BlockColouredRedstoneRender implements ISimpleBlockRenderingHandler {

	private final RenderBlocks renderer = new RenderBlocks();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	private float getColourFromMeta(int meta) {
		return 0.5F + meta / 30.0F;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if (!(block instanceof ColouredRedstone))
			return true;

		ColouredRedstone wire = (ColouredRedstone) block;

		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);
		IIcon cross = wire.cross;
		IIcon line = wire.line;
		IIcon cross_overlay = wire.cross_overlay;
		IIcon line_overlay = wire.line_overlay;
		tessellator.setBrightness(wire.getMixedBrightnessForBlock(world, x, y, z));

		int colour = wire.colorMultiplier(world, x, y, z);
		float R = (colour >> 16 & 255) / 255.0F;
		float G = (colour >> 8 & 255) / 255.0F;
		float B = (colour & 255) / 255.0F;

		float r = R * getColourFromMeta(meta);
		float g = G * getColourFromMeta(meta);
		float b = B * getColourFromMeta(meta);

		tessellator.setColorOpaque_F(r, g, b);
		boolean flag = ColouredRedstone.isPowerProviderOrWire(world, x - 1, y, z, 1, wire) || !world.isBlockNormalCube(x - 1, y, z) && ColouredRedstone.isPowerProviderOrWire(world, x - 1, y - 1, z, -1, wire);
		boolean flag1 = ColouredRedstone.isPowerProviderOrWire(world, x + 1, y, z, 3, wire) || !world.isBlockNormalCube(x + 1, y, z) && ColouredRedstone.isPowerProviderOrWire(world, x + 1, y - 1, z, -1, wire);
		boolean flag2 = ColouredRedstone.isPowerProviderOrWire(world, x, y, z - 1, 2, wire) || !world.isBlockNormalCube(x, y, z - 1) && ColouredRedstone.isPowerProviderOrWire(world, x, y - 1, z - 1, -1, wire);
		boolean flag3 = ColouredRedstone.isPowerProviderOrWire(world, x, y, z + 1, 0, wire) || !world.isBlockNormalCube(x, y, z + 1) && ColouredRedstone.isPowerProviderOrWire(world, x, y - 1, z + 1, -1, wire);

		if (!world.isBlockNormalCube(x, y + 1, z)) {
			if (world.isBlockNormalCube(x - 1, y, z) && ColouredRedstone.isPowerProviderOrWire(world, x - 1, y + 1, z, -1, wire))
				flag = true;
			if (world.isBlockNormalCube(x + 1, y, z) && ColouredRedstone.isPowerProviderOrWire(world, x + 1, y + 1, z, -1, wire))
				flag1 = true;
			if (world.isBlockNormalCube(x, y, z - 1) && ColouredRedstone.isPowerProviderOrWire(world, x, y + 1, z - 1, -1, wire))
				flag2 = true;
			if (world.isBlockNormalCube(x, y, z + 1) && ColouredRedstone.isPowerProviderOrWire(world, x, y + 1, z + 1, -1, wire))
				flag3 = true;
		}

		float f5 = x + 0;
		float f6 = x + 1;
		float f7 = z + 0;
		float f8 = z + 1;
		int i1 = 0;

		if ((flag || flag1) && !flag2 && !flag3)
			i1 = 1;

		if ((flag2 || flag3) && !flag1 && !flag)
			i1 = 2;

		if (i1 == 0) {
			int j1 = 0;
			int k1 = 0;
			int l1 = 16;
			int i2 = 16;
			boolean flag4 = true;

			if (!flag) {
				f5 += 0.3125F;
				j1 += 5;
			}
			if (!flag1) {
				f6 -= 0.3125F;
				l1 -= 5;
			}
			if (!flag2) {
				f7 += 0.3125F;
				k1 += 5;
			}
			if (!flag3) {
				f8 -= 0.3125F;
				i2 -= 5;
			}

			tessellator.addVertexWithUV(f6, y + 0.015625D, f8, cross.getInterpolatedU(l1), cross.getInterpolatedV(i2));
			tessellator.addVertexWithUV(f6, y + 0.015625D, f7, cross.getInterpolatedU(l1), cross.getInterpolatedV(k1));
			tessellator.addVertexWithUV(f5, y + 0.015625D, f7, cross.getInterpolatedU(j1), cross.getInterpolatedV(k1));
			tessellator.addVertexWithUV(f5, y + 0.015625D, f8, cross.getInterpolatedU(j1), cross.getInterpolatedV(i2));
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			tessellator.addVertexWithUV(f6, y + 0.015625D, f8, cross_overlay.getInterpolatedU(l1), cross_overlay.getInterpolatedV(i2));
			tessellator.addVertexWithUV(f6, y + 0.015625D, f7, cross_overlay.getInterpolatedU(l1), cross_overlay.getInterpolatedV(k1));
			tessellator.addVertexWithUV(f5, y + 0.015625D, f7, cross_overlay.getInterpolatedU(j1), cross_overlay.getInterpolatedV(k1));
			tessellator.addVertexWithUV(f5, y + 0.015625D, f8, cross_overlay.getInterpolatedU(j1), cross_overlay.getInterpolatedV(i2));
		} else if (i1 == 1) {
			tessellator.addVertexWithUV(f6, y + 0.015625D, f8, line.getMaxU(), line.getMaxV());
			tessellator.addVertexWithUV(f6, y + 0.015625D, f7, line.getMaxU(), line.getMinV());
			tessellator.addVertexWithUV(f5, y + 0.015625D, f7, line.getMinU(), line.getMinV());
			tessellator.addVertexWithUV(f5, y + 0.015625D, f8, line.getMinU(), line.getMaxV());
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			tessellator.addVertexWithUV(f6, y + 0.015625D, f8, line_overlay.getMaxU(), line_overlay.getMaxV());
			tessellator.addVertexWithUV(f6, y + 0.015625D, f7, line_overlay.getMaxU(), line_overlay.getMinV());
			tessellator.addVertexWithUV(f5, y + 0.015625D, f7, line_overlay.getMinU(), line_overlay.getMinV());
			tessellator.addVertexWithUV(f5, y + 0.015625D, f8, line_overlay.getMinU(), line_overlay.getMaxV());
		} else {
			tessellator.addVertexWithUV(f6, y + 0.015625D, f8, line.getMaxU(), line.getMaxV());
			tessellator.addVertexWithUV(f6, y + 0.015625D, f7, line.getMinU(), line.getMaxV());
			tessellator.addVertexWithUV(f5, y + 0.015625D, f7, line.getMinU(), line.getMinV());
			tessellator.addVertexWithUV(f5, y + 0.015625D, f8, line.getMaxU(), line.getMinV());
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			tessellator.addVertexWithUV(f6, y + 0.015625D, f8, line_overlay.getMaxU(), line_overlay.getMaxV());
			tessellator.addVertexWithUV(f6, y + 0.015625D, f7, line_overlay.getMinU(), line_overlay.getMaxV());
			tessellator.addVertexWithUV(f5, y + 0.015625D, f7, line_overlay.getMinU(), line_overlay.getMinV());
			tessellator.addVertexWithUV(f5, y + 0.015625D, f8, line_overlay.getMaxU(), line_overlay.getMinV());
		}

		if (!world.isBlockNormalCube(x, y + 1, z)) {
			float f9 = 0.021875F;

			if (world.isBlockNormalCube(x - 1, y, z) && world.getBlockId(x - 1, y + 1, z) == wire) {
				tessellator.setColorOpaque_F(r, g, b);
				tessellator.addVertexWithUV(x + 0.015625D, y + 1 + 0.021875F, z + 1, line.getMaxU(), line.getMinV());
				tessellator.addVertexWithUV(x + 0.015625D, y + 0, z + 1, line.getMinU(), line.getMinV());
				tessellator.addVertexWithUV(x + 0.015625D, y + 0, z + 0, line.getMinU(), line.getMaxV());
				tessellator.addVertexWithUV(x + 0.015625D, y + 1 + 0.021875F, z + 0, line.getMaxU(), line.getMaxV());
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				tessellator.addVertexWithUV(x + 0.015625D, y + 1 + 0.021875F, z + 1, line_overlay.getMaxU(), line_overlay.getMinV());
				tessellator.addVertexWithUV(x + 0.015625D, y + 0, z + 1, line_overlay.getMinU(), line_overlay.getMinV());
				tessellator.addVertexWithUV(x + 0.015625D, y + 0, z + 0, line_overlay.getMinU(), line_overlay.getMaxV());
				tessellator.addVertexWithUV(x + 0.015625D, y + 1 + 0.021875F, z + 0, line_overlay.getMaxU(), line_overlay.getMaxV());
			}

			if (world.isBlockNormalCube(x + 1, y, z) && world.getBlockId(x + 1, y + 1, z) == wire) {
				tessellator.setColorOpaque_F(r, g, b);
				tessellator.addVertexWithUV(x + 1 - 0.015625D, y + 0, z + 1, line.getMinU(), line.getMaxV());
				tessellator.addVertexWithUV(x + 1 - 0.015625D, y + 1 + 0.021875F, z + 1, line.getMaxU(), line.getMaxV());
				tessellator.addVertexWithUV(x + 1 - 0.015625D, y + 1 + 0.021875F, z + 0, line.getMaxU(), line.getMinV());
				tessellator.addVertexWithUV(x + 1 - 0.015625D, y + 0, z + 0, line.getMinU(), line.getMinV());
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				tessellator.addVertexWithUV(x + 1 - 0.015625D, y + 0, z + 1, line_overlay.getMinU(), line_overlay.getMaxV());
				tessellator.addVertexWithUV(x + 1 - 0.015625D, y + 1 + 0.021875F, z + 1, line_overlay.getMaxU(), line_overlay.getMaxV());
				tessellator.addVertexWithUV(x + 1 - 0.015625D, y + 1 + 0.021875F, z + 0, line_overlay.getMaxU(), line_overlay.getMinV());
				tessellator.addVertexWithUV(x + 1 - 0.015625D, y + 0, z + 0, line_overlay.getMinU(), line_overlay.getMinV());
			}

			if (world.isBlockNormalCube(x, y, z - 1) && world.getBlockId(x, y + 1, z - 1) == wire) {
				tessellator.setColorOpaque_F(r, g, b);
				tessellator.addVertexWithUV(x + 1, y + 0, z + 0.015625D, line.getMinU(), line.getMaxV());
				tessellator.addVertexWithUV(x + 1, y + 1 + 0.021875F, z + 0.015625D, line.getMaxU(), line.getMaxV());
				tessellator.addVertexWithUV(x + 0, y + 1 + 0.021875F, z + 0.015625D, line.getMaxU(), line.getMinV());
				tessellator.addVertexWithUV(x + 0, y + 0, z + 0.015625D, line.getMinU(), line.getMinV());
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				tessellator.addVertexWithUV(x + 1, y + 0, z + 0.015625D, line_overlay.getMinU(), line_overlay.getMaxV());
				tessellator.addVertexWithUV(x + 1, y + 1 + 0.021875F, z + 0.015625D, line_overlay.getMaxU(), line_overlay.getMaxV());
				tessellator.addVertexWithUV(x + 0, y + 1 + 0.021875F, z + 0.015625D, line_overlay.getMaxU(), line_overlay.getMinV());
				tessellator.addVertexWithUV(x + 0, y + 0, z + 0.015625D, line_overlay.getMinU(), line_overlay.getMinV());
			}

			if (world.isBlockNormalCube(x, y, z + 1) && world.getBlock(x, y + 1, z + 1) == wire) {
				tessellator.setColorOpaque_F(r, g, b);
				tessellator.addVertexWithUV(x + 1, y + 1 + 0.021875F, z + 1 - 0.015625D, line.getMaxU(), line.getMinV());
				tessellator.addVertexWithUV(x + 1, y + 0, z + 1 - 0.015625D, line.getMinU(), line.getMinV());
				tessellator.addVertexWithUV(x + 0, y + 0, z + 1 - 0.015625D, line.getMinU(), line.getMaxV());
				tessellator.addVertexWithUV(x + 0, y + 1 + 0.021875F, z + 1 - 0.015625D, line.getMaxU(), line.getMaxV());
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				tessellator.addVertexWithUV(x + 1, y + 1 + 0.021875F, z + 1 - 0.015625D, line_overlay.getMaxU(), line_overlay.getMinV());
				tessellator.addVertexWithUV(x + 1, y + 0, z + 1 - 0.015625D, line_overlay.getMinU(), line_overlay.getMinV());
				tessellator.addVertexWithUV(x + 0, y + 0, z + 1 - 0.015625D, line_overlay.getMinU(), line_overlay.getMaxV());
				tessellator.addVertexWithUV(x + 0, y + 1 + 0.021875F, z + 1 - 0.015625D, line_overlay.getMaxU(), line_overlay.getMaxV());
			}
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.COLOURED_REDSTONE;
	}
}