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

public class EmptyMug extends Item {

	public EmptyMug(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.EMPTY_MUG_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.EMPTY_MUG_NAME));
	}
}
