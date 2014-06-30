package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.PlaceholderSlot;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.items.PortableDualWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerPortableDualWorkTable extends ContainerDualWorkTable {

	private final World world;

	public ContainerPortableDualWorkTable(EntityPlayer player, int slot) {
		super(player.inventory, PortableDualWorkTable.getTile(player.inventory.getStackInSlot(slot)), false);
		world = player.worldObj;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 19 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; i++)
			if (player.inventory.currentItem == i)
				addSlotToContainer(new PlaceholderSlot(player.inventory, i, 19 + i * 18, 142));
			else
				addSlotToContainer(new Slot(player.inventory, i, 19 + i * 18, 142));
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if (inventory == dualWorkTable.invtCraftMatrix)
			craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(dualWorkTable.invtCraftMatrix, world));
		else if (inventory == dualWorkTable.invtCraftMatrixRight)
			craftResultRight.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(dualWorkTable.invtCraftMatrixRight, world));
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		NBTTagCompound data = new NBTTagCompound();
		data.setTag("Inventory", new NBTTagCompound());
		dualWorkTable.writeToNBT(data.getCompoundTag("Inventory"));

		if (player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.portalDualWorkTable) {
			for (int i = 0; i < player.inventory.mainInventory.length; i++)
				if (player.inventory.mainInventory[i] != null)
					if (player.inventory.mainInventory[i].getItem() == ModItems.portalDualWorkTable)
						player.inventory.mainInventory[i].setTagCompound(data);
		} else
			player.getHeldItem().setTagCompound(data);
	}
}