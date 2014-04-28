package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemFood;

public class RoastedSquid extends ItemFood {

	public RoastedSquid() {
		super(6, 0.5F, true);
		setFull3D();
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.ROASTED_SQUID_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ROASTED_SQUID_NAME));
	}
}