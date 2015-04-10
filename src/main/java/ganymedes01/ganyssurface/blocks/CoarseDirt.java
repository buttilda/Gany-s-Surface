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

public class CoarseDirt extends Block {

	public CoarseDirt() {
		super(Material.ground);
		setHardness(0.5F);
		setStepSound(soundTypeGravel);
		setBlockTextureName(Strings.COARSE_DIRT);
		setBlockName(Utils.getUnlocalisedName(Strings.COARSE_DIRT));
		setCreativeTab(GanysSurface.enableCoarseDirt ? GanysSurface.surfaceTab : null);
	}
}