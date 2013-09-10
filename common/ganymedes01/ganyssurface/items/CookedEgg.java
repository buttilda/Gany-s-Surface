package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemFood;

public class CookedEgg extends ItemFood {

	public CookedEgg(int id) {
		super(id, 3, false);
		setMaxStackSize(16);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.COOKED_EGG_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.COOKED_EGG_NAME));
	}
}
