package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.items.ColouredRedstoneItem;
import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ModRecipes {

	private static String[] dyes = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };

	public static void init() {
		registerOreDictionary();

		registerBlockRecipes();
		registerItemRecipes();
		registerArmourRecipes();
	}

	private static void registerOreDictionary() {
		if (OreDictionary.getOres("egg").isEmpty())
			OreDictionary.registerOre("egg", new ItemStack(Item.egg));
		OreDictionary.registerOre("mobEgg", ModItems.chargedCreeperSpawner);
		OreDictionary.registerOre("mobEgg", ModItems.horseSpawner);
	}

	private static void registerArmourRecipes() {
		if (GanysSurface.enableWoodenArmour) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenHelmet), "xxx", "x x", 'x', "logWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenChestplate), "x x", "xxx", "xxx", 'x', "logWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenLeggings), "xxx", "x x", "x x", 'x', "logWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenBoots), "x x", "x x", 'x', "logWood"));
		}
		GameRegistry.addRecipe(new RecipesIronArmourDyes());
	}

	private static void registerItemRecipes() {
		GameRegistry.addRecipe(new ItemStack(ModItems.rot, 4), "xxx", "xyx", "xxx", 'x', Item.rottenFlesh, 'y', Block.dirt);
		GameRegistry.addRecipe(new ItemStack(ModItems.teaBag), " y ", "yxy", " y ", 'x', ModItems.teaLeaves, 'y', Item.silk);
		GameRegistry.addRecipe(new ItemStack(ModItems.emptyMug, 5), "x x", "xxx", 'x', Item.clay);
		GameRegistry.addRecipe(new ItemStack(ModItems.cupOfTea), " x ", "yaz", " b ", 'x', Item.bucketMilk, 'y', Item.potion, 'z', Item.sugar, 'a', ModItems.teaBag, 'b', ModItems.emptyMug);
		GameRegistry.addRecipe(new ItemStack(ModItems.mankyCupOfTea), " y ", "xaz", " b ", 'x', Item.bucketMilk, 'y', Item.potion, 'z', Item.sugar, 'a', ModItems.teaBag, 'b', ModItems.emptyMug);
		GameRegistry.addRecipe(new ItemStack(ModItems.rot, 8, 1), "xxx", "xyx", "xxx", 'x', new ItemStack(ModItems.poop, 1, 0), 'y', Block.dirt);
		GameRegistry.addRecipe(new ItemStack(ModItems.rot, 16, 1), "xxx", "xyx", "xxx", 'x', new ItemStack(ModItems.poop, 1, 1), 'y', Block.dirt);
		for (ItemStack egg : OreDictionary.getOres("egg"))
			FurnaceRecipes.smelting().addSmelting(egg.itemID, egg.getItemDamage(), new ItemStack(ModItems.cookedEgg), 0.5F);

		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianHead), " x ", "xyx", 'x', Block.obsidian, 'y', Item.ingotIron);
		GameRegistry.addRecipe(new ItemStack(ModItems.woodenWrench), "x x", " x ", " x ", 'x', Block.planks);
		GameRegistry.addRecipe(new ItemStack(ModItems.batNet), "xyx", " x ", " x ", 'x', Item.stick, 'y', Item.silk);
		GameRegistry.addRecipe(new ItemStack(ModItems.batStew), "xyz", " w ", 'x', Block.mushroomBrown, 'z', Block.mushroomRed, 'y', ModItems.pocketBat, 'w', Item.bowlEmpty);
		if (GanysSurface.activateChocolate)
			GameRegistry.addRecipe(new ItemStack(ModItems.chocolateBar, 4), "xxx", "xyx", "xxx", 'x', new ItemStack(Item.dyePowder, 1, 3), 'y', Item.bucketMilk);
		GameRegistry.addRecipe(new ItemStack(ModItems.horsalyser), "xyx", "xzx", "xwx", 'x', Item.leather, 'y', Item.flint, 'z', Block.thinGlass, 'w', Item.redstone);

		for (int i = 0; i < dyes.length; i++)
			for (ItemStack dye : OreDictionary.getOres(dyes[i]))
				if (i != 1) { // Skip Red
					GameRegistry.addRecipe(MultipleItemsRecipe.createNewRecipe(new ItemStack(ModItems.colouredRedstone, 8, i), "xxx", "xyx", "xxx", 'x', ColouredRedstoneItem.getRedstonesForRecipe(), 'y', dye));
					GameRegistry.addShapelessRecipe(new ItemStack(ModItems.colouredRedstone, 9, i), new ItemStack(ModBlocks.colouredRedstoneBlock, 1, i));
				} else
					GameRegistry.addRecipe(MultipleItemsRecipe.createNewRecipe(new ItemStack(Item.redstone, 8), "xxx", "xyx", "xxx", 'x', ColouredRedstoneItem.getRedstonesForRecipe(), 'y', dye));
		GameRegistry.addRecipe(new ItemStack(ModItems.villageFinder), "xxx", "xyx", "xxx", 'x', Item.leather, 'y', Item.enderPearl);
		GameRegistry.addRecipe(new ItemStack(ModItems.portalDualWorkTable), "y ", " x", 'x', ModItems.batNet, 'y', ModBlocks.dualWorkTable);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.icyPickaxe), "xxx", " y ", " y ", 'x', Block.ice, 'y', "stickWood"));
		FurnaceRecipes.smelting().addSmelting(ModItems.pocketBat.itemID, 1, new ItemStack(ModItems.roastedSquid), 0.5F);
		GameRegistry.addRecipe(new StorageCaseRecipe());

		// Vanilla
		GameRegistry.addRecipe(new ItemStack(Item.clay, 8), "xxx", "yzy", "xxx", 'x', Block.gravel, 'y', Block.dirt, 'z', Item.bucketWater);
		GameRegistry.addRecipe(new ItemStack(Item.nameTag), " y ", "x  ", 'x', Item.paper, 'y', Item.silk);
		GameRegistry.addShapelessRecipe(new ItemStack(Item.slimeBall, 8), ModBlocks.slimeBlock);
	}

	private static void registerBlockRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dislocator), "zxz", "y y", "zyz", 'x', ModItems.obsidianHead, 'y', "plankWood", 'z', Item.ingotIron));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.sensoringDislocator), "wxw", "zyz", "wzw", 'x', ModBlocks.dislocator, 'y', ModBlocks.blockDetector, 'z', new ItemStack(Block.stoneSingleSlab, 1, 0), 'w', Item.ingotGold);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.cubicSensoringDislocator), "xxy", "xwx", "yxx", 'x', ModBlocks.sensoringDislocator, 'y', Block.glass, 'w', Item.diamond);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.rainDetector), "xyx", "yyy", "xyx", 'x', Item.emerald, 'y', "slabWood"));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockDetector), "xyx", "yzy", "xyx", 'x', Item.redstone, 'y', new ItemStack(Block.stoneSingleSlab, 1, 0), 'z', Item.comparator);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.disguisedTrapDoorOak), new ItemStack(Block.planks, 1, 0), Block.trapdoor);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.disguisedTrapDoorSpruce), new ItemStack(Block.planks, 1, 1), Block.trapdoor);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.disguisedTrapDoorBirch), new ItemStack(Block.planks, 1, 2), Block.trapdoor);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.disguisedTrapDoorJungle), new ItemStack(Block.planks, 1, 3), Block.trapdoor);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.workTable), Block.workbench, Block.chest);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.organicMatterCompressor), "zzz", "zxz", "zyz", 'x', Item.cauldron, 'y', Item.emerald, 'z', Block.obsidian);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.cushion), "zxz", "xyx", "zxz", 'x', Block.cloth, 'y', dyes[5], 'z', Item.goldNugget));
		if (GanysSurface.activateChocolate)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.chocolateCake), "xxx", "yzy", "www", 'x', Item.bucketMilk, 'y', ModItems.chocolateBar, 'z', "egg", 'w', Item.wheat));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.chestPropellant), "ywy", "xzx", "xyx", 'x', Item.ingotIron, 'y', Item.goldNugget, 'z', new ItemStack(Block.sandStone, 1, 2), 'w', Item.redstone);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.fertilizedSoil), "yyy", "xzx", "yyy", 'x', new ItemStack(ModItems.poop, 1, 1), 'y', new ItemStack(ModItems.rot, 1, 1), 'z', Block.dirt);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.planter), "aza", "ywy", " x ", 'x', Block.hopperBlock, 'y', new ItemStack(Block.cloth, 1, 13), 'z', Block.dispenser, 'w', ModBlocks.blockDetector, 'a', Block.stone);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.lantern), Block.glass, Block.torchWood);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.inkHarvester), "xyx", "xzx", "xwx", 'x', new ItemStack(ModItems.pocketBat, 1, 1), 'y', Item.redstone, 'z', Block.blockIron, 'w', Item.swordGold);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.slimeBlock), "xxx", "xyx", "xxx", 'x', Item.slimeBall, 'y', Item.bucketWater);
		for (int i = 0; i < dyes.length; i++) {
			if (i != 1)
				GameRegistry.addRecipe(new ItemStack(ModBlocks.colouredRedstoneBlock, 1, i), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.colouredRedstone, 1, i));
			GameRegistry.addRecipe(new ItemStack(ModBlocks.itemDisplay, 1, i), "xxx", "x x", "xyx", 'x', Block.thinGlass, 'y', new ItemStack(Block.carpet, 0, i));
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dualWorkTable), "yyy", "x x", "yyy", 'x', ModBlocks.workTable, 'y', "plankWood"));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.market), "xzx", "xyx", "xzx", 'x', Item.ingotIron, 'y', new ItemStack(Item.dyePowder), 'z', Block.chest);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.farmManager), "xyx", "xzx", "xyx", 'x', ModBlocks.planter, 'y', Block.chest, 'z', Item.ingotGold);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.encasingBench), "xyx", "yzy", "xyx", 'x', Item.ingotGold, 'y', Block.pistonBase, 'z', Block.chest);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.autoEncaser), "xyx", "yzy", "xyx", 'x', Item.diamond, 'y', Block.pistonBase, 'z', ModBlocks.encasingBench);

		// Vanilla
		GameRegistry.addRecipe(new ItemStack(Block.web), "x x", " y ", "x x", 'y', Item.slimeBall, 'x', Item.silk);
	}
}