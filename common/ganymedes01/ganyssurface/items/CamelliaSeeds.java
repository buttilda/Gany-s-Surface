package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class CamelliaSeeds extends ItemSeeds {

	public CamelliaSeeds(int id) {
		super(id, ModBlocks.camelliaCrop.blockID, Block.tilledField.blockID);
		setMaxStackSize(64);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.CAMELLIA_SEEDS_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CAMELLIA_SEEDS_NAME));
	}
}