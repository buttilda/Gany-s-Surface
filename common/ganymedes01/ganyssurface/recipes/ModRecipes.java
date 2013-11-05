package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ModRecipes {

	public static void init() {
		registerBlockRecipes();
		registerItemRecipes();
		registerArmourRecipes();
	}

	private static void registerArmourRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenHelmet), "xxx", "x x", 'x', "logWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenChestplate), "x x", "xxx", "xxx", 'x', "logWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenLeggings), "xxx", "x x", "x x", 'x', "logWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenBoots), "x x", "x x", 'x', "logWood"));
	}

	private static void registerItemRecipes() {
		GameRegistry.addRecipe(new ItemStack(ModItems.rot, 4), "xxx", "xyx", "xxx", 'x', Item.rottenFlesh, 'y', Block.dirt);
		GameRegistry.addRecipe(new ItemStack(ModItems.teaBag), " y ", "yxy", " y ", 'x', ModItems.teaLeaves, 'y', Item.silk);
		GameRegistry.addRecipe(new ItemStack(ModItems.emptyMug, 5), "x x", "xxx", 'x', Item.clay);
		GameRegistry.addRecipe(new ItemStack(ModItems.cupOfTea), " x ", "yaz", " b ", 'x', Item.bucketMilk, 'y', Item.potion, 'z', Item.sugar, 'a', ModItems.teaBag, 'b', ModItems.emptyMug);
		GameRegistry.addRecipe(new ItemStack(ModItems.mankyCupOfTea), " y ", "xaz", " b ", 'x', Item.bucketMilk, 'y', Item.potion, 'z', Item.sugar, 'a', ModItems.teaBag, 'b', ModItems.emptyMug);
		GameRegistry.addRecipe(new ItemStack(ModItems.rot, 8, 1), "xxx", "xyx", "xxx", 'x', new ItemStack(ModItems.poop, 1, 0), 'y', Block.dirt);
		GameRegistry.addRecipe(new ItemStack(ModItems.rot, 16, 1), "xxx", "xyx", "xxx", 'x', new ItemStack(ModItems.poop, 1, 1), 'y', Block.dirt);
		GameRegistry.addSmelting(Item.egg.itemID, new ItemStack(ModItems.cookedEgg), 0F);
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianHead), " x ", "xyx", 'x', Block.obsidian, 'y', Item.ingotIron);
		GameRegistry.addRecipe(new ItemStack(ModItems.woodenWrench), "x x", " x ", " x ", 'x', Block.planks);
		GameRegistry.addRecipe(new ItemStack(ModItems.batNet), "xyx", " x ", " x ", 'x', Item.stick, 'y', Item.silk);
		GameRegistry.addRecipe(new ItemStack(ModItems.batStew), "xyz", " w ", 'x', Block.mushroomBrown, 'z', Block.mushroomRed, 'y', ModItems.pocketBat, 'w', Item.bowlEmpty);
		if (GanysSurface.activateChocolate)
			GameRegistry.addRecipe(new ItemStack(ModItems.chocolateBar, 4), "xxx", "xyx", "xxx", 'x', new ItemStack(Item.dyePowder, 1, 3), 'y', Item.bucketMilk);
		GameRegistry.addRecipe(new ItemStack(ModItems.horsalyser), "xyx", "xzx", "xwx", 'x', Item.leather, 'y', Item.flint, 'z', Block.thinGlass, 'w', Item.redstone);

		// Vanilla
		GameRegistry.addRecipe(new ItemStack(Item.clay, 8), "xxx", "yzy", "xxx", 'x', Block.gravel, 'y', Block.dirt, 'z', Item.bucketWater);
		GameRegistry.addRecipe(new ItemStack(Item.nameTag), " y ", "x  ", 'x', Item.paper, 'y', Item.silk);
		GameRegistry.addRecipe(new ItemStack(Block.web), "x x", " y ", "x x", 'y', Item.slimeBall, 'x', Item.silk);
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
		GameRegistry.addRecipe(new ItemStack(ModBlocks.organicMatterCompressor), "yzy", "zxz", "zwz", 'x', Item.cauldron, 'y', Item.emerald, 'z', Block.obsidian, 'w', Block.blockIron);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.cushion), "zxz", "xyx", "zxz", 'x', Block.cloth, 'y', new ItemStack(Item.dyePowder, 1, 5), 'z', Item.goldNugget);
		if (GanysSurface.activateChocolate)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.chocolateCake), "xxx", "yzy", "www", 'x', Item.bucketMilk, 'y', ModItems.chocolateBar, 'z', Item.egg, 'w', Item.wheat);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.itemDisplay), "xxx", "x x", "xyx", 'x', Block.thinGlass, 'y', new ItemStack(Block.carpet, 0, 14));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.chestPropellant), "ywy", "xzx", "xyx", 'x', Item.ingotIron, 'y', Item.goldNugget, 'z', new ItemStack(Block.sandStone, 1, 2), 'w', Item.redstone);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.fertilizedSoil), "yyy", "xzx", "yyy", 'x', new ItemStack(ModItems.poop, 1, 1), 'y', new ItemStack(ModItems.rot, 1, 1), 'z', Block.dirt);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.planter), "aza", "ywy", " x ", 'x', Block.hopperBlock, 'y', new ItemStack(Block.cloth, 1, 13), 'z', Block.dispenser, 'w', ModBlocks.blockDetector, 'a', Block.stone);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.lantern), Block.glass, Block.torchWood);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.inkHarvester), "xzy", "yax", "xwy", 'x', new ItemStack(Item.dyePowder), 'y', new ItemStack(Item.dyePowder, 1, 4), 'z', Item.redstone, 'w', Item.swordGold, 'a', Block.blockIron);
	}
}