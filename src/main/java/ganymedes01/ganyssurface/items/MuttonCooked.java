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

public class MuttonCooked extends ItemFood implements IConfigurable {

	public MuttonCooked() {
		super(6, 0.8F, true);
		setTextureName(Strings.COOKED_MUTTON);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.COOKED_MUTTON));
		setCreativeTab(GanysSurface.enableMutton ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableMutton;
	}
}