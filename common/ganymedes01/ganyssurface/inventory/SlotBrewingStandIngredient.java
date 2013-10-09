package ganymedes01.ganyssurface.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/*
 * Stolen from net.minecraft.inventory
 * 
 */

class SlotBrewingStandIngredient extends Slot {
	/** The brewing stand this slot belongs to. */
	final ContainerVanillaBrewingStand brewingStand;

	public SlotBrewingStandIngredient(ContainerVanillaBrewingStand containerVanillaBrewingStand, IInventory par2IInventory, int par3, int par4, int par5) {
		super(par2IInventory, par3, par4, par5);
		brewingStand = containerVanillaBrewingStand;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for
	 * the armor slots.
	 */
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack != null ? Item.itemsList[par1ItemStack.itemID].isPotionIngredient(par1ItemStack) : false;
	}

	/**
	 * Returns the maximum stack size for a given slot (usually the same as
	 * getInventoryStackLimit(), but 1 in the case of armor slots)
	 */
	@Override
	public int getSlotStackLimit() {
		return 64;
	}
}
