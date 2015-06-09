package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.inventory.slots.PlaceholderSlot;
import ganymedes01.ganyssurface.items.PortableDualWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerPortableDualWorkTable extends ContainerDualWorkTable {

	private final int slot;

	public ContainerPortableDualWorkTable(EntityPlayer player, int slot) {
		super(player.inventory, PortableDualWorkTable.getTile(player.inventory.getStackInSlot(slot)), false);
		world = player.worldObj;
		this.slot = slot;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 19 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; i++)
			if (slot == i)
				addSlotToContainer(new PlaceholderSlot(player.inventory, i, 19 + i * 18, 142));
			else
				addSlotToContainer(new Slot(player.inventory, i, 19 + i * 18, 142));
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		NBTTagCompound data = new NBTTagCompound();
		data.setTag("Inventory", new NBTTagCompound());
		tile.writeToNBT(data.getCompoundTag("Inventory"));

		if (player.inventory.mainInventory[slot] != null)
			if (player.inventory.mainInventory[slot].getItem() == ModItems.portalDualWorkTable)
				player.inventory.mainInventory[slot].setTagCompound(data);
	}
}