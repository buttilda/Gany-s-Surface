package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.Item;

public class TeaBag extends Item {

	public TeaBag(int id) {
		super(id);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.TEA_BAG_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.TEA_BAG_NAME));
	}
}
