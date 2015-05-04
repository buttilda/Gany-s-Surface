package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BeetrootSoup extends ItemSoup implements IConfigurable {

	public BeetrootSoup() {
		super(6);
		setContainerItem(Items.bowl);
		setTextureName(Utils.getItemTexture(Strings.BEETROOT_SOUP_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.BEETROOT_SOUP_NAME));
		setCreativeTab(GanysSurface.enableBeetroots ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableBeetroots;
	}
}