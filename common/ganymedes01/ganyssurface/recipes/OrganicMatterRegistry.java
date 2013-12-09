package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.core.utils.ItemStackMap;
import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class OrganicMatterRegistry {

	private static ItemStackMap<Integer> matterYield = new ItemStackMap<Integer>();

	static {
		addMatterYield(new ItemStack(Item.coal, 1, 1), 16);
		addItemYield(Item.swordWood);
		addItemYield(Item.hoeWood);
		addItemYield(Item.axeWood);
		addItemYield(Item.shovelWood);
		addItemYield(Item.helmetLeather);
		addItemYield(Item.plateLeather);
		addItemYield(Item.legsLeather);
		addItemYield(Item.bootsLeather);
		addItemYield(Item.sign);
		addItemYield(Item.saddle);
		addItemYield(Item.book);
		addItemYield(Item.egg);
		addItemYield(Item.fishingRod);
		addItemYield(Item.itemFrame);
		addItemYield(Item.boat);
		addItemYield(Item.bone);
		addItemYield(Item.bed);
		addItemYield(Item.emptyMap);
		addItemYield(Item.map);
		addItemYield(Item.writableBook);
		addItemYield(Item.writtenBook);
		addItemYield(Item.carrotOnAStick);
		addItemYield(Item.enchantedBook);
		addItemYield(Item.nameTag);
		addItemYield(Item.sugar);
		addItemYield(Item.cake);
		addItemYield(Item.slimeBall);
		addItemYield(Item.paper);
		addItemYield(Item.reed);
		addItemYield(Item.leather);
		addItemYield(Item.doorWood);
		addItemYield(Item.leash);
		addItemYield(Item.wheat);
		addItemYield(Item.dyePowder);
		addMatterYield(new ItemStack(Item.dyePowder, 1, 15));

		addItemYield(ModItems.woodenBoots);
		addItemYield(ModItems.woodenLeggings);
		addItemYield(ModItems.woodenChestplate);
		addItemYield(ModItems.woodenHelmet);
		addItemYield(ModItems.woodenWrench);
		addItemYield(ModItems.rot);
		addMatterYield(new ItemStack(ModItems.rot, 1, 1));
		addItemYield(ModItems.teaLeaves);
		addItemYield(ModItems.teaBag);
		addItemYield(ModItems.poop);
		addMatterYield(new ItemStack(ModItems.poop, 1, 1));
		addItemYield(ModItems.batNet);
		addItemYield(ModItems.pocketBat, 3);
		addItemYield(ModItems.horsalyser);
		addItemYield(ModItems.horseSpawner);
		addMatterYield(new ItemStack(ModItems.horseSpawner, 1, 1));
		addMatterYield(new ItemStack(ModItems.horseSpawner, 1, 2));
		addItemYield(ModItems.chargedCreeperSpawner);

		addMatterYield(new ItemStack(Block.ladder));
		addMatterYield(new ItemStack(Block.woodenButton));
		addMatterYield(new ItemStack(Block.carpet));
		addMatterYield(new ItemStack(Block.hay));
		addMatterYield(new ItemStack(Block.pumpkin), 4);
		addMatterYield(new ItemStack(Block.pumpkinLantern), 4);
		addMatterYield(new ItemStack(Block.melon), 4);
		addMatterYield(new ItemStack(Block.cactus), 4);
		addMatterYield(new ItemStack(Block.grass), 4);
	}

	private static void addItemYield(Item matter, int yield) {
		if (matter != null)
			addMatterYield(new ItemStack(matter), yield);
	}

	private static void addItemYield(Item matter) {
		addItemYield(matter, 2);
	}

	public static void addMatterYield(ItemStack matter, int yield) {
		if (matter != null) {
			matter.stackSize = 1;

			matterYield.put(matter, yield);
		}
	}

	private static void addMatterYield(ItemStack matter) {
		addMatterYield(matter, 2);
	}

	public static boolean isOrganicMatter(ItemStack stack) {
		if (stack == null)
			return false;
		else
			return getOrganicYield(stack) > 0;
	}

	public static int getOrganicYield(ItemStack stack) {
		if (matterYield.containsKey(stack))
			return matterYield.get(stack);
		else {
			Item item = stack.getItem();

			if (item instanceof ItemFood)
				return 1;
			else if (item instanceof ItemSeeds)
				return 1;
			else if (item instanceof ItemMonsterPlacer)
				return 2;
			else if (item instanceof ItemSkull)
				return 2;
			else if (item instanceof ItemRecord)
				return 2;

			Block block = null;
			if (stack.itemID < Block.blocksList.length)
				block = Block.blocksList[stack.itemID];
			if (block != null)
				if (block.blockMaterial == Material.wood) {
					if (!(block instanceof BlockHalfSlab))
						return 4;
				} else if (block.blockMaterial == Material.cactus || block.blockMaterial == Material.leaves || block.blockMaterial == Material.plants || block.blockMaterial == Material.pumpkin || block.blockMaterial == Material.vine || block.blockMaterial == Material.web ||
				block.blockMaterial == Material.grass || block.blockMaterial == Material.cloth)
					return 3;

			for (ItemStack logs : OreDictionary.getOres("plankWood"))
				if (logs.getItem() == item)
					return 4;
			for (ItemStack logs : OreDictionary.getOres("slabWood"))
				if (logs.getItem() == item)
					return 2;
			for (ItemStack logs : OreDictionary.getOres("stairWood"))
				if (logs.getItem() == item)
					return 2;
			for (ItemStack logs : OreDictionary.getOres("treeSapling"))
				if (logs.getItem() == item)
					return 2;
			for (ItemStack logs : OreDictionary.getOres("treeLeaves"))
				if (logs.getItem() == item)
					return 1;
			for (ItemStack logs : OreDictionary.getOres("stickWood"))
				if (logs.getItem() == item)
					return 1;
		}
		return -1;
	}
}