package ganymedes01.ganyssurface.inventory.slots;

import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class WorkTableResultSlot extends SlotCrafting {

	private TileEntityWorkTable workTable;
	private IInventory matrix;
	private final int startIndex;

	public WorkTableResultSlot(TileEntityWorkTable tile, EntityPlayer player, IInventory matrix, IInventory result, int slotIndex, int x, int y) {
		this(tile, player, matrix, result, slotIndex, x, y, 0);
	}

	public WorkTableResultSlot(TileEntityWorkTable tile, EntityPlayer player, IInventory matrix, IInventory result, int slotIndex, int x, int y, int startIndex) {
		super(player, matrix, result, slotIndex, x, y);
		workTable = tile;
		this.matrix = matrix;
		this.startIndex = startIndex;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		super.onPickupFromSlot(player, stack);
		for (int i = 0; i < matrix.getSizeInventory(); i++)
			workTable.setInventorySlotContents(startIndex + i, matrix.getStackInSlot(i));
	}
}