package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemFood;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ChocolateBar extends ItemFood implements IConfigurable {

	public ChocolateBar() {
		super(3, 1.2F, false);
		setCreativeTab(GanysSurface.enableChocolate ? GanysSurface.surfaceTab : null);
		setTextureName(Utils.getItemTexture(Strings.CHOCOLATE_BAR_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.CHOCOLATE_BAR_NAME));
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableChocolate;
	}
}