package ganymedes01.ganyssurface.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class SlotEncaser extends Slot {

	private final IInventory craftMatrix;
	private final EntityPlayer thePlayer;
	private int amountCrafted;

	public SlotEncaser(EntityPlayer par1EntityPlayer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6) {
		super(par3IInventory, par4, par5, par6);
		thePlayer = par1EntityPlayer;
		craftMatrix = par2IInventory;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int size) {
		if (getHasStack())
			amountCrafted += Math.min(size, getStack().stackSize);

		return super.decrStackSize(size);
	}

	@Override
	protected void onCrafting(ItemStack stack, int size) {
		amountCrafted += size;
		this.onCrafting(stack);
	}

	@Override
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(thePlayer.worldObj, thePlayer, amountCrafted);
		amountCrafted = 0;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, craftMatrix);
		this.onCrafting(stack);

		for (int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
			ItemStack itemstack1 = craftMatrix.getStackInSlot(i);

			if (itemstack1 != null)
				craftMatrix.decrStackSize(i, 1);
		}
	}
}