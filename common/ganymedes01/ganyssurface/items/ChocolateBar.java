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

public class ChocolateBar extends ItemFood {

	public ChocolateBar() {
		super(ModIDs.CHOCOLATE_BAR_ID, 3, 1.2F, false);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.CHOCOLATE_BAR_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CHOCOLATE_BAR_NAME));
	}
}
