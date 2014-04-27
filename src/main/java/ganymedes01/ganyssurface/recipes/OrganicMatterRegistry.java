package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.core.utils.UnsizedStack;
import ganymedes01.ganyssurface.items.ModItems;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
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

	static {
		addMatterYield(new ItemStack(Item.coal), -1);
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
		addItemYield(ModItems.pocketCritter, 3);
		addItemYield(ModItems.horsalyser);

		addMatterYield(new ItemStack(Block.ladder));
		addMatterYield(new ItemStack(Block.woodenButton));

		addOreYield("mobEgg", 2);
		addOreYield("mobHead", 8);
		addOreYield("record", 4);
		addOreYield("plankWood", 4);
		addOreYield("slabWood", 2);
		addOreYield("stairWood", 2);
		addOreYield("treeSapling", 2);
		addOreYield("treeLeaves", 1);
		addOreYield("stickWood", 1);
		addOreYield("egg", 1);

		addMaterialYield(Material.cactus, 4);
		addMaterialYield(Material.leaves, 3);
		addMaterialYield(Material.plants, 3);
		addMaterialYield(Material.pumpkin, 3);
		addMaterialYield(Material.vine, 3);
		addMaterialYield(Material.web, 3);
		addMaterialYield(Material.grass, 4);
		addMaterialYield(Material.cloth, 3);
		addMaterialYield(Material.cake, 3);
		addMaterialYield(Material.materialCarpet, 2);
		addMaterialYield(Material.pumpkin, 4);
		addMaterialYield(Material.wood, 4);
	}

	private static void addItemYield(Item matter, int yield) {
		if (matter != null)
			addMatterYield(new ItemStack(matter), yield);
	}

	private static void addItemYield(Item matter) {
		addItemYield(matter, 2);
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

	public static int getOrganicYield(ItemStack stack) {
		if (stack == null)
			return -1;
		if (stack.getItem().itemID == Block.coalBlock.blockID)
			return -1;

		if (matterYield.containsKey(new UnsizedStack(stack)))
			return matterYield.get(new UnsizedStack(stack));
		else if (oreYield.containsKey(OreDictionary.getOreID(stack)))
			return oreYield.get(OreDictionary.getOreID(stack));

		int ret = -1;

		if (stack.getItem() instanceof ItemBlock && stack.itemID < Block.blocksList.length) {
			Block block = Block.blocksList[stack.itemID];
			Integer matYield = materialYield.get(block.blockMaterial);
			matYield = matYield == null ? -1 : matYield;
			ret = block instanceof BlockHalfSlab ? (int) (matYield / 2.0F) : matYield;
		} else {
			Item item = stack.getItem();
			if (item instanceof ItemFood)
				ret = (int) (10 * ((ItemFood) item).getSaturationModifier());
			else if (item instanceof ItemSeeds)
				ret = 1;
		}

		return ret;
	}
}