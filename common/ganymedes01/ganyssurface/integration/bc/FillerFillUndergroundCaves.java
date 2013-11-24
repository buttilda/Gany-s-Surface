package ganymedes01.ganyssurface.integration.bc;

import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import buildcraft.api.core.IBox;
import buildcraft.api.filler.IFillerPattern;

public class FillerFillUndergroundCaves implements IFillerPattern {

	private int id;
	private static Icon icon;

	public static void setIcon(Icon icon) {
		FillerFillUndergroundCaves.icon = icon;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean iteratePattern(TileEntity tile, IBox box, ItemStack stackToPlace) {
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

	private boolean isBlockReplaceable(World world, int x, int y, int z) {
		int blockID = world.getBlockId(x, y, z);
		Block block = Block.blocksList[blockID];
		if (block == null)
			return true;
		return block.isBlockReplaceable(world, x, y, z);
	}

	@Override
	public Icon getTexture() {
		return FillerFillUndergroundCaves.icon;
	}

	@Override
	public String getName() {
		return "Fill Underground Caves";
	}
}