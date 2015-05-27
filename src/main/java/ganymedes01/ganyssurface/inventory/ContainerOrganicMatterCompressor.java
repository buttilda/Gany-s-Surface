package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.BetterSlot;
import ganymedes01.ganyssurface.recipes.OrganicMatterRegistry;
import ganymedes01.ganyssurface.tileentities.TileEntityOrganicMatterCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerOrganicMatterCompressor extends GanysContainer {

	private final TileEntityOrganicMatterCompressor compressor;

	public ContainerOrganicMatterCompressor(InventoryPlayer inventory, TileEntityOrganicMatterCompressor tile) {
		super(tile);
		compressor = tile;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new BetterSlot(tile, j + i * 3, j * 18 + 34, i * 18 + 18));
		addSlotToContainer(new BetterSlot(tile, 9, 126, 17));
		addSlotToContainer(new BetterSlot(tile, 10, 126, 53));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < crafters.size(); i++)
			compressor.sendGUIData(this, (ICrafting) crafters.get(i));
	}

	@Override
	public void updateProgressBar(int i, int j) {
		compressor.getGUIData(i, j);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotItemStack = slot.getStack();
			stack = slotItemStack.copy();

			if (slotIndex < 11) {
				if (!mergeItemStack(slotItemStack, 11, inventorySlots.size(), true))
					return null;
			} else if (slotItemStack.getItem() == Items.coal && slotItemStack.getItemDamage() == 0) {
				if (!mergeItemStack(slotItemStack, 9, 10, false))
					return null;
			} else if (OrganicMatterRegistry.isOrganicMatter(slotItemStack)) {
				if (!mergeItemStack(slotItemStack, 0, 9, false))
					return null;
			} else
				return null;

			if (slotItemStack.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
		}

		return stack;
	}
}