package ganymedes01.ganyssurface.core.utils;

import ganymedes01.ganyssurface.lib.Reference;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Utils {

	public static final String getUnlocalizedName(String name) {
		return Reference.MOD_ID + "." + name;
	}

	public static final String getBlockTexture(String name, boolean hasSubBlocks) {
		if (hasSubBlocks)
			return Reference.ITEM_BLOCK_TEXTURE_PATH + name + "_";
		else
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
		return new Color(R, G, B).getRGB() & 0x00ffffff;
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
		if (item.getEntityItem() == null || item.getEntityItem().stackSize <= 0)
			return false;
		ArrayList<Integer> slots = getStackSlots(iinventory, item.getEntityItem());

		while (slots.size() > 0 && item.getEntityItem().stackSize > 0) {
			for (Integer slot : slots)
				while (iinventory.getStackInSlot(slot).stackSize < iinventory.getStackInSlot(slot).getMaxStackSize() && item.getEntityItem().stackSize > 0) {
					iinventory.getStackInSlot(slot).stackSize++;
					item.getEntityItem().stackSize--;
				}
			slots = getStackSlots(iinventory, item.getEntityItem());
		}
		if (item.getEntityItem().stackSize <= 0) {
			item.setDead();
			return true;
		}

		for (int i = 0; i < iinventory.getSizeInventory(); i++)
			if (iinventory.getStackInSlot(i) == null) {
				iinventory.setInventorySlotContents(i, item.getEntityItem());
				item.setDead();
				return true;
			}
		return false;
	}

	public static final boolean addStacktoInventory(IInventory iinventory, ItemStack stack) {
		if (stack == null || stack.stackSize <= 0)
			return false;
		ArrayList<Integer> slots = getStackSlots(iinventory, stack);

		while (slots.size() > 0 && stack.stackSize > 0) {
			for (Integer slot : slots)
				while (iinventory.getStackInSlot(slot).stackSize < iinventory.getStackInSlot(slot).getMaxStackSize() && stack.stackSize > 0) {
					iinventory.getStackInSlot(slot).stackSize++;
					stack.stackSize--;
				}
			slots = getStackSlots(iinventory, stack);
		}
		if (stack.stackSize <= 0)
			return true;

		for (int i = 0; i < iinventory.getSizeInventory(); i++)
			if (iinventory.getStackInSlot(i) == null) {
				iinventory.setInventorySlotContents(i, stack.copy());
				stack.stackSize = 0;
				return true;
			}
		return false;
	}

	private static final ArrayList<Integer> getStackSlots(IInventory iinventory, ItemStack stack) {
		ArrayList<Integer> slots = new ArrayList<Integer>();
		if (stack.stackSize > 0)
			for (int i = 0; i < iinventory.getSizeInventory(); i++)
				if (iinventory.getStackInSlot(i) != null && stack != null)
					if (iinventory.getStackInSlot(i).getItem() == stack.getItem())
						if (iinventory.getStackInSlot(i).isItemEqual(stack))
							if (iinventory.getStackInSlot(i).stackSize < iinventory.getStackInSlot(i).getMaxStackSize())
								slots.add(i);
		return slots;
	}

	public static final String CHAT_COLOUR_BLACK = "\u00a70";
	public static final String CHAT_COLOUR_DARKBLUE = "\u00a71";
	public static final String CHAT_COLOUR_DARKGREEN = "\u00a72";
	public static final String CHAT_COLOUR_DARKAQUA = "\u00a73";
	public static final String CHAT_COLOUR_DARKRED = "\u00a74";
	public static final String CHAT_COLOUR_DARKPURPLE = "\u00a75";
	public static final String CHAT_COLOUR_GOLD = "\u00a76";
	public static final String CHAT_COLOUR_GREY = "\u00a77";
	public static final String CHAT_COLOUR_DARKGREY = "\u00a78";
	public static final String CHAT_COLOUR_BLUE = "\u00a79";
	public static final String CHAT_COLOUR_GREEN = "\u00a7a";
	public static final String CHAT_COLOUR_AQUA = "\u00a7b";
	public static final String CHAT_COLOUR_RED = "\u00a7c";
	public static final String CHAT_COLOUR_PUEPLE = "\u00a7d";
	public static final String CHAT_COLOUR_YELLOW = "\u00a7e";
	public static final String CHAT_COLOUR_WHITE = "\u00a7f";
}