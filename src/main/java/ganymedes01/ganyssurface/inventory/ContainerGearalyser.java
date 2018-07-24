package ganymedes01.ganyssurface.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganyssurface.inventory.slots.GearSlot;
import ganymedes01.ganyssurface.inventory.slots.SlotArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerGearalyser extends Container {

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);

	public ContainerGearalyser(InventoryPlayer player) {
		addSlotToContainer(new GearSlot(craftMatrix, 0, 11, 14));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 169));

		for (int i = 0; i < 4; i++) {
			final int type = i;
			addSlotToContainer(new SlotArmor(player, player.getSizeInventory() - 1 - i, 176, 14 + i * 18, i) {

				@Override
				@SideOnly(Side.CLIENT)
				public IIcon getBackgroundIconIndex() {
					return ItemArmor.func_94602_b(type);
				}
			});
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if (!player.worldObj.isRemote)
			for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
				ItemStack stack = craftMatrix.getStackInSlotOnClosing(i);
				if (stack != null)
					player.dropPlayerItemWithRandomChoice(stack, false);
			}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotItemStack = slot.getStack();
			stack = slotItemStack.copy();

			if (slotIndex < 1) {
				if (!mergeItemStack(slotItemStack, 1, inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(slotItemStack, 0, 1, true))
				return null;

			if (slotItemStack.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
		}

		return stack;
	}

}