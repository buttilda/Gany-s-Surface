package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.items.WoodenArmour;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem() instanceof WoodenArmour)
			return 300;
		return 0;
	}
}
