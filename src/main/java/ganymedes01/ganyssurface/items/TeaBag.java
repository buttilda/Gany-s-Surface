package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.Item;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TeaBag extends Item implements IConfigurable {

	public TeaBag() {
		setTextureName(Utils.getItemTexture(Strings.TEA_BAG_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.TEA_BAG_NAME));
		setCreativeTab(GanysSurface.enableTea ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableTea;
	}
}