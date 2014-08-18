package ganymedes01.ganyssurface.api;

import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public interface IQuiver {

	public int getArrowAmount(ItemStack stack);

	public void setArrowAmount(ItemStack stack, int amount);

	public int getMaxArrowCount(ItemStack stack);
}