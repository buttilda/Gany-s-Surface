package ganymedes01.ganyssurface.creativetab;

import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class CreativeTabSurface extends CreativeTabs {

	public CreativeTabSurface() {
		super(Reference.MOD_ID);
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Blocks.grass);
	}
}