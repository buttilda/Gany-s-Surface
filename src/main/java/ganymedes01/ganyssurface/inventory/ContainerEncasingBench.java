package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.inventory.slots.SlotEncaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerEncasingBench extends Container {

	private final InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	private final IInventory craftResult = new InventoryCraftResult();
	private final IInventory planks = new InventoryBasic("Planks", false, 1) {

		@Override
		public ItemStack decrStackSize(int slot, int size) {
			ItemStack ret = super.decrStackSize(slot, size);
			ContainerEncasingBench.this.onCraftMatrixChanged(this);
			return ret;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			super.setInventorySlotContents(slot, stack);
			ContainerEncasingBench.this.onCraftMatrixChanged(this);
		}
	};

	public ContainerEncasingBench(InventoryPlayer player) {
		addSlotToContainer(new SlotEncaser(craftMatrix, planks, craftResult, 0, 124, 41));
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(craftMatrix, j + i * 3, 30 + j * 18, 23 + i * 18));
		addSlotToContainer(new Slot(planks, 0, 90, 15) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return InventoryUtils.isItemOre(stack, "plankWood");
			}
		});

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 90 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 148));

		onCraftMatrixChanged(craftMatrix);
	}

	private ItemStack getEncasedItem() {
		if (planks.getStackInSlot(0) == null)
			return null;

		for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
			for (int j = 0; j < craftMatrix.getSizeInventory(); j++)
				if (i != j)
					if (!InventoryUtils.areStacksTheSame(craftMatrix.getStackInSlot(i), craftMatrix.getStackInSlot(j), false))
						return null;

		ItemStack storageCase = new ItemStack(ModItems.storageCase);
		storageCase.setTagCompound(new NBTTagCompound());

		NBTTagCompound stackData = new NBTTagCompound();
		ItemStack sCopy = craftMatrix.getStackInSlot(0).copy();
		sCopy.stackSize = 1;
		sCopy.writeToNBT(stackData);
		storageCase.getTagCompound().setTag("stack", stackData);
		return storageCase;
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		craftResult.setInventorySlotContents(0, getEncasedItem());
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if (!player.worldObj.isRemote) {
			for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
				ItemStack stack = craftMatrix.getStackInSlotOnClosing(i);
				if (stack != null)
					player.dropPlayerItemWithRandomChoice(stack, false);
			}
			ItemStack stack = planks.getStackInSlotOnClosing(0);
			if (stack != null)
				player.dropPlayerItemWithRandomChoice(stack, false);
		}
	}

	@Override
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != craftResult && super.func_94530_a(stack, slot);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex == 0) {
				if (!mergeItemStack(itemstack1, 10, 46, true))
					return null;
				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotIndex >= 10 && slotIndex < 46) {
				if (!mergeItemStack(itemstack1, 1, 10, false))
					return null;
			} else if (!mergeItemStack(itemstack1, 10, 46, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}
}