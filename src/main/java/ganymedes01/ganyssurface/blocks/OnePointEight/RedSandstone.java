package ganymedes01.ganyssurface.blocks.OnePointEight;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemBlockGeneric;
import net.minecraft.block.BlockSandStone;
import net.minecraft.item.ItemBlock;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class RedSandstone extends BlockSandStone implements ISubBlocksBlock, IConfigurable {

	public RedSandstone() {
		setHardness(0.8F);
		setBlockTextureName("red_sandstone");
		setBlockName(Utils.getUnlocalisedName("red_sandstone"));
		setCreativeTab(GanysSurface.enableRedSandstone ? GanysSurface.surfaceTab : null);
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableRedSandstone;
	}
}