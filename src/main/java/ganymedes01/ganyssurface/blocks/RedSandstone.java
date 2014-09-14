package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemRedSandstoneBlocks;
import net.minecraft.block.BlockSandStone;
import net.minecraft.item.ItemBlock;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class RedSandstone extends BlockSandStone implements ISubBlocksBlock {

	public RedSandstone() {
		setHardness(0.8F);
		setBlockName(Utils.getUnlocalizedName("red_sandstone"));
		setBlockTextureName(Utils.getBlockTexture("red_sandstone"));
		setCreativeTab(GanysSurface.enableRedSandstone ? GanysSurface.surfaceTab : null);
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemRedSandstoneBlocks.class;
	}
}