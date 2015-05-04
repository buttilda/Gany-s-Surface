package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
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

public class CharcoalBlock extends Block implements IConfigurable {

	public CharcoalBlock() {
		super(Material.rock);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName(Strings.CHARCOAL_BLOCK));
		setBlockTextureName(Utils.getBlockTexture(Strings.CHARCOAL_BLOCK));
		setCreativeTab(GanysSurface.enableBlockOfCharcoal ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableBlockOfCharcoal;
	}
}