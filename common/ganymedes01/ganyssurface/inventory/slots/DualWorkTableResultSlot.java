package ganymedes01.ganyssurface.inventory.slots;

import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
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

public class DualWorkTableResultSlot extends SlotCrafting {

	private TileEntityDualWorkTable workTable;
	private IInventory matrix;
	private final int startIndex;

	public DualWorkTableResultSlot(TileEntityDualWorkTable tile, EntityPlayer player, IInventory matrix, IInventory result, int slotIndex, int y, int z, int startIndex) {
		super(player, matrix, result, slotIndex, y, z);
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