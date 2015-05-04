package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class CamelliaSeeds extends ItemSeeds implements IConfigurable {

	public CamelliaSeeds() {
		super(ModBlocks.camelliaCrop, Blocks.farmland);
		setTextureName(Utils.getItemTexture(Strings.CAMELLIA_SEEDS_NAME));
		setCreativeTab(GanysSurface.enableTea ? GanysSurface.surfaceTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.CAMELLIA_SEEDS_NAME));
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableTea;
	}
}