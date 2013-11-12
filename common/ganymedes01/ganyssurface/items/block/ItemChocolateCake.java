package ganymedes01.ganyssurface.items.block;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemBlock;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ItemChocolateCake extends ItemBlock {

	public ItemChocolateCake(int id) {
		super(id);
		setMaxStackSize(1);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CHOCOLATE_CAKE_NAME + "_item"));
	}
}
