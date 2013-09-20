package ganymedes01.ganyssurface.tileentities;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerOrganicMatterCompressor;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.items.Rot;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemCarrotOnAStick;
import net.minecraft.item.ItemCoal;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSign;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class TileEntityOrganicMatterCompressor extends TileEntity implements ISidedInventory {

	private ItemStack[] inventory = new ItemStack[11];
	private final int BLOCK_OF_COAL = 10;
	private final int COAL = 9;
	private final int[] SLOTS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private final int WORK_TIME = 2400;
	private int currentTime, organicMatter;
	private final int NEEDED_MATTER = 144;

	public TileEntityOrganicMatterCompressor() {
		currentTime = WORK_TIME;
		organicMatter = 0;
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (yCoord > 15)
			return;
		if (inventory[BLOCK_OF_COAL] == null || inventory[BLOCK_OF_COAL].stackSize < 64) {
			if (organicMatter < NEEDED_MATTER)
				organicMatter = collectOrganicMatter();
			if (organicMatter >= NEEDED_MATTER)
				if (inventory[COAL] != null) {
					currentTime--;
					if (currentTime <= 0)
						generateBlockOfCoal();
				} else
					currentTime = WORK_TIME;
		}
	}

	private int collectOrganicMatter() {
		int matter = 0;
		for (int i = 0; i < 9; i++) {
			if (inventory[i] != null) {
				matter += getOrganicYield(inventory[i]);
				inventory[i].stackSize--;
				if (inventory[i].stackSize <= 0)
					inventory[i] = null;
			}
			if (organicMatter + matter >= NEEDED_MATTER)
				return organicMatter + matter;
		}
		return organicMatter + matter;
	}

	private void generateBlockOfCoal() {
		if (inventory[BLOCK_OF_COAL] == null)
			inventory[BLOCK_OF_COAL] = new ItemStack(Block.coalBlock);
		else
			inventory[BLOCK_OF_COAL].stackSize++;

		inventory[COAL].stackSize--;
		if (inventory[COAL].stackSize <= 0)
			inventory[COAL] = null;
		currentTime = WORK_TIME;
		organicMatter -= NEEDED_MATTER;
	}

	// 144 = Block of Coal
	// 16 = Coal
	private int getOrganicYield(ItemStack stack) {
		if (!isOrganicMatter(stack))
			return 0;

		Block block = null;
		if (stack.itemID < Block.blocksList.length)
			block = Block.blocksList[stack.itemID];
		if (block != null) {
			if (block.blockMaterial == Material.wood)
				if (!(block instanceof BlockHalfSlab))
					return 4;
			if (block.blockMaterial == Material.cactus || block.blockMaterial == Material.pumpkin || block.blockMaterial == Material.grass)
				return 4;
		}

		Item item = stack.getItem();
		if (item instanceof ItemSeeds)
			return 1;
		else if (item instanceof ItemCoal)
			if (stack.getItemDamage() == 1)
				return 16;

		for (ItemStack logs : OreDictionary.getOres("plankWood"))
			if (logs.getItem() == item)
				return 4;
		return 2;
	}

	public static boolean isOrganicMatter(ItemStack stack) {
		if (stack == null)
			return false;
		Item item = stack.getItem();

		if (item instanceof ItemFood)
			return true;
		else if (item instanceof ItemCoal)
			return stack.getItemDamage() == 1;
		else if (item instanceof ItemTool)
			return ((ItemTool) item).getToolMaterialName().compareToIgnoreCase("wood") == 0;
		else if (item instanceof ItemSword)
			return ((ItemSword) item).getToolMaterialName().compareToIgnoreCase("wood") == 0;
		else if (item instanceof ItemHoe)
			return ((ItemHoe) item).getMaterialName().compareToIgnoreCase("wood") == 0;
		else if (item instanceof ItemSeeds)
			return true;
		else if (item instanceof ItemArmor)
			return ((ItemArmor) item).getArmorMaterial() == EnumArmorMaterial.CLOTH || ((ItemArmor) item).getArmorMaterial().name().compareToIgnoreCase("wood") == 0 || ((ItemArmor) item).getArmorMaterial().name().compareToIgnoreCase("log") == 0;
		else if (item instanceof ItemHangingEntity)
			return true;
		else if (item instanceof ItemSign)
			return true;
		else if (item instanceof ItemSaddle)
			return true;
		else if (item instanceof ItemBoat)
			return true;
		else if (item instanceof ItemBook)
			return true;
		else if (item instanceof ItemEgg)
			return true;
		else if (item instanceof ItemFishingRod)
			return true;
		else if (item instanceof ItemBed)
			return true;
		else if (item instanceof ItemMonsterPlacer)
			return true;
		else if (item instanceof ItemWritableBook)
			return true;
		else if (item instanceof ItemEditableBook)
			return true;
		else if (item instanceof ItemEmptyMap)
			return true;
		else if (item instanceof ItemSkull)
			return true;
		else if (item instanceof ItemCarrotOnAStick)
			return true;
		else if (item instanceof ItemEnchantedBook)
			return true;
		else if (item instanceof ItemNameTag)
			return true;
		else if (item instanceof ItemRecord)
			return true;
		else if (item instanceof Rot)
			return true;
		else if (item == ModItems.woodenWrench)
			return true;
		else if (item == ModItems.teaLeaves)
			return true;
		else if (item == ModItems.poop)
			return true;
		else if (item == Item.sugar)
			return true;
		else if (item == Item.cake)
			return true;
		else if (item == Item.bone)
			return true;
		else if (item == Item.slimeBall)
			return true;
		else if (item == Item.paper)
			return true;
		else if (item == Item.reed)
			return true;
		else if (item == Item.leather)
			return true;
		else if (item == Item.doorWood)
			return true;
		else if (item == Item.wheat)
			return true;
		else if (item == Item.dyePowder)
			return stack.getItemDamage() == 0 || stack.getItemDamage() == 15;

		Block block = null;
		if (stack.itemID < Block.blocksList.length)
			block = Block.blocksList[stack.itemID];
		if (block != null)
			if (block.blockMaterial == Material.cactus || block.blockMaterial == Material.leaves || block.blockMaterial == Material.plants || block.blockMaterial == Material.pumpkin || block.blockMaterial == Material.vine || block.blockMaterial == Material.wood ||
			block.blockMaterial == Material.web || block.blockMaterial == Material.grass || block.blockMaterial == Material.cloth)
				return true;
			else if (block instanceof BlockLadder)
				return true;
			else if (block instanceof BlockButtonWood)
				return true;
			else if (block instanceof BlockCarpet)
				return true;

		for (ItemStack logs : OreDictionary.getOres("plankWood"))
			if (logs.getItem() == item)
				return true;
		for (ItemStack logs : OreDictionary.getOres("slabWood"))
			if (logs.getItem() == item)
				return true;
		for (ItemStack logs : OreDictionary.getOres("stairWood"))
			if (logs.getItem() == item)
				return true;
		for (ItemStack logs : OreDictionary.getOres("treeSapling"))
			if (logs.getItem() == item)
				return true;
		for (ItemStack logs : OreDictionary.getOres("treeLeaves"))
			if (logs.getItem() == item)
				return true;
		for (ItemStack logs : OreDictionary.getOres("stickWood"))
			if (logs.getItem() == item)
				return true;
		return false;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (inventory[slot] != null) {
			ItemStack stack;
			if (inventory[slot].stackSize <= size) {
				stack = inventory[slot];
				inventory[slot] = null;
				return stack;
			} else {
				stack = inventory[slot].splitStack(size);
				if (inventory[slot].stackSize == 0)
					inventory[slot] = null;
				return stack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (inventory[slot] != null) {
			ItemStack stack = inventory[slot];
			inventory[slot] = null;
			return stack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInvName() {
		return Utils.getConainerName(Strings.ORGANIC_MATTER_COMPRESSOR_NAME);
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 9)
			return stack.getItem() == Item.coal && stack.getItemDamage() == 0;
		else if (slot < 9)
			return isOrganicMatter(stack);
		else
			return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? new int[] { BLOCK_OF_COAL } : side == 1 ? new int[] { COAL } : SLOTS;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		NBTTagList nbttaglist = data.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
			if (b0 >= 0 && b0 < inventory.length)
				inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		currentTime = data.getInteger("currentTime");
		organicMatter = data.getInteger("organicMatter");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; ++i)
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		data.setTag("Items", nbttaglist);
		data.setInteger("currentTime", currentTime);
		data.setInteger("organicMatter", organicMatter);
	}

	public void getGUIData(int id, int value) {
		switch (id) {
			case 0:
				currentTime = value;
				break;
			case 1:
				organicMatter = value;
				break;
		}
	}

	public void sendGUIData(ContainerOrganicMatterCompressor compressor, ICrafting craft) {
		craft.sendProgressBarUpdate(compressor, 0, currentTime);
		craft.sendProgressBarUpdate(compressor, 1, organicMatter);
	}

	@SideOnly(Side.CLIENT)
	public float getOrganicMatter() {
		return (float) organicMatter / (float) NEEDED_MATTER;
	}

	@SideOnly(Side.CLIENT)
	public int getScaledWorkTime(int scale) {
		if (currentTime == 0)
			return 0;
		return scale - (int) (scale * ((float) currentTime / (float) WORK_TIME));
	}
}
