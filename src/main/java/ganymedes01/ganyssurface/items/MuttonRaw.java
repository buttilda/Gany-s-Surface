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

public class MuttonRaw extends ItemFood implements IConfigurable {

	public MuttonRaw() {
		super(2, 0.3F, true);
		setTextureName(Strings.RAW_MUTTON);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.RAW_MUTTON));
		setCreativeTab(GanysSurface.enableMutton ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableMutton;
	}
}