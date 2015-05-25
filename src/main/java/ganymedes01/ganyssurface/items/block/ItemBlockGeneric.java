package ganymedes01.ganyssurface.items.block;

import ganymedes01.ganyssurface.blocks.BlockGeneric;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ItemBlockGeneric extends ItemBlock {

	public ItemBlockGeneric(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (field_150939_a instanceof BlockGeneric) {
			String name = ((BlockGeneric) field_150939_a).getNameFor(stack.getItemDamage());
			if ("".equals(name))
				return getUnlocalizedName();
			else
				return getUnlocalizedName() + "_" + name;
		}

		return getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}