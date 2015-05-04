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

public class PineCone extends Item implements IConfigurable {

	public PineCone() {
		setTextureName(Utils.getItemTexture(Strings.PINE_CONE_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.PINE_CONE_NAME));
		setCreativeTab(GanysSurface.enablePineCones ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePineCones;
	}
}