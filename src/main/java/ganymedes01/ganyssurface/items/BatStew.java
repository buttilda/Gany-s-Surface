package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
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
		super(8);
		setTextureName(Utils.getItemTexture(Strings.BAT_STEW_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.BAT_STEW_NAME));
		setCreativeTab(GanysSurface.enablePocketCritters ? GanysSurface.surfaceTab : null);
	}
}