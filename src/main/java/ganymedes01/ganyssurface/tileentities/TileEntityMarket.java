package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityMarket extends GanysInventory implements ISidedInventory {

	@SideOnly(Side.CLIENT)
	private EntityItem entityItem;
	private String owner;

	public TileEntityMarket() {
		super(0, Strings.MARKET);
	}

	@SideOnly(Side.CLIENT)
	public EntityItem getEntityItem(int index) {
		ItemStack stack = inventory[index];
		if (stack == null)
			return null;

		if (entityItem == null) {
			entityItem = new EntityItem(worldObj);
			entityItem.hoverStart = 0;
		}

		entityItem.setEntityItemStack(stack);
		return entityItem;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	public void setOwner(EntityPlayer player) {
		owner = player.getCommandSenderName();
	}

	public String getOwner() {
		return owner;
	}
}