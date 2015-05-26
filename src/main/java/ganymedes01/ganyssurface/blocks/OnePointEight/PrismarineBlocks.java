package ganymedes01.ganyssurface.blocks.OnePointEight;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.blocks.BlockGeneric;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.material.Material;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class PrismarineBlocks extends BlockGeneric implements ISubBlocksBlock, IConfigurable {

	public PrismarineBlocks() {
		super(Material.rock, "rough", "bricks", "dark");
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockTextureName("prismarine");
		setBlockName(Utils.getUnlocalisedName("prismarineBlocks"));
		setCreativeTab(GanysSurface.enablePrismarineStuff ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePrismarineStuff;
	}
}