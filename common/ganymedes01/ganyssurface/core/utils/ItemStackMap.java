package ganymedes01.ganyssurface.core.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ItemStackMap<T> extends HashMap<ItemStack, T> {

	@Override
	public boolean containsKey(Object key) {
		if (key instanceof ItemStack) {
			Iterator iterator = keySet().iterator();
			while (iterator.hasNext())
				if (areItemsEqual((ItemStack) iterator.next(), (ItemStack) key))
					return true;
		}
		return false;
	}

	@Override
	public T get(Object key) {
		if (containsKey(key)) {
			Iterator<Map.Entry<ItemStack, T>> iterator = entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<ItemStack, T> mapEntry = iterator.next();
				if (areItemsEqual(mapEntry.getKey(), (ItemStack) key))
					return mapEntry.getValue();
			}
		}
		return null;
	}

	private boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
		if (stack1 == null || stack2 == null)
			return false;

		ItemStack stack1Single = stack1.copy();
		stack1Single.stackSize = 1;

		ItemStack stack2Single = stack2.copy();
		stack2Single.stackSize = 1;

		if (stack1.getItem().getMaxDamage() > 0 || stack2.getItem().getMaxDamage() > 0)
			return stack1.itemID == stack2.itemID;

		return ItemStack.areItemStacksEqual(stack1Single, stack2Single);
	}
}