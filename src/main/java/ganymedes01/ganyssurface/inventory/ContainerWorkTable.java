package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict2;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable.WorkTableCrafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerWorkTable extends GanysContainer implements INoConflictRecipeContainer {

	private final World world;
	private final InventoryCrafting matrix;
	private final IInventory result = new InventoryCraftResult();
	private int currentResultIndex = 0;
	private boolean hasMultipleResults = false;

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
		boolean hasNonNull = false;
		for (int i = 0; i < matrix.getSizeInventory(); i++)
			if (matrix.getStackInSlot(i) != null) {
				hasNonNull = true;
				break;
			}
		if (!hasNonNull) {
			result.setInventorySlotContents(0, null);
			return;
		}

		if (GanysSurface.enableNoRecipeConflict) {
			List<ItemStack> results = getPossibleResults(matrix, world);
			if (results.isEmpty()) {
				result.setInventorySlotContents(0, null);
				hasMultipleResults = false;
			} else if (results.size() == 1) {
				result.setInventorySlotContents(0, results.get(0));
				hasMultipleResults = false;
			} else {
				setCurrentResultIndex(true, Math.min(currentResultIndex, results.size() - 1));
				hasMultipleResults = true;
			}

			for (Object crafter : crafters)
				if (crafter instanceof EntityPlayer)
					PacketHandler.sendToPlayer(new PacketGUINoRecipeConflict2(true, hasMultipleResults), (EntityPlayer) crafter);
		} else
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

	@Override
	public void handleButtonClick(boolean isFirstMatrix, int bump) {
		List<ItemStack> results = getPossibleResults(matrix, world);
		if (results.size() <= 1)
			return;

		int index = currentResultIndex + bump;
		if (index >= results.size())
			index = 0;
		else if (index < 0)
			index = results.size() - 1;

		setCurrentResultIndex(true, index);
		for (Object crafter : crafters)
			if (crafter instanceof EntityPlayer)
				PacketHandler.sendToPlayer(new PacketGUINoRecipeConflict(true, index), (EntityPlayer) crafter);
	}

	@Override
	public void setCurrentResultIndex(boolean isFirstMatrix, int index) {
		List<ItemStack> results = getPossibleResults(matrix, world);
		if (results.size() <= 1)
			return;

		currentResultIndex = index;
		result.setInventorySlotContents(0, results.get(currentResultIndex));
	}

	@SuppressWarnings("unchecked")
	public static List<ItemStack> getPossibleResults(InventoryCrafting matrix, World world) {
		List<ItemStack> results = new ArrayList<ItemStack>();
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

		int toolCount = 0;
		ItemStack tool1 = null;
		ItemStack tool2 = null;
		for (int i = 0; i < matrix.getSizeInventory(); i++) {
			ItemStack stack = matrix.getStackInSlot(i);
			if (stack != null) {
				if (toolCount == 0)
					tool1 = stack;
				if (toolCount == 1)
					tool2 = stack;
				toolCount++;
			}
		}
		if (toolCount == 2 && tool1.getItem() == tool2.getItem() && tool1.stackSize == 1 && tool2.stackSize == 1 && tool1.getItem().isRepairable()) {
			Item item = tool1.getItem();
			int damage1 = item.getMaxDamage() - tool1.getItemDamageForDisplay();
			int damage2 = item.getMaxDamage() - tool2.getItemDamageForDisplay();
			int newDamage = item.getMaxDamage() - (damage1 + damage2 + item.getMaxDamage() * 5 / 100);
			if (newDamage < 0)
				newDamage = 0;
			results.add(new ItemStack(tool1.getItem(), 1, newDamage));
		} else
			for (IRecipe recipe : recipes)
				if (recipe.matches(matrix, world))
					results.add(recipe.getCraftingResult(matrix));

		return results;
	}

	@Override
	public void setHasMultipleResults(boolean isFirstMatrix, boolean hasMultipleResults) {
		this.hasMultipleResults = hasMultipleResults;
	}

	@Override
	public boolean hasMultipleResults(boolean isFirstMatrix) {
		return hasMultipleResults;
	}
}