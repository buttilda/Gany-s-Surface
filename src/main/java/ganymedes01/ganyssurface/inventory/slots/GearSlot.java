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
		return isRegisteredGear(stack) || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemSword;
	}

	private boolean isRegisteredGear(ItemStack stack) {
		for (int id : OreDictionary.getOreIDs(stack))
			if (OreDictionary.getOreName(id).startsWith("gear"))
				return true;
		return false;
	}
}