package ganymedes01.ganyssurface.core.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's Surface
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
		return new HashCodeBuilder().append(item).append(meta).append(nbt).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UnsizedStack))
			return false;

		return new EqualsBuilder().append(item, ((UnsizedStack) obj).item).append(meta, ((UnsizedStack) obj).meta).append(nbt, ((UnsizedStack) obj).nbt).build();
	}

	@Override
	public int compareTo(UnsizedStack unStack) {
		return hashCode() - unStack.hashCode();
	}
}