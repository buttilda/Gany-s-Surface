package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemFood;

public class MuttonRaw extends ItemFood {

	public MuttonRaw() {
		super(2, 0.3F, true);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.RAW_MUTTON));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.RAW_MUTTON));
	}
}