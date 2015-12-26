package ganymedes01.ganyssurface.inventory;

import java.util.List;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict2;
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

public class ContainerDualWorkTable extends GanysContainer implements INoConflictRecipeContainer {

	protected World world;
	protected InventoryCrafting matrixLeft;
	protected InventoryCrafting matrixRight;

	protected int currentResultIndex1 = 0, currentResultIndex2 = 0;
	protected boolean hasMultipleResults1 = false, hasMultipleResults2 = false;

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
		boolean hasNonNull = false;
		for (int i = 0; i < inventory.getSizeInventory(); i++)
			if (inventory.getStackInSlot(i) != null) {
				hasNonNull = true;
				break;
			}
		if (!hasNonNull) {
			if (inventory == matrixLeft)
				result.setInventorySlotContents(0, null);
			else
				resultRight.setInventorySlotContents(0, null);
			return;
		}

		if (GanysSurface.enableNoRecipeConflict) {
			if (inventory == matrixLeft) {
				List<ItemStack> results = ContainerWorkTable.getPossibleResults(matrixLeft, world);
				if (results.isEmpty()) {
					result.setInventorySlotContents(0, null);
					hasMultipleResults1 = false;
				} else if (results.size() == 1) {
					result.setInventorySlotContents(0, results.get(0));
					hasMultipleResults1 = false;
				} else {
					setCurrentResultIndex(true, Math.min(currentResultIndex1, results.size() - 1));
					hasMultipleResults1 = true;
				}

				for (Object crafter : crafters)
					if (crafter instanceof EntityPlayer)
						PacketHandler.sendToPlayer(new PacketGUINoRecipeConflict2(true, hasMultipleResults1), (EntityPlayer) crafter);
			}

			if (inventory == matrixRight) {
				List<ItemStack> results = ContainerWorkTable.getPossibleResults(matrixRight, world);
				if (results.isEmpty()) {
					resultRight.setInventorySlotContents(0, null);
					hasMultipleResults2 = false;
				} else if (results.size() == 1) {
					resultRight.setInventorySlotContents(0, results.get(0));
					hasMultipleResults2 = false;
				} else {
					setCurrentResultIndex(false, Math.min(currentResultIndex2, results.size() - 1));
					hasMultipleResults2 = true;
				}

				for (Object crafter : crafters)
					if (crafter instanceof EntityPlayer)
						PacketHandler.sendToPlayer(new PacketGUINoRecipeConflict2(false, hasMultipleResults2), (EntityPlayer) crafter);
			}
		} else {
			if (inventory == matrixLeft)
				result.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(matrixLeft, world));
			if (inventory == matrixRight)
				resultRight.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(matrixRight, world));
		}
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

	@Override
	public void handleButtonClick(boolean isFirstMatrix, int bump) {
		InventoryCrafting matrix = isFirstMatrix ? matrixLeft : matrixRight;
		List<ItemStack> results = ContainerWorkTable.getPossibleResults(matrix, world);
		if (results.size() <= 1)
			return;

		int index = (isFirstMatrix ? currentResultIndex1 : currentResultIndex2) + bump;
		if (index >= results.size())
			index = 0;
		else if (index < 0)
			index = results.size() - 1;

		setCurrentResultIndex(isFirstMatrix, index);
		for (Object crafter : crafters)
			if (crafter instanceof EntityPlayer)
				PacketHandler.sendToPlayer(new PacketGUINoRecipeConflict(isFirstMatrix, index), (EntityPlayer) crafter);
	}

	@Override
	public void setCurrentResultIndex(boolean isFirstMatrix, int index) {
		InventoryCrafting matrix = isFirstMatrix ? matrixLeft : matrixRight;
		List<ItemStack> results = ContainerWorkTable.getPossibleResults(matrix, world);
		if (results.size() <= 1)
			return;

		if (isFirstMatrix)
			currentResultIndex1 = index;
		else
			currentResultIndex2 = index;
		(isFirstMatrix ? result : resultRight).setInventorySlotContents(0, results.get(isFirstMatrix ? currentResultIndex1 : currentResultIndex2));
	}

	@Override
	public void setHasMultipleResults(boolean isFirstMatrix, boolean hasMultipleResults) {
		if (isFirstMatrix)
			hasMultipleResults1 = hasMultipleResults;
		else
			hasMultipleResults2 = hasMultipleResults;
	}

	@Override
	public boolean hasMultipleResults(boolean isFirstMatrix) {
		return isFirstMatrix ? hasMultipleResults1 : hasMultipleResults2;
	}
}