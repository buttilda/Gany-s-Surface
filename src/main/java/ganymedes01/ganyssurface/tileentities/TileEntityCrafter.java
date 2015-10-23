package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntityCrafter extends GanysInventory implements ISidedInventory {

	private static final int[] ACCESSIBLE_SLOTS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 18 };

	private final CrafterGrid grid = new CrafterGrid(this);

	public TileEntityCrafter() {
		super(19, Strings.CRAFTER);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		if (inventory[18] == null || inventory[18].stackSize < inventory[18].getMaxStackSize()) {
			ItemStack output = CraftingManager.getInstance().findMatchingRecipe(grid, worldObj);
			onPickupFromSlot(output);
		}
	}

	private void onPickupFromSlot(ItemStack stack) {
		EntityPlayer player = Utils.getPlayer(worldObj);

		FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, grid);
		stack.onCrafting(player.worldObj, player, stack.stackSize);

		for (int i = 0; i < grid.getSizeInventory(); i++) {
			ItemStack itemstack1 = grid.getStackInSlot(i);

			if (itemstack1 != null) {
				grid.decrStackSize(i, 1);

				if (itemstack1.getItem().hasContainerItem(itemstack1)) {
					ItemStack itemstack2 = itemstack1.getItem().getContainerItem(itemstack1);

					if (itemstack2 != null && itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage()) {
						MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, itemstack2));
						continue;
					}

					if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !player.inventory.addItemStackToInventory(itemstack2))
						if (grid.getStackInSlot(i) == null)
							grid.setInventorySlotContents(i, itemstack2);
						else
							player.dropPlayerItemWithRandomChoice(itemstack2, false);
				}
			}
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return ACCESSIBLE_SLOTS;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 18;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot >= 0 && slot <= 8;
	}

	class CrafterGrid extends InventoryCrafting {

		private static final int OFFSET = 9;
		private final TileEntityCrafter parent;

		public CrafterGrid(TileEntityCrafter parent) {
			super(null, 3, 3);
			this.parent = parent;
		}

		@Override
		public int getSizeInventory() {
			return 9;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return parent.getStackInSlot(OFFSET + slot);
		}

		@Override
		public ItemStack decrStackSize(int slot, int size) {
			return parent.decrStackSize(OFFSET + slot, size);
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			return parent.getStackInSlotOnClosing(OFFSET + slot);
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			parent.setInventorySlotContents(OFFSET + slot, stack);
		}

		@Override
		public String getInventoryName() {
			return parent.getInventoryName();
		}

		@Override
		public boolean hasCustomInventoryName() {
			return parent.hasCustomInventoryName();
		}

		@Override
		public int getInventoryStackLimit() {
			return 1;
		}

		@Override
		public void markDirty() {
			parent.markDirty();
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player) {
			return parent.isUseableByPlayer(player);
		}

		@Override
		public void openInventory() {
			parent.openInventory();
		}

		@Override
		public void closeInventory() {
			parent.closeInventory();
		}

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
			return true;
		}
	}
}