package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ContainerCraftingTable extends ContainerWorkbench implements INoConflictRecipeContainer {

	private final World world;
	private int currentResultIndex = 0;

	public ContainerCraftingTable(InventoryPlayer playerInventory, World world, int x, int y, int z) {
		super(playerInventory, world, x, y, z);
		this.world = world;
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		List<ItemStack> results = ContainerWorkTable.getPossibleResults(craftMatrix, world);
		if (results.isEmpty())
			craftResult.setInventorySlotContents(0, null);
		else if (results.size() == 1)
			craftResult.setInventorySlotContents(0, results.get(0));
		else
			setCurrentResultIndex(true, Math.min(currentResultIndex, results.size() - 1));
	}

	@Override
	public void handleButtonClick(boolean isFirstMatrix, int bump) {
		List<ItemStack> results = ContainerWorkTable.getPossibleResults(craftMatrix, world);
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
		List<ItemStack> results = ContainerWorkTable.getPossibleResults(craftMatrix, world);
		if (results.size() <= 1)
			return;

		currentResultIndex = index;
		craftResult.setInventorySlotContents(0, results.get(currentResultIndex));
	}
}