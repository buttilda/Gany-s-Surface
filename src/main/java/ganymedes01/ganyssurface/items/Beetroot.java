package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemFood;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Beetroot extends ItemFood {

	public Beetroot() {
		super(1, 0.3F, false);
		setTextureName(Utils.getItemTexture(Strings.BEETROOT_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.BEETROOT_NAME));
		setCreativeTab(GanysSurface.enableBeetroots ? GanysSurface.surfaceTab : null);
	}
}