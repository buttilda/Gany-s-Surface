package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.Item;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ObsidianHead extends Item {

	public ObsidianHead() {
		setMaxStackSize(16);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.OBSIDIAN_HEAD_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.OBSIDIAN_HEAD_NAME));
	}
}