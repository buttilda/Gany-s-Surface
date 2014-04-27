package ganymedes01.ganyssurface.items.block;

import ganymedes01.ganyssurface.blocks.ColouredRedstone;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ItemColouredRedstoneBlock extends ItemBlock {

	public ItemColouredRedstoneBlock(int id) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.COLOURED_REDSTONE_BLOCK_NAME + "_item_"));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + ColouredRedstone.COLOURS[stack.getItemDamage()].toUpperCase();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}