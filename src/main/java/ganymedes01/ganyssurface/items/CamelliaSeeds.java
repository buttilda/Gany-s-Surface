package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ModBlocks;
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

public class CamelliaSeeds extends ItemSeeds {

	public CamelliaSeeds() {
		super(ModBlocks.camelliaCrop, Blocks.farmland);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.CAMELLIA_SEEDS_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CAMELLIA_SEEDS_NAME));
	}
}