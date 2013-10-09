package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.Item;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TeaBag extends Item {

	public TeaBag() {
		super(ModIDs.TEA_BAG_ID);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.TEA_BAG_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.TEA_BAG_NAME));
	}
}
