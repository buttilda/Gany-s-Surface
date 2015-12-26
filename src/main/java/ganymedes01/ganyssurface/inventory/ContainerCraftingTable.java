package ganymedes01.ganyssurface.inventory;

import java.util.List;

import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict;
import ganymedes01.ganyssurface.network.packet.PacketGUINoRecipeConflict2;
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
	private final int x, y, z;
	private int currentResultIndex = 0;
	private boolean hasMultipleResults = false;

	public ContainerCraftingTable(InventoryPlayer playerInventory, World world, int x, int y, int z) {
		super(playerInventory, world, x, y, z);
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(x + 0.5, y + 0.5, z + 0.5) <= 64.0;
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		List<ItemStack> results = ContainerWorkTable.getPossibleResults(craftMatrix, world);
		if (results.isEmpty()) {
			craftResult.setInventorySlotContents(0, null);
			hasMultipleResults = false;
		} else if (results.size() == 1) {
			craftResult.setInventorySlotContents(0, results.get(0));
			hasMultipleResults = false;
		} else {
			setCurrentResultIndex(true, Math.min(currentResultIndex, results.size() - 1));
			hasMultipleResults = true;
		}

		for (Object crafter : crafters)
			if (crafter instanceof EntityPlayer)
				PacketHandler.sendToPlayer(new PacketGUINoRecipeConflict2(true, hasMultipleResults), (EntityPlayer) crafter);
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

	@Override
	public void setHasMultipleResults(boolean isFirstMatrix, boolean hasMultipleResults) {
		this.hasMultipleResults = hasMultipleResults;
	}

	@Override
	public boolean hasMultipleResults(boolean isFirstMatrix) {
		return hasMultipleResults;
	}
}