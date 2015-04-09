package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class FlintBlock extends Block {

	public FlintBlock() {
		super(Material.rock);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(soundTypeStone);
		setBlockName(Utils.getUnlocalisedName(Strings.FLINT_BLOCK));
		setBlockTextureName(Utils.getBlockTexture(Strings.FLINT_BLOCK));
		setCreativeTab(GanysSurface.enableBlockOfFlint ? GanysSurface.surfaceTab : null);
	}
}