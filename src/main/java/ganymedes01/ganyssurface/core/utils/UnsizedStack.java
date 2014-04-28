package ganymedes01.ganyssurface.core.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Gany's Nether
 * 
 * @author ganymedes01
 * 
 */

public class UnsizedStack implements Comparable<UnsizedStack> {

	private final Item item;
	private final int size;
	private final int meta;
	private final NBTTagCompound nbt;

	public UnsizedStack(ItemStack stack) {
		item = stack.getItem();
		size = stack.stackSize;
		meta = stack.getItemDamage();
		nbt = stack.getTagCompound();
	}

	public int getOldStackSize() {
		return size;
	}

	public ItemStack getStack() {
		return getStack(1);
	}

	public ItemStack getStack(int size) {
		ItemStack stack = new ItemStack(item, size, meta);
		stack.setTagCompound(nbt);
		return stack;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hash = new HashCodeBuilder(17, 31);
		hash.append(item);
		hash.append(meta);
		hash.append(nbt);
		return hash.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public int compareTo(UnsizedStack unStack) {
		return hashCode() - unStack.hashCode();
	}
}