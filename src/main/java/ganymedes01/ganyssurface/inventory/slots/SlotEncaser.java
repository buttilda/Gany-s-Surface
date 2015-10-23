package ganymedes01.ganyssurface.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SlotEncaser extends Slot {

	private final IInventory craftMatrix, planks;

	public SlotEncaser(IInventory matrix, IInventory planks, IInventory invt, int index, int x, int y) {
		super(invt, index, x, y);
		craftMatrix = matrix;
		this.planks = planks;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		onCrafting(stack);

		for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
			ItemStack itemstack1 = craftMatrix.getStackInSlot(i);

			if (itemstack1 != null)
				craftMatrix.decrStackSize(i, 1);
		}

		for (int i = 0; i < planks.getSizeInventory(); ++i) {
			ItemStack itemstack1 = planks.getStackInSlot(i);

			if (itemstack1 != null)
				planks.decrStackSize(i, 1);
		}
	}
}