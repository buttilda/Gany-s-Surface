package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.items.WoodenArmour;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
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
		else if (fuel.getItem() == Item.getItemFromBlock(ModBlocks.charcoalBlock))
			return TileEntityFurnace.getItemBurnTime(new ItemStack(Blocks.coal_block));
		else if (fuel.getItem() == ModItems.stick)
			return 100;

		return 0;
	}
}
