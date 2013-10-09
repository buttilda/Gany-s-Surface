package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemSoup;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BatStew extends ItemSoup {

	public BatStew() {
		super(ModIDs.BAT_STEW_ID, 8);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.BAT_STEW_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.BAT_STEW_NAME));
	}
}
