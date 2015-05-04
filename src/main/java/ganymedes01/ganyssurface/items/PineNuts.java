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

public class PineNuts extends ItemFood implements IConfigurable {

	public PineNuts() {
		super(4, 0.3F, false);
		setTextureName(Utils.getItemTexture(Strings.PINE_NUTS_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.PINE_NUTS_NAME));
		setCreativeTab(GanysSurface.enablePineCones ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePineCones;
	}
}