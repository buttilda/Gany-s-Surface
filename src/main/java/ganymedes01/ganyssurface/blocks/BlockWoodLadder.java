package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.BlockLadder;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockWoodLadder extends BlockLadder implements IConfigurable {

	public BlockWoodLadder(int meta) {
		setHardness(0.4F);
		setStepSound(soundTypeLadder);
		setBlockName(Utils.getUnlocalisedName("ladder" + meta));
		setBlockTextureName(Utils.getBlockTexture("ladder_" + BlockWoodDoor.names[meta]));
		setCreativeTab(GanysSurface.enableWoodenLadders ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWoodenLadders;
	}
}