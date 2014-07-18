package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemFood;

public class MuttonCooked extends ItemFood {

	public MuttonCooked() {
		super(6, 0.8F, true);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.COOKED_MUTTON));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.COOKED_MUTTON));
	}
}