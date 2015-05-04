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

public class ObsidianHead extends Item implements IConfigurable {

	public ObsidianHead() {
		setMaxStackSize(16);
		setTextureName(Utils.getItemTexture(Strings.OBSIDIAN_HEAD_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.OBSIDIAN_HEAD_NAME));
		setCreativeTab(GanysSurface.enableDislocators ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableDislocators;
	}
}