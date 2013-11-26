package ganymedes01.ganyssurface.integration.bc;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.IBox;
import buildcraft.api.filler.IFillerPattern;
import buildcraft.api.filler.IPatternIterator;

public class FillerFillUndergroundCaves implements IFillerPattern {

	private static Icon icon;

	public static void setIcon(Icon icon) {
		FillerFillUndergroundCaves.icon = icon;
	}

	@Override
	public String getUniqueTag() {
		return Reference.MOD_ID + ":" + "fillUndergroundCaves";
	}

	@Override
	public Icon getIcon() {
		return FillerFillUndergroundCaves.icon;
	}

	@Override
	public String getDisplayName() {
		return "Fill Underground Caves";
	}

	@Override
	public IPatternIterator createPatternIterator(TileEntity tile, IBox box, ForgeDirection orientation) {
		return new PatternIterator(tile, box);
	}

	private boolean isBlockReplaceable(World world, int x, int y, int z) {
		int blockID = world.getBlockId(x, y, z);
		Block block = Block.blocksList[blockID];
		if (block == null)
			return true;
		return block.isBlockReplaceable(world, x, y, z);
	}

	private class PatternIterator implements IPatternIterator {

		private final IBox box;
		private final TileEntity tile;

		public PatternIterator(TileEntity tile, IBox box) {
			this.box = box;
			this.tile = tile;
		}

		@Override
		public boolean iteratePattern(ItemStack stackToPlace) {
			int xMin = (int) box.pMin().x;
			int yMin = 0;
			int zMin = (int) box.pMin().z;

			int xMax = (int) box.pMax().x;
			int yMax = (int) box.pMax().y;
			int zMax = (int) box.pMax().z;

			World world = tile.worldObj;

			for (int x = xMin; x <= xMax; x++)
				for (int z = zMin; z <= zMax; z++) {
					int maxY = yMax;
					while (isBlockReplaceable(world, x, maxY, z))
						maxY--;
					maxY--;
					for (int y = yMin; y <= maxY; y++)
						if (isBlockReplaceable(world, x, y, z)) {
							if (stackToPlace != null)
								stackToPlace.getItem().onItemUse(stackToPlace, Utils.getPlayer(world), world, x, y - 1, z, 1, 0.0f, 0.0f, 0.0f);
							return false;
						}
				}
			return true;
		}
	}
}