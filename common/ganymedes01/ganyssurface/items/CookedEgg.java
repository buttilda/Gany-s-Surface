package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemFood;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class CookedEgg extends ItemFood {

	public CookedEgg() {
		super(ModIDs.COOKED_EGG_ID, 5, false);
		setMaxStackSize(16);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.COOKED_EGG_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.COOKED_EGG_NAME));
	}
}
