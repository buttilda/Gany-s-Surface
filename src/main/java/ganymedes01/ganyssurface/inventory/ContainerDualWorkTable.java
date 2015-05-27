package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
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

public class ContainerDualWorkTable extends GanysContainer {

	protected World world;
	protected InventoryCrafting matrixLeft;
	protected InventoryCrafting matrixRight;

	protected IInventory result = new InventoryCraftResult();
	protected IInventory resultRight = new InventoryCraftResult();

	protected final TileEntityDualWorkTable tile;

	public ContainerDualWorkTable(InventoryPlayer inventory, TileEntityDualWorkTable tile) {
		this(inventory, tile, true);
	}

	public ContainerDualWorkTable(InventoryPlayer inventory, TileEntityDualWorkTable tile, boolean addPlayerInventory) {
		super(tile);
		this.tile = tile;
		world = tile.getWorldObj();
		matrixLeft = new WorkTableCrafting(tile, this);
		matrixRight = new WorkTableCrafting(tile, this, 9);

		addSlotToContainer(new SlotCrafting(inventory.player, matrixLeft, result, 0, 75, 35));
		addSlotToContainer(new SlotCrafting(inventory.player, matrixRight, resultRight, 1, 168, 35));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(matrixLeft, j + i * 3, 12 + j * 18, 17 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new Slot(matrixRight, j + i * 3, 105 + j * 18, 17 + i * 18));

		if (addPlayerInventory) {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 9; j++)
					addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 19 + j * 18, 84 + i * 18));
			for (int i = 0; i < 9; i++)
				addSlotToContainer(new Slot(inventory, i, 19 + i * 18, 142));
		}

		onCraftMatrixChanged(matrixLeft);
		onCraftMatrixChanged(matrixRight);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if (inventory == matrixLeft)
			result.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(matrixLeft, world));

		if (inventory == matrixRight)
			resultRight.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(matrixRight, world));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex <= 20)
				if (!mergeItemStack(itemstack1, 20, inventorySlots.size(), true))
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
		return !(slot instanceof SlotCrafting);
	}
}