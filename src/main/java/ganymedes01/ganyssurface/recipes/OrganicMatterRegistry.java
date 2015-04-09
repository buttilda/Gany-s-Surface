package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.UnsizedStack;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class OrganicMatterRegistry {

	private static HashMap<UnsizedStack, Integer> matterYield = new HashMap<UnsizedStack, Integer>();
	private static HashMap<Integer, Integer> oreYield = new HashMap<Integer, Integer>();
	private static HashMap<Material, Integer> materialYield = new HashMap<Material, Integer>();

	public static void init() {
		addMatterYield(new ItemStack(Items.coal), -1);
		addMatterYield(new ItemStack(Items.coal, 1, 1), 16);
		addMatterYield(Items.wooden_sword);
		addMatterYield(Items.wooden_hoe);
		addMatterYield(Items.wooden_axe);
		addMatterYield(Items.wooden_shovel);
		addMatterYield(Items.leather_helmet);
		addMatterYield(Items.leather_chestplate);
		addMatterYield(Items.leather_leggings);
		addMatterYield(Items.leather_boots);
		addMatterYield(Items.sign);
		addMatterYield(Items.saddle);
		addMatterYield(Items.book);
		addMatterYield(Items.fishing_rod);
		addMatterYield(Items.item_frame);
		addMatterYield(Items.boat);
		addMatterYield(Items.bone);
		addMatterYield(Items.bed);
		addMatterYield(Items.filled_map);
		addMatterYield(Items.map);
		addMatterYield(Items.writable_book);
		addMatterYield(Items.written_book);
		addMatterYield(Items.carrot_on_a_stick);
		addMatterYield(Items.enchanted_book);
		addMatterYield(Items.name_tag);
		addMatterYield(Items.sugar);
		addMatterYield(Items.cake);
		addMatterYield(Items.slime_ball);
		addMatterYield(Items.paper);
		addMatterYield(Items.sugar);
		addMatterYield(Items.leather);
		addMatterYield(Items.wooden_door);
		addMatterYield(Items.lead);
		addMatterYield(Items.wheat);
		addMatterYield(Items.dye);
		addMatterYield(Items.reeds);
		addMatterYield(Items.feather);
		addMatterYield(Items.egg);
		addMatterYield(new ItemStack(Items.dye, 1, 15));

		if (GanysSurface.enableWoodenArmour) {
			addMatterYield(ModItems.woodenBoots);
			addMatterYield(ModItems.woodenLeggings);
			addMatterYield(ModItems.woodenChestplate);
			addMatterYield(ModItems.woodenHelmet);
		}
		if (GanysSurface.enablePoop) {
			addMatterYield(new ItemStack(ModItems.rot, 1, 1));
			addMatterYield(ModItems.poop);
			addMatterYield(new ItemStack(ModItems.poop, 1, 1));
			addMatterYield(new ItemStack(ModBlocks.blockOfPoop, 1, 0), 18);
			addMatterYield(new ItemStack(ModBlocks.blockOfPoop, 1, 1), 18);
		}
		if (GanysSurface.enablePocketCritters) {
			addMatterYield(ModItems.pocketCritter, 3);
			addMatterYield(ModItems.batNet);
		}
		if (GanysSurface.enableTea) {
			addMatterYield(ModItems.teaLeaves);
			addMatterYield(ModItems.teaBag);
		}
		if (GanysSurface.enableWoodenWrench)
			addMatterYield(ModItems.woodenWrench);
		if (GanysSurface.enableRot)
			addMatterYield(ModItems.rot);
		if (GanysSurface.enableAnalisers)
			addMatterYield(ModItems.horsalyser);
		if (GanysSurface.enablePineCones)
			addMatterYield(ModItems.pineCone);

		addMatterYield(new ItemStack(Blocks.ladder));
		addMatterYield(new ItemStack(Blocks.wooden_button));

		addOreYield("mobEgg", 2);
		addOreYield("itemSkull", 8);
		addOreYield("record", 4);
		addOreYield("plankWood", 4);
		addOreYield("slabWood", 2);
		addOreYield("stairWood", 2);
		addOreYield("treeSapling", 2);
		addOreYield("treeLeaves", 1);
		addOreYield("stickWood", 1);

		addMaterialYield(Material.cactus, 4);
		addMaterialYield(Material.leaves, 3);
		addMaterialYield(Material.plants, 3);
		addMaterialYield(Material.vine, 3);
		addMaterialYield(Material.web, 3);
		addMaterialYield(Material.grass, 4);
		addMaterialYield(Material.cloth, 3);
		addMaterialYield(Material.cake, 3);
		addMaterialYield(Material.carpet, 2);
		addMaterialYield(Material.gourd, 4);
		addMaterialYield(Material.wood, 4);
	}

	private static void addMatterYield(Item matter, int yield) {
		if (matter != null)
			addMatterYield(new ItemStack(matter), yield);
	}

	private static void addMatterYield(Item matter) {
		addMatterYield(matter, 2);
	}

	public static void addMatterYield(ItemStack matter, int yield) {
		if (matter != null)
			matterYield.put(new UnsizedStack(matter), yield);
	}

	private static void addMatterYield(ItemStack matter) {
		addMatterYield(matter, 2);
	}

	private static void addOreYield(String ore, int yield) {
		int oreID = OreDictionary.getOreID(ore);
		if (!oreYield.containsKey(oreID))
			oreYield.put(oreID, yield);
	}

	private static void addMaterialYield(Material material, int yield) {
		if (!materialYield.containsKey(material))
			materialYield.put(material, yield);
	}

	public static boolean isOrganicMatter(ItemStack stack) {
		return getOrganicYield(stack) > 0;
	}

	private static boolean mapContainsKeys(HashMap<Integer, Integer> map, ItemStack stack) {
		for (int id : OreDictionary.getOreIDs(stack))
			if (map.containsKey(id))
				return true;
		return false;
	}

	private static int getValue(HashMap<Integer, Integer> map, ItemStack stack) {
		for (int id : OreDictionary.getOreIDs(stack)) {
			Integer value = map.get(id);
			if (value != null)
				return value;
		}
		return -1;
	}

	public static int getOrganicYield(ItemStack stack) {
		if (stack == null)
			return -1;
		if (stack.getItem() == Item.getItemFromBlock(Blocks.coal_block))
			return -1;

		if (matterYield.containsKey(new UnsizedStack(stack)))
			return matterYield.get(new UnsizedStack(stack));
		else if (mapContainsKeys(oreYield, stack))
			return getValue(oreYield, stack);

		int ret = -1;

		if (stack.getItem() instanceof ItemBlock) {
			Block block = Block.getBlockFromItem(stack.getItem());
			Integer matYield = materialYield.get(block.getMaterial());
			matYield = matYield == null ? -1 : matYield;
			ret = block instanceof BlockSlab ? (int) (matYield / 2.0F) : matYield;
		} else {
			Item item = stack.getItem();
			if (item instanceof ItemFood)
				ret = (int) (10 * ((ItemFood) item).func_150906_h(stack));
			else if (item instanceof ItemSeeds)
				ret = 1;
		}

		return ret;
	}
}