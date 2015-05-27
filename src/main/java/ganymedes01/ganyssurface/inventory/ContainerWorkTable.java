package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable.WorkTableCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerWorkTable extends GanysContainer {

	protected World world;
	protected InventoryCrafting matrix;
	protected IInventory result = new InventoryCraftResult();

	public ContainerWorkTable(InventoryPlayer inventory, TileEntityWorkTable tile) {
		super(tile);
		world = tile.getWorldObj();
		matrix = new WorkTableCrafting(tile, this);

		addSlotToContainer(new SlotCrafting(inventory.player, matrix, result, 0, 124, 35));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(matrix, j + i * 3, 30 + j * 18, 17 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));

		onCraftMatrixChanged(matrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		result.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(matrix, world));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex <= 10)
				if (!mergeItemStack(itemstack1, 10, inventorySlots.size(), true))
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

	@Override
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != result;
	}
}