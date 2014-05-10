package ganymedes01.ganyssurface.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class GearSlot extends Slot {

	public GearSlot(IInventory inventory, int slot, int posX, int posY) {
		super(inventory, slot, posX, posY);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		String oreName = OreDictionary.getOreName(OreDictionary.getOreID(stack));
		return oreName.startsWith("gear") || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemSword;
	}
}