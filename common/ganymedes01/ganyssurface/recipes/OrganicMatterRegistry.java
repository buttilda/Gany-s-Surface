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
		addMatterYield(new ItemStack(Item.swordWood));
		addMatterYield(new ItemStack(Item.hoeWood));
		addMatterYield(new ItemStack(Item.axeWood));
		addMatterYield(new ItemStack(Item.shovelWood));
		addMatterYield(new ItemStack(Item.helmetLeather));
		addMatterYield(new ItemStack(Item.plateLeather));
		addMatterYield(new ItemStack(Item.legsLeather));
		addMatterYield(new ItemStack(Item.bootsLeather));
		addMatterYield(new ItemStack(Item.sign));
		addMatterYield(new ItemStack(Item.saddle));
		addMatterYield(new ItemStack(Item.book));
		addMatterYield(new ItemStack(Item.egg));
		addMatterYield(new ItemStack(Item.fishingRod));
		addMatterYield(new ItemStack(Item.itemFrame));
		addMatterYield(new ItemStack(Item.boat));
		addMatterYield(new ItemStack(Item.bone));
		addMatterYield(new ItemStack(Item.bed));
		addMatterYield(new ItemStack(Item.emptyMap));
		addMatterYield(new ItemStack(Item.map));
		addMatterYield(new ItemStack(Item.writableBook));
		addMatterYield(new ItemStack(Item.writtenBook));
		addMatterYield(new ItemStack(Item.carrotOnAStick));
		addMatterYield(new ItemStack(Item.enchantedBook));
		addMatterYield(new ItemStack(Item.nameTag));
		addMatterYield(new ItemStack(Item.sugar));
		addMatterYield(new ItemStack(Item.cake));
		addMatterYield(new ItemStack(Item.slimeBall));
		addMatterYield(new ItemStack(Item.paper));
		addMatterYield(new ItemStack(Item.reed));
		addMatterYield(new ItemStack(Item.leather));
		addMatterYield(new ItemStack(Item.doorWood));
		addMatterYield(new ItemStack(Item.leash));
		addMatterYield(new ItemStack(Item.wheat));
		addMatterYield(new ItemStack(Item.dyePowder));
		addMatterYield(new ItemStack(Item.dyePowder, 1, 15));

		addMatterYield(new ItemStack(ModItems.woodenBoots));
		addMatterYield(new ItemStack(ModItems.woodenLeggings));
		addMatterYield(new ItemStack(ModItems.woodenChestplate));
		addMatterYield(new ItemStack(ModItems.woodenHelmet));
		addMatterYield(new ItemStack(ModItems.woodenWrench));
		addMatterYield(new ItemStack(ModItems.rot));
		addMatterYield(new ItemStack(ModItems.rot, 1, 1));
		addMatterYield(new ItemStack(ModItems.teaLeaves));
		addMatterYield(new ItemStack(ModItems.teaBag));
		addMatterYield(new ItemStack(ModItems.poop));
		addMatterYield(new ItemStack(ModItems.poop, 1, 1));
		addMatterYield(new ItemStack(ModItems.batNet));
		addMatterYield(new ItemStack(ModItems.pocketBat), 3);
		addMatterYield(new ItemStack(ModItems.horsalyser));
		addMatterYield(new ItemStack(ModItems.horseSpawner));
		addMatterYield(new ItemStack(ModItems.horseSpawner, 1, 1));
		addMatterYield(new ItemStack(ModItems.horseSpawner, 1, 2));
		addMatterYield(new ItemStack(ModItems.chargedCreeperSpawner));

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