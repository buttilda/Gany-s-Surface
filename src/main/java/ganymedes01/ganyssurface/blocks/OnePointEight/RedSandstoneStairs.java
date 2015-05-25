package ganymedes01.ganyssurface.blocks.OnePointEight;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.BlockStairs;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class RedSandstoneStairs extends BlockStairs implements IConfigurable {

	public RedSandstoneStairs() {
		super(ModBlocks.redSandstone, 0);
		setHardness(0.8F);
		setLightOpacity(0);
		setBlockName(Utils.getUnlocalisedName("red_sandstone_stairs"));
		setCreativeTab(GanysSurface.enableRedSandstone ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableRedSandstone;
	}
}