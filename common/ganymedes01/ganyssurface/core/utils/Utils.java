package ganymedes01.ganyssurface.core.utils;

import ganymedes01.ganyssurface.lib.Reference;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Utils {

	private static EntityPlayer player;

	public static final String getUnlocalizedName(String name) {
		return Reference.MOD_ID + "." + name;
	}

	public static final String getBlockTexture(String name) {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + name;
	}

	public static final String getItemTexture(String name) {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + name;
	}

	public static final String getArmourTexture(String name, int layer) {
		return Reference.ARMOUR_TEXTURE_PATH + name.toLowerCase() + "_layer_" + layer + ".png";
	}

	public static final String getGUITexture(String name) {
		return Reference.GUI_TEXTURE_PATH + name + ".png";
	}

	public static final String getEntityTexture(String name) {
		return Reference.ENTITY_TEXTURE_PATH + name + ".png";
	}

	public static final String getConainerName(String name) {
		return "container." + Reference.MOD_ID + ":" + name;
	}

	public static final void dropStack(World world, int x, int y, int z, ItemStack stack) {
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			float f = 0.7F;
			double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static final int getColour(int R, int G, int B) {
		return new Color(R < 0 ? 0 : R, G < 0 ? 0 : G, B < 0 ? 0 : B).getRGB() & 0x00ffffff;
	}

	public static final ResourceLocation getResource(String path) {
		return new ResourceLocation(path);
	}

	public static final ArrayList<Integer> getRandomizedList(int min, int max) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = min; i < max; i++)
			list.add(i);
		Collections.shuffle(list);
		return list;
	}

	public static final boolean addEntitytoInventory(IInventory iinventory, EntityItem item) {
		boolean flag = addStackToInventory(iinventory, item.getEntityItem());
		if (item.getEntityItem().stackSize <= 0)
			item.setDead();
		return flag;
	}

	public static final boolean addStackToInventory(IInventory iinventory, ItemStack stack) {
		return addStackToInventory(iinventory, stack, -1);
	}

	public static final boolean addStackToInventory(IInventory iinventory, ItemStack stack, int side) {
		if (stack == null || stack.stackSize <= 0)
			return false;
		ArrayList<Integer> slots = getStackSlots(iinventory, stack, side);

		while (!slots.isEmpty() && stack.stackSize > 0) {
			for (Integer slot : slots)
				while (iinventory.getStackInSlot(slot).stackSize < iinventory.getStackInSlot(slot).getMaxStackSize() && iinventory.getStackInSlot(slot).stackSize < iinventory.getInventoryStackLimit() && stack.stackSize > 0) {
					iinventory.getStackInSlot(slot).stackSize++;
					stack.stackSize--;
				}
			slots = getStackSlots(iinventory, stack, side);
		}
		if (stack.stackSize <= 0)
			return true;
		else if (iinventory instanceof ISidedInventory) {
			for (int slot : ((ISidedInventory) iinventory).getAccessibleSlotsFromSide(side))
				if (insertStackToNullSlot(slot, iinventory, stack, side))
					return true;
		} else
			for (int i = 0; i < iinventory.getSizeInventory(); i++)
				if (insertStackToNullSlot(i, iinventory, stack, side))
					return true;
		return false;
	}

	private static final boolean insertStackToNullSlot(int i, IInventory iinventory, ItemStack stack, int side) {
		if (iinventory.getStackInSlot(i) == null && iinventory.isItemValidForSlot(i, stack)) {
			if (stack.stackSize > iinventory.getInventoryStackLimit()) {
				iinventory.setInventorySlotContents(i, new ItemStack(stack.itemID, iinventory.getInventoryStackLimit(), stack.getItemDamage()));
				stack.stackSize -= iinventory.getInventoryStackLimit();
			} else {
				iinventory.setInventorySlotContents(i, stack.copy());
				stack.stackSize = 0;
			}
			return true;
		}
		return false;
	}

	private static final ArrayList<Integer> getStackSlots(IInventory iinventory, ItemStack stack, int side) {
		ArrayList<Integer> slots = new ArrayList<Integer>();
		if (stack.stackSize <= 0)
			return slots;

		if (iinventory instanceof ISidedInventory && side > -1) {
			for (int slot : ((ISidedInventory) iinventory).getAccessibleSlotsFromSide(side))
				if (checkForStackSlots(slot, iinventory, stack))
					slots.add(slot);
		} else
			for (int i = 0; i < iinventory.getSizeInventory(); i++)
				if (checkForStackSlots(i, iinventory, stack))
					slots.add(i);
		return slots;
	}

	private static final boolean checkForStackSlots(int slot, IInventory iinventory, ItemStack stack) {
		if (iinventory.getStackInSlot(slot) != null && stack != null)
			if (iinventory.isItemValidForSlot(slot, stack))
				if (iinventory.getStackInSlot(slot).getItem() == stack.getItem())
					if (iinventory.getStackInSlot(slot).isItemEqual(stack))
						if (iinventory.getStackInSlot(slot).stackSize < iinventory.getStackInSlot(slot).getMaxStackSize())
							if (iinventory.getStackInSlot(slot).stackSize < iinventory.getInventoryStackLimit())
								return true;
		return false;
	}

	public static EntityPlayer getPlayer(World world) {
		if (player != null)
			return player;
		else {
			player = new EntityPlayer(world, "[" + Reference.CHANNEL_NAME + "]") {
				@Override
				public void sendChatToPlayer(ChatMessageComponent var1) {
				}

				@Override
				public boolean canCommandSenderUseCommand(int var1, String var2) {
					return false;
				}

				@Override
				public ChunkCoordinates getPlayerCoordinates() {
					return null;
				}
			};
			return player;
		}
	}
}