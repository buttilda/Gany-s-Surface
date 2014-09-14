package ganymedes01.ganyssurface.items.block;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ItemRedSandstoneBlocks extends ItemBlock {

	public ItemRedSandstoneBlocks(Block block) {
		super(block);
		setHasSubtypes(true);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.PRISMARINE_BLOCKS + "_item_"));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + stack.getItemDamage();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}