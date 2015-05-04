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

public class TeaLeaves extends Item implements IConfigurable {

	public TeaLeaves() {
		setTextureName(Utils.getItemTexture(Strings.TEA_LEAVES_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.TEA_LEAVES_NAME));
		setCreativeTab(GanysSurface.enableTea ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableTea;
	}
}