package ganymedes01.ganyssurface.core.utils;

import ganymedes01.ganyssurface.lib.Reference;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.ReflectionHelper;

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

	public static final int[] getSlotsFromSide(IInventory iinventory, int side) {
		if (iinventory == null)
			return null;

		if (iinventory instanceof ISidedInventory)
			return ((ISidedInventory) iinventory).getAccessibleSlotsFromSide(side);
		else {
			int[] slots = new int[iinventory.getSizeInventory()];
			for (int i = 0; i < slots.length; i++)
				slots[i] = i;
			return slots;
		}
	}

	public static final ItemStack extractFromInventory(IInventory iinventory, int side) {
		if (iinventory instanceof TileEntityChest)
			return extractFromInventory(getInventoryFromChest((TileEntityChest) iinventory), side);
		return extractFromSlots(iinventory, side, getSlotsFromSide(iinventory, side));
	}

	private static ItemStack extractFromSlots(IInventory iinventory, int side, int[] slots) {
		for (int slot : slots) {
			ItemStack invtStack = iinventory.getStackInSlot(slot);
			if (invtStack != null)
				if (iinventory instanceof ISidedInventory ? ((ISidedInventory) iinventory).canExtractItem(slot, invtStack, side) : true) {
					ItemStack copy = invtStack.copy();
					invtStack.stackSize--;
					copy.stackSize = 1;
					return copy;
				}
		}
		return null;
	}

	public static final boolean addEntitytoInventory(IInventory iinventory, EntityItem item) {
		if (item == null)
			return false;

		boolean flag = addStackToInventory(iinventory, item.getEntityItem());
		if (item.getEntityItem().stackSize <= 0)
			item.setDead();
		return flag;
	}

	public static final boolean addStackToInventory(IInventory iinventory, ItemStack stack) {
		return addStackToInventory(iinventory, stack, 0);
	}

	public static final boolean addStackToInventory(IInventory iinventory, ItemStack stack, int side) {
		if (iinventory == null)
			return false;

		if (stack == null || stack.stackSize <= 0)
			return false;
		if (iinventory instanceof TileEntityChest)
			return addStackToInventory(getInventoryFromChest((TileEntityChest) iinventory), stack, side);

		return addToSlots(iinventory, stack, side, getSlotsFromSide(iinventory, side));
	}

	private static final boolean addToSlots(IInventory iinventory, ItemStack stack, int side, int[] slots) {
		for (int slot : slots) {
			if (iinventory instanceof ISidedInventory) {
				if (!((ISidedInventory) iinventory).canInsertItem(slot, stack, side))
					continue;
			} else if (!iinventory.isItemValidForSlot(slot, stack))
				continue;

			if (iinventory.getStackInSlot(slot) == null) {
				iinventory.setInventorySlotContents(slot, stack.copy());
				return true;
			} else {
				ItemStack invtStack = iinventory.getStackInSlot(slot);
				if (areStacksTheSame(invtStack, stack, false) && invtStack.stackSize < invtStack.getMaxStackSize()) {
					invtStack.stackSize += stack.stackSize;
					if (invtStack.stackSize > invtStack.getMaxStackSize()) {
						stack.stackSize = invtStack.stackSize - invtStack.getMaxStackSize();
						invtStack.stackSize = invtStack.getMaxStackSize();
					} else
						stack.stackSize = 0;
					return true;
				}
			}
		}
		return false;
	}

	public static final boolean areStacksTheSame(ItemStack stack1, ItemStack stack2, boolean matchSize) {
		if (stack1 == null || stack2 == null)
			return false;

		if (stack1.itemID == stack2.itemID)
			if (stack1.getItemDamage() == stack2.getItemDamage())
				if (!matchSize || stack1.stackSize == stack2.stackSize) {
					if (stack1.hasTagCompound())
						return stack2.hasTagCompound() ? stack1.getTagCompound().equals(stack2.getTagCompound()) : false;
					return true;
				}
		return false;
	}

	public static final IInventory getInventoryFromChest(TileEntityChest chest) {
		TileEntityChest adjacent = null;
		if (chest.adjacentChestXNeg != null)
			adjacent = chest.adjacentChestXNeg;
		if (chest.adjacentChestXNeg != null)
			adjacent = chest.adjacentChestXNeg;
		if (chest.adjacentChestXPos != null)
			adjacent = chest.adjacentChestXPos;
		if (chest.adjacentChestZNeg != null)
			adjacent = chest.adjacentChestZNeg;
		if (chest.adjacentChestZPosition != null)
			adjacent = chest.adjacentChestZPosition;
		if (adjacent != null)
			return new InventoryLargeChest("", chest, adjacent);

		return chest;
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

	public static ChunkCoordinates findNearestVillage(World world, int x, int y, int z) {
		try {
			Village village = null;
			float nearestDistance = Float.MAX_VALUE;
			List villageList = (List) ReflectionHelper.findField(VillageCollection.class, new String[] { "villageList", "field_75552_d" }).get(world.villageCollectionObj);
			Iterator iterator = villageList.iterator();

			while (iterator.hasNext()) {
				Village next = (Village) iterator.next();
				float distance = next.getCenter().getDistanceSquared(x, y, z);

				if (distance < nearestDistance) {
					village = next;
					nearestDistance = distance;
				}
			}

			return village != null ? village.getCenter() : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}