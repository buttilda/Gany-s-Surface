package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.blocks.OnePointEight.Stones18;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.EnumColour;
import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.entity.item.EntityPainting.EnumArt;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ModRecipes {

	public static String[] dyes = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };

	public static void init() {
		if (GanysSurface.enableEncasers)
			RecipeSorter.register(Reference.MOD_ID + ".StorageCaseRecipe", StorageCaseRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
		if (GanysSurface.enableDyedArmour)
			RecipeSorter.register(Reference.MOD_ID + ".RecipeArmourDyes", RecipeArmourDyes.class, Category.SHAPELESS, "after:minecraft:shapeless");
		if (GanysSurface.enableBanners) {
			RecipeSorter.register(Reference.MOD_ID + ".RecipeDuplicatePattern", RecipeDuplicatePattern.class, Category.SHAPELESS, "after:minecraft:shapeless");
			RecipeSorter.register(Reference.MOD_ID + ".RecipeAddPattern", RecipeAddPattern.class, Category.SHAPED, "after:minecraft:shaped");
		}

		registerOreDictionary();

		registerBlockRecipes();
		registerItemRecipes();

		add18Recipes();

		tweakRecipes();

		if (GanysSurface.enableOMC)
			OrganicMatterRegistry.init();
	}

	private static void tweakRecipes() {
		if (GanysSurface.enableDoors) {
			Items.wooden_door.setMaxStackSize(64);
			Items.iron_door.setMaxStackSize(64);
			Items.wooden_door.setTextureName(Utils.getItemTexture("door_wood"));
			Items.iron_door.setTextureName(Utils.getItemTexture("door_iron"));
			removeFirstRecipeFor(Items.wooden_door);
			removeFirstRecipeFor(Items.iron_door);
		}

		if (GanysSurface.enableChests)
			removeFirstRecipeFor(Blocks.chest);

		if (GanysSurface.enableFences) {
			removeFirstRecipeFor(Blocks.fence);
			removeFirstRecipeFor(Blocks.fence_gate);
			Blocks.fence.setCreativeTab(null);
			Blocks.fence_gate.setCreativeTab(null);
		}

		if (GanysSurface.enableBurnableBlocks) {
			Blocks.fire.setFireInfo(Blocks.fence_gate, 5, 20);
			Blocks.fire.setFireInfo(Blocks.fence, 5, 20);
			Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
		}

		if (GanysSurface.enableWoodenButtons)
			removeFirstRecipeFor(Blocks.wooden_button);

		if (GanysSurface.enableWoodenPressurePlates)
			removeFirstRecipeFor(Blocks.wooden_pressure_plate);

		if (GanysSurface.enableWoodenTrapdoors)
			removeFirstRecipeFor(Blocks.trapdoor);

		if (GanysSurface.enableWoodenLadders)
			removeFirstRecipeFor(Items.stick);

		if (GanysSurface.enableWoodenLadders)
			removeFirstRecipeFor(Blocks.ladder);

		if (GanysSurface.enableWoodenSigns)
			removeFirstRecipeFor(Items.sign);

		if (GanysSurface.enableWoodenBookshelves)
			removeFirstRecipeFor(Blocks.bookshelf);
	}

	private static void registerOreDictionary() {
		OreDictionary.registerOre("chestWood", new ItemStack(Blocks.chest));
		OreDictionary.registerOre("trapdoorWood", Blocks.trapdoor);

		if (GanysSurface.enableColouredRedstone)
			for (int i = 0; i < 16; i++) {
				if (i == 1)
					continue;
				OreDictionary.registerOre("dustRedstone", new ItemStack(ModItems.colouredRedstone, 1, i));
				OreDictionary.registerOre("blockRedstone", new ItemStack(ModBlocks.colouredRedstoneBlock, 1, i));
			}

		if (GanysSurface.enableChocolate)
			OreDictionary.registerOre("beansCocoa", new ItemStack(Items.dye, 1, 3));

		if (GanysSurface.enablePrismarineStuff) {
			OreDictionary.registerOre("shardPrismarine", new ItemStack(ModItems.prismarineItems, 1, 0));
			OreDictionary.registerOre("crystalPrismarine", new ItemStack(ModItems.prismarineItems, 1, 1));
			OreDictionary.registerOre("blockPrismarine", new ItemStack(ModBlocks.prismarineBlocks, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (GanysSurface.enable18Stones) {
			OreDictionary.registerOre("stoneGranite", new ItemStack(ModBlocks.newStones, 1, Stones18.GRANITE));
			OreDictionary.registerOre("stoneDiorite", new ItemStack(ModBlocks.newStones, 1, Stones18.DIORITE));
			OreDictionary.registerOre("stoneAndesite", new ItemStack(ModBlocks.newStones, 1, Stones18.ANDESITE));
			OreDictionary.registerOre("stoneGranitePolished", new ItemStack(ModBlocks.newStones, 1, Stones18.POLISHED_GRANITE));
			OreDictionary.registerOre("stoneDioritePolished", new ItemStack(ModBlocks.newStones, 1, Stones18.POLISHED_DIORITE));
			OreDictionary.registerOre("stoneAndesitePolished", new ItemStack(ModBlocks.newStones, 1, Stones18.POLISHED_ANDESITE));
		}

		if (GanysSurface.enableBasalt) {
			OreDictionary.registerOre("stoneBasalt", new ItemStack(ModBlocks.basalt, 1, 0));
			OreDictionary.registerOre("stoneBasaltPolished", new ItemStack(ModBlocks.basalt, 1, 1));
		}

		if (GanysSurface.enableChests)
			for (Block chest : ModBlocks.chests)
				OreDictionary.registerOre("chestWood", new ItemStack(chest));

		if (GanysSurface.enableSpawnEggs) {
			OreDictionary.registerOre("mobEgg", ModItems.chargedCreeperSpawner);
			OreDictionary.registerOre("mobEgg", new ItemStack(ModItems.horseSpawner, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (GanysSurface.enableSlimeBlock)
			OreDictionary.registerOre("blockSlime", new ItemStack(ModBlocks.slimeBlock));

		if (GanysSurface.enableBlockOfCharcoal)
			OreDictionary.registerOre("blockCharcoal", new ItemStack(ModBlocks.charcoalBlock));

		if (GanysSurface.enableRedyeingBlocks) {
			OreDictionary.registerOre("clayHardened", new ItemStack(Blocks.hardened_clay));
			OreDictionary.registerOre("clayHardened", new ItemStack(Blocks.stained_hardened_clay, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (GanysSurface.enableOMC)
			OreDictionary.registerOre("itemSkull", new ItemStack(Items.skull, 1, OreDictionary.WILDCARD_VALUE));

		if (GanysSurface.enableBeetroots)
			OreDictionary.registerOre("cropBeetroot", ModItems.beetroot);

		if (GanysSurface.enableWoodenTrapdoors)
			for (Block trapdoor : ModBlocks.trapdoors)
				OreDictionary.registerOre("trapdoorWood", trapdoor);

		if (GanysSurface.enableIronTrapdoor)
			OreDictionary.registerOre("trapdoorIron", ModBlocks.ironTrapdoor);

		if (GanysSurface.enableWoodenLadders)
			OreDictionary.registerOre("stickWood", new ItemStack(ModItems.stick, 1, OreDictionary.WILDCARD_VALUE));

		if (GanysSurface.enableStorageBlocks)
			for (int i = 0; i < ModBlocks.storage.getBlockNumber(); i++)
				OreDictionary.registerOre(ModBlocks.storage.getOreName(i), new ItemStack(ModBlocks.storage, 1, i));

		if (GanysSurface.enableDyeBlocks)
			for (int i = 0; i < ModBlocks.dye.getBlockNumber(); i++)
				OreDictionary.registerOre(ModBlocks.dye.getOreName(i), new ItemStack(ModBlocks.dye, 1, i));
	}

	private static void registerItemRecipes() {
		if (GanysSurface.enableWoodenArmour) {
			addShapedRecipe(new ItemStack(ModItems.woodenHelmet), "xxx", "x x", 'x', "logWood");
			addShapedRecipe(new ItemStack(ModItems.woodenChestplate), "x x", "xxx", "xxx", 'x', "logWood");
			addShapedRecipe(new ItemStack(ModItems.woodenLeggings), "xxx", "x x", "x x", 'x', "logWood");
			addShapedRecipe(new ItemStack(ModItems.woodenBoots), "x x", "x x", 'x', "logWood");
		}

		if (GanysSurface.enableTea) {
			addShapedRecipe(new ItemStack(ModItems.teaBag), " y ", "yxy", " y ", 'x', ModItems.teaLeaves, 'y', Items.string);
			addShapedRecipe(new ItemStack(ModItems.emptyMug, 1, 1), "x x", "xxx", 'x', Items.clay_ball);
			GameRegistry.addSmelting(new ItemStack(ModItems.emptyMug, 1, 1), new ItemStack(ModItems.emptyMug), 0.0F);
			addShapedRecipe(new ItemStack(ModItems.cupOfTea), " x ", "yaz", " b ", 'x', Items.milk_bucket, 'y', Items.potionitem, 'z', Items.sugar, 'a', ModItems.teaBag, 'b', ModItems.emptyMug);
			addShapedRecipe(new ItemStack(ModItems.mankyCupOfTea), " y ", "xaz", " b ", 'x', Items.milk_bucket, 'y', Items.potionitem, 'z', Items.sugar, 'a', ModItems.teaBag, 'b', ModItems.emptyMug);
		}

		if (GanysSurface.enablePoop) {
			addShapedRecipe(new ItemStack(ModItems.rot, 4, 1), "xxx", "xyx", "xxx", 'x', new ItemStack(ModItems.poop, 1, 0), 'y', Blocks.dirt);
			addShapedRecipe(new ItemStack(ModItems.rot, 8, 1), "xxx", "xyx", "xxx", 'x', new ItemStack(ModItems.poop, 1, 1), 'y', Blocks.dirt);
		}

		if (GanysSurface.enableDislocators)
			addShapedRecipe(new ItemStack(ModItems.obsidianHead), " x ", "xyx", 'x', Blocks.obsidian, 'y', "ingotIron");

		if (GanysSurface.enableChocolate)
			addShapedRecipe(new ItemStack(ModItems.chocolateBar, 4), "xxx", "xyx", "xxx", 'x', "beansCocoa", 'y', Items.milk_bucket);

		if (GanysSurface.enableQuiver)
			addShapedRecipe(new ItemStack(ModItems.quiver), "xyx", "x x", "xxx", 'x', Items.leather, 'y', Items.string);

		if (GanysSurface.enableColouredRedstone)
			for (int i = 0; i < dyes.length; i++)
				if (i != 1) { // Skip Red
					addShapedRecipe(new ItemStack(ModItems.colouredRedstone, 8, i), "xxx", "xyx", "xxx", 'x', "dustRedstone", 'y', dyes[i]);
					addShapelessRecipe(new ItemStack(ModItems.colouredRedstone, 9, i), new ItemStack(ModBlocks.colouredRedstoneBlock, 1, i));
				} else
					addShapedRecipe(new ItemStack(Items.redstone, 8), "xxx", "xyx", "xxx", 'x', "dustRedstone", 'y', dyes[i]);

		if (GanysSurface.enableCookedEgg)
			GameRegistry.addSmelting(Items.egg, new ItemStack(ModItems.cookedEgg), 0.5F);

		if (GanysSurface.enablePineCones)
			addShapelessRecipe(new ItemStack(ModItems.pineNuts), ModItems.pineCone);

		if (GanysSurface.enablePocketCritters) {
			addShapedRecipe(new ItemStack(ModItems.batNet), "xyx", " x ", " x ", 'x', "stickWood", 'y', Items.string);
			addShapedRecipe(new ItemStack(ModItems.batStew), "xyz", " w ", 'x', Blocks.brown_mushroom, 'z', Blocks.red_mushroom, 'y', ModItems.pocketCritter, 'w', Items.bowl);
			GameRegistry.addSmelting(new ItemStack(ModItems.pocketCritter, 1, 1), new ItemStack(ModItems.roastedSquid), 0.1F);
		}

		if (GanysSurface.enableWorkTables)
			addShapedRecipe(new ItemStack(ModItems.portalDualWorkTable), "y ", " x", 'x', ModItems.batNet, 'y', ModBlocks.dualWorkTable);

		if (GanysSurface.enableEncasers)
			GameRegistry.addRecipe(new StorageCaseRecipe());

		if (GanysSurface.enableDyedArmour)
			GameRegistry.addRecipe(new RecipeArmourDyes());

		if (GanysSurface.enableAnalisers) {
			addShapedRecipe(new ItemStack(ModItems.horsalyser), "xyx", "xzx", "xwx", 'x', Items.leather, 'y', Items.flint, 'z', Blocks.glass_pane, 'w', "dustRedstone");
			addShapedRecipe(new ItemStack(ModItems.gearalyser), "xyx", "xzx", "xwx", 'x', "ingotIron", 'y', Items.flint, 'z', Blocks.glass_pane, 'w', "dustRedstone");
		}

		if (GanysSurface.enableRot)
			addShapedRecipe(new ItemStack(ModItems.rot, 4), "xxx", "xyx", "xxx", 'x', Items.rotten_flesh, 'y', Blocks.dirt);

		if (GanysSurface.enableWoodenWrench)
			addShapedRecipe(new ItemStack(ModItems.woodenWrench), "x x", " x ", " x ", 'x', "plankWood");

		if (GanysSurface.enableVillageFinder)
			addShapedRecipe(new ItemStack(ModItems.villageFinder), "xxx", "xyx", "xxx", 'x', Items.leather, 'y', Items.ender_pearl);

		if (GanysSurface.enableIcyPick)
			addShapedRecipe(new ItemStack(ModItems.icyPickaxe), "xxx", " y ", " y ", 'x', Blocks.ice, 'y', "stickWood");

		if (GanysSurface.enablePaintings)
			for (int i = 0; i < EnumArt.values().length; i++) {
				ItemStack middle;
				if (i <= 15)
					middle = new ItemStack(Blocks.carpet, 1, i);
				else
					middle = new ItemStack(Blocks.wool, 1, i - 16);
				addShapedRecipe(new ItemStack(ModItems.painting, 1, i), "xxx", "xyx", "xxx", 'x', "stickWood", 'y', middle);
			}

		if (GanysSurface.enableBeetroots) {
			addShapelessRecipe(new ItemStack(ModItems.beetrootSoup), "cropBeetroot", "cropBeetroot", "cropBeetroot", "cropBeetroot", Items.bowl);
			addShapelessRecipe(new ItemStack(Items.dye, 1, 1), "cropBeetroot");
		}

		if (GanysSurface.enableWoodenLadders) {
			for (int i = 0; i < BlockWood.field_150096_a.length - 1; i++)
				addShapedRecipe(new ItemStack(ModItems.stick, 4, i), "x", "x", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Items.stick, 4), "x", "x", 'x', "plankWood");
		}

		// Vanilla
		if (GanysSurface.enableExtraVanillaRecipes) {
			addShapedRecipe(new ItemStack(Items.clay_ball, 4), "xxx", "yzy", "xxx", 'x', Blocks.gravel, 'y', Blocks.dirt, 'z', Items.water_bucket);
			addShapelessRecipe(new ItemStack(Items.clay_ball, 4), Blocks.clay);
			addShapedRecipe(new ItemStack(Items.name_tag), " y ", "x  ", 'x', Items.paper, 'y', Items.string);
			addShapelessRecipe(new ItemStack(Items.fermented_spider_eye), Items.spider_eye, Items.sugar, Blocks.red_mushroom);
		}
	}

	private static void registerBlockRecipes() {
		if (GanysSurface.enablePlanter) {
			addShapedRecipe(new ItemStack(ModBlocks.farmManager), "xyx", "xzx", "xyx", 'x', ModBlocks.planter, 'y', "chestWood", 'z', "ingotGold");
			addShapedRecipe(new ItemStack(ModBlocks.planter), "aza", "ywy", " x ", 'x', Blocks.hopper, 'y', new ItemStack(Blocks.wool, 1, 13), 'z', Blocks.dispenser, 'w', ModBlocks.blockDetector, 'a', "stone");
		}

		if (GanysSurface.enableChocolate)
			addShapedRecipe(new ItemStack(ModBlocks.chocolateCake), "xxx", "yzy", "www", 'x', Items.milk_bucket, 'y', ModItems.chocolateBar, 'z', "egg", 'w', Items.wheat);

		if (GanysSurface.enableColouredRedstone)
			for (int i = 0; i < dyes.length; i++)
				if (i != 1)
					addShapedRecipe(new ItemStack(ModBlocks.colouredRedstoneBlock, 1, i), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.colouredRedstone, 1, i));

		if (GanysSurface.enableDislocators) {
			addShapedRecipe(new ItemStack(ModBlocks.dislocator), "zxz", "y y", "zyz", 'x', ModItems.obsidianHead, 'y', "plankWood", 'z', "ingotIron");
			addShapedRecipe(new ItemStack(ModBlocks.sensitiveDislocator), "wxw", "zyz", "wzw", 'x', ModBlocks.dislocator, 'y', ModBlocks.blockDetector, 'z', new ItemStack(Blocks.stone_slab, 1, 0), 'w', "ingotGold");
			addShapedRecipe(new ItemStack(ModBlocks.cubicSensitiveDislocator), "xxy", "xwx", "yxx", 'x', ModBlocks.sensitiveDislocator, 'y', "blockGlass", 'w', "gemDiamond");
			addShapedRecipe(new ItemStack(ModBlocks.blockDetector), "xyx", "yzy", "xyx", 'x', "dustRedstone", 'y', new ItemStack(Blocks.stone_slab, 1, 0), 'z', Items.comparator);
		}

		if (GanysSurface.enableItemDisplay)
			for (int i = 0; i < dyes.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.itemDisplay, 1, i), "xxx", "x x", "xyx", 'x', Blocks.glass_pane, 'y', new ItemStack(Blocks.carpet, 0, i));

		if (GanysSurface.enableWorkTables) {
			addShapedRecipe(new ItemStack(ModBlocks.dualWorkTable), "yyy", "x x", "yyy", 'x', ModBlocks.workTable, 'y', "plankWood");
			addShapelessRecipe(new ItemStack(ModBlocks.workTable), Blocks.crafting_table, "chestWood");
		}

		if (GanysSurface.enableLeafWalls) {
			addShapedRecipe(new ItemStack(ModBlocks.leafWall, 4, 0), "x", "x", 'x', new ItemStack(Blocks.leaves, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.leafWall, 4, 1), "x", "x", 'x', new ItemStack(Blocks.leaves, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.leafWall, 4, 2), "x", "x", 'x', new ItemStack(Blocks.leaves, 1, 2));
			addShapedRecipe(new ItemStack(ModBlocks.leafWall, 4, 3), "x", "x", 'x', new ItemStack(Blocks.leaves, 1, 3));
		}

		if (GanysSurface.enableDisguisedTrapdoors)
			for (int i = 0; i < ModBlocks.disguisedTrapdoors.length; i++)
				addShapelessRecipe(new ItemStack(ModBlocks.disguisedTrapdoors[i]), new ItemStack(Blocks.planks, 1, i), "trapdoorWood");

		if (GanysSurface.enableEncasers) {
			addShapedRecipe(new ItemStack(ModBlocks.encasingBench), "xyx", "yzy", "xyx", 'x', "ingotGold", 'y', Blocks.piston, 'z', "chestWood");
			addShapedRecipe(new ItemStack(ModBlocks.autoEncaser), "xyx", "yzy", "xyx", 'x', "gemDiamond", 'y', Blocks.piston, 'z', ModBlocks.encasingBench);
		}
		if (GanysSurface.enableOMC)
			addShapedRecipe(new ItemStack(ModBlocks.organicMatterCompressor), "zzz", "zxz", "zyz", 'x', Items.cauldron, 'y', "gemEmerald", 'z', Blocks.obsidian);

		if (GanysSurface.enableRainDetector)
			addShapedRecipe(new ItemStack(ModBlocks.rainDetector), "xyx", "yyy", "xyx", 'x', "gemEmerald", 'y', "slabWood");

		if (GanysSurface.enableCushion)
			addShapedRecipe(new ItemStack(ModBlocks.cushion), "zxz", "xyx", "zxz", 'x', Blocks.wool, 'y', dyes[5], 'z', "nuggetGold");

		if (GanysSurface.enableChestPropellant)
			addShapedRecipe(new ItemStack(ModBlocks.chestPropellant), "ywy", "xzx", "xyx", 'x', "ingotIron", 'y', "nuggetGold", 'z', new ItemStack(Blocks.sandstone, 1, 2), 'w', "dustRedstone");

		if (GanysSurface.enableFertilisedSoil)
			if (GanysSurface.enablePoop)
				addShapedRecipe(new ItemStack(ModBlocks.fertilizedSoil), "yyy", "xzx", "yyy", 'x', new ItemStack(ModItems.poop, 1, 1), 'y', new ItemStack(ModItems.rot, 1, 1), 'z', Blocks.dirt);
			else
				addShapedRecipe(new ItemStack(ModBlocks.fertilizedSoil), "yyy", "xzx", "yyy", 'x', new ItemStack(Items.fermented_spider_eye), 'y', new ItemStack(ModItems.rot), 'z', Blocks.dirt);

		if (GanysSurface.enableLantern)
			addShapelessRecipe(new ItemStack(ModBlocks.lantern), Blocks.glass, Blocks.torch);

		if (GanysSurface.enableInkHarvester)
			addShapedRecipe(new ItemStack(ModBlocks.inkHarvester), "xyx", "xzx", "xwx", 'x', new ItemStack(ModItems.pocketCritter, 1, 1), 'y', "dustRedstone", 'z', "blockIron", 'w', Items.golden_sword);

		if (GanysSurface.enableSlimeBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.slimeBlock), "xxx", "xyx", "xxx", 'x', "slimeball", 'y', Items.water_bucket);
			addShapelessRecipe(new ItemStack(Items.slime_ball, 8), ModBlocks.slimeBlock);
		}

		if (GanysSurface.enableBlockOfCharcoal) {
			addShapedRecipe(new ItemStack(ModBlocks.charcoalBlock), "xxx", "xxx", "xxx", 'x', new ItemStack(Items.coal, 1, 1));
			addShapelessRecipe(new ItemStack(Items.coal, 9, 1), new ItemStack(ModBlocks.charcoalBlock));
		}

		if (GanysSurface.enableChests) {
			for (int i = 0; i < ModBlocks.chests.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.chests[i]), "xxx", "x x", "xxx", 'x', new ItemStack(Blocks.planks, 1, i));
				addShapelessRecipe(new ItemStack(Blocks.chest), ModBlocks.chests[i]);
			}
			addShapedRecipe(new ItemStack(Blocks.chest), "xxx", "x x", "xxx", 'x', "plankWood");
		}

		if (GanysSurface.enableSlowRail)
			if (Loader.isModLoaded("Railcraft")) {
				ItemStack advancedRail = new ItemStack(GameRegistry.findItem("Railcraft", "part.rail"), 1, 1);
				ItemStack railBed = new ItemStack(GameRegistry.findItem("Railcraft", "part.railbed"));
				addShapedRecipe(new ItemStack(ModBlocks.slowRail, 16), "xyx", "xzx", "xyx", 'x', advancedRail, 'y', "slimeball", 'z', railBed);
			} else
				addShapedRecipe(new ItemStack(ModBlocks.slowRail, 6), "w w", "xyx", "wzw", 'x', "slimeball", 'y', "stickWood", 'z', "dustRedstone", 'w', "ingotIron");

		if (GanysSurface.enableBasalt) {
			if (GanysSurface.enable18Stones)
				addShapelessRecipe(new ItemStack(ModBlocks.basalt, 2, 0), "stoneDiorite", "stoneAndesite");
			else
				addShapelessRecipe(new ItemStack(ModBlocks.basalt, 2, 0), new ItemStack(Blocks.stone), new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE));

			addShapedRecipe(new ItemStack(ModBlocks.basalt, 4, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.basalt, 1, 0));
		}

		if (GanysSurface.enableWoodenButtons) {
			for (int i = 0; i < ModBlocks.buttons.length; i++) {
				Block button = ModBlocks.buttons[i];
				addShapelessRecipe(new ItemStack(button), new ItemStack(Blocks.planks, 1, i + 1));
				addShapelessRecipe(new ItemStack(Blocks.wooden_button), new ItemStack(button));
			}
			addShapelessRecipe(new ItemStack(Blocks.wooden_button), "plankWood");
		}

		if (GanysSurface.enableWoodenPressurePlates) {
			for (int i = 0; i < ModBlocks.pressurePlates.length; i++) {
				Block pressurePlate = ModBlocks.pressurePlates[i];
				addShapedRecipe(new ItemStack(pressurePlate), "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
				addShapelessRecipe(new ItemStack(Blocks.wooden_pressure_plate), new ItemStack(pressurePlate));
			}
			addShapedRecipe(new ItemStack(Blocks.wooden_pressure_plate), "xx", 'x', "plankWood");
		}

		if (GanysSurface.enablePoop)
			for (int i = 0; i < 2; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.blockOfPoop, 1, i), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.poop, 1, i));
				addShapelessRecipe(new ItemStack(ModItems.poop, 9, i), new ItemStack(ModBlocks.blockOfPoop, 1, i));
			}

		if (GanysSurface.enableWoodenTrapdoors) {
			for (int i = 0; i < ModBlocks.trapdoors.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.trapdoors[i], 2), "xxx", "xxx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Blocks.trapdoor, 2), "xxx", "xxx", 'x', "plankWood");
		}

		if (GanysSurface.enableWoodenLadders) {
			for (int i = 0; i < ModBlocks.ladders.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.ladders[i], 3), "x x", "xxx", "x x", 'x', new ItemStack(ModItems.stick, 1, i));
			addShapedRecipe(new ItemStack(Blocks.ladder, 3), "x x", "xxx", "x x", 'x', "stickWood");
		}

		if (GanysSurface.enableWoodenSigns) {
			for (int i = 0; i < ModBlocks.signs.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.signs[i], 3), "xxx", "xxx", " y ", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', "stickWood");
			addShapedRecipe(new ItemStack(Items.sign, 3), "xxx", "xxx", " y ", 'x', "plankWood", 'y', "stickWood");
		}

		if (GanysSurface.enableWoodenBookshelves) {
			for (int i = 0; i < BlockWood.field_150096_a.length - 1; i++)
				addShapedRecipe(new ItemStack(ModBlocks.bookshelf, 1, i), "xxx", "yyy", "xxx", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', new ItemStack(Items.book));
			addShapedRecipe(new ItemStack(Blocks.bookshelf), "xxx", "yyy", "xxx", 'x', "plankWood", 'y', new ItemStack(Items.book));
		}

		if (GanysSurface.enableStorageBlocks)
			for (int i = 0; i < ModBlocks.storage.getBlockNumber(); i++) {
				addShapedRecipe(new ItemStack(ModBlocks.storage, 1, i), "xxx", "xxx", "xxx", 'x', ModBlocks.storage.getStoredObjectIngredient(i));
				addShapelessRecipe(ModBlocks.storage.getStoredObjectResult(i), ModBlocks.storage.getOreName(i));
			}

		if (GanysSurface.enableDyeBlocks)
			for (int i = 0; i < ModBlocks.dye.getBlockNumber(); i++) {
				addShapedRecipe(new ItemStack(ModBlocks.dye, 1, i), "xxx", "xxx", "xxx", 'x', ModBlocks.dye.getStoredObjectIngredient(i));
				addShapelessRecipe(ModBlocks.dye.getStoredObjectResult(i), ModBlocks.dye.getOreName(i));
			}

		// Vanilla
		if (GanysSurface.enableExtraVanillaRecipes)
			addShapedRecipe(new ItemStack(Blocks.web), "x x", " y ", "x x", 'y', "slimeball", 'x', Items.string);

		if (GanysSurface.enableRedyeingBlocks) {
			String[] reDyes = dyes.clone();
			ArrayUtils.reverse(reDyes);
			for (int i = 0; i < 16; i++) {
				addShapedRecipe(new ItemStack(Blocks.stained_glass_pane, 8, i), "xxx", "xyx", "xxx", 'x', "paneGlass", 'y', reDyes[i]);
				addShapelessRecipe(new ItemStack(Blocks.stained_glass_pane, 1, i), "paneGlass", reDyes[i]);

				addShapedRecipe(new ItemStack(Blocks.stained_hardened_clay, 8, i), "xxx", "xyx", "xxx", 'x', "clayHardened", 'y', reDyes[i]);
				addShapelessRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, i), "clayHardened", reDyes[i]);

				addShapedRecipe(new ItemStack(Blocks.carpet, 8, i), "xxx", "xyx", "xxx", 'x', Blocks.carpet, 'y', reDyes[i]);
				addShapelessRecipe(new ItemStack(Blocks.carpet, 1, i), new ItemStack(Blocks.carpet, 1, OreDictionary.WILDCARD_VALUE), reDyes[i]);

				addShapedRecipe(new ItemStack(Blocks.stained_glass, 8, i), "xxx", "xyx", "xxx", 'x', Blocks.glass, 'y', reDyes[i]);
				addShapelessRecipe(new ItemStack(Blocks.stained_glass, 1, i), Blocks.glass, reDyes[i]);
			}
		}
	}

	private static void add18Recipes() {
		addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.vine));
		addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.vine));
		addShapedRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "x", "x", 'x', new ItemStack(Blocks.stone_slab, 1, 5));
		GameRegistry.addSmelting(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stonebrick, 1, 2), 0.0F);

		if (GanysSurface.enableCoarseDirt)
			addShapedRecipe(new ItemStack(ModBlocks.coarseDirt, 4), "xy", "yx", 'x', new ItemStack(Blocks.dirt), 'y', new ItemStack(Blocks.gravel));

		if (GanysSurface.enableMutton)
			GameRegistry.addSmelting(ModItems.rawMutton, new ItemStack(ModItems.cookedMutton), 1.0F);

		if (GanysSurface.enableIronTrapdoor)
			addShapedRecipe(new ItemStack(ModBlocks.ironTrapdoor), "xx", "xx", 'x', "ingotIron");

		if (GanysSurface.enable18Stones) {
			// Diorite
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.DIORITE), "xy", "yx", 'x', new ItemStack(Blocks.cobblestone), 'y', "gemQuartz");
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_DIORITE), "xx", "xx", 'x', "stoneDiorite");
			// Andesite
			addShapelessRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.ANDESITE), new ItemStack(Blocks.cobblestone), "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_ANDESITE), "xx", "xx", 'x', "stoneAndesite");
			// Granite
			addShapelessRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.GRANITE), "gemQuartz", "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_GRANITE), "xx", "xx", 'x', "stoneGranite");
		}

		if (GanysSurface.enablePrismarineStuff) {
			int PLAIN = 0;
			int BRICKS = 1;
			int DARK = 2;

			addShapedRecipe(new ItemStack(ModBlocks.prismarineBlocks, 1, DARK), "xxx", "xyx", "xxx", 'x', "shardPrismarine", 'y', "dyeBlack");
			addShapedRecipe(new ItemStack(ModBlocks.prismarineBlocks, 1, PLAIN), "xx", "xx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.prismarineBlocks, 1, BRICKS), "xxx", "xxx", "xxx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.seaLantern), "xyx", "yyy", "xyx", 'x', "shardPrismarine", 'y', "crystalPrismarine");
		}

		if (GanysSurface.enableDoors) {
			for (int i = 0; i < ModItems.doors.length; i++)
				addShapedRecipe(new ItemStack(ModItems.doors[i], 3), "xx", "xx", "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Items.wooden_door, 3), "xx", "xx", "xx", 'x', "plankWood");
			addShapedRecipe(new ItemStack(Items.iron_door, 3), "xx", "xx", "xx", 'x', "ingotIron");
		}

		if (GanysSurface.enableRedSandstone) {
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone), "xx", "xx", 'x', new ItemStack(Blocks.sand, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone, 1, 1), "x", "x", 'x', new ItemStack(ModBlocks.redSandstoneSlab));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone, 1, 2), "xx", "xx", 'x', new ItemStack(ModBlocks.redSandstone));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstoneSlab, 6), "xxx", 'x', ModBlocks.redSandstone);
			addShapedRecipe(new ItemStack(ModBlocks.redSandstoneStairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.redSandstone);
		}

		if (GanysSurface.enableFences) {
			for (int i = 0; i < ModBlocks.fences.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.fences[i], 3), "xyx", "xyx", 'x', new ItemStack(Blocks.planks, 1, i), 'y', "stickWood");
			addShapedRecipe(new ItemStack(ModBlocks.fences[0], 3), "xyx", "xyx", 'x', "plankWood", 'y', "stickWood");
			addShapelessRecipe(new ItemStack(Blocks.fence), ModBlocks.fences[0]);
			addShapelessRecipe(new ItemStack(ModBlocks.fences[0]), Blocks.fence);

			for (int i = 0; i < ModBlocks.gates.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.gates[i]), "yxy", "yxy", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', "stickWood");
			addShapedRecipe(new ItemStack(Blocks.fence_gate), "yxy", "yxy", 'x', "plankWood", 'y', "stickWood");
		}

		if (GanysSurface.enableBanners) {
			for (EnumColour colour : EnumColour.values())
				addShapedRecipe(new ItemStack(ModBlocks.banner, 1, colour.getDamage()), new Object[] { "xxx", "xxx", " y ", 'x', new ItemStack(Blocks.wool, 1, colour.getDamage()), 'y', "stickWood" });
			GameRegistry.addRecipe(new RecipeDuplicatePattern());
			GameRegistry.addRecipe(new RecipeAddPattern());
		}

		if (GanysSurface.enableSponge) {
			addShapelessRecipe(new ItemStack(ModBlocks.sponge), Blocks.sponge);
			addShapelessRecipe(new ItemStack(Blocks.sponge), ModBlocks.sponge);
		}
	}

	private static void addShapedRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, objects));
	}

	private static void addShapelessRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(output, objects));
	}

	private static void removeFirstRecipeFor(Block block) {
		removeFirstRecipeFor(Item.getItemFromBlock(block));
	}

	private static void removeFirstRecipeFor(Item item) {
		for (Object recipe : CraftingManager.getInstance().getRecipeList())
			if (recipe != null) {
				ItemStack stack = ((IRecipe) recipe).getRecipeOutput();
				if (stack != null && stack.getItem() == item) {
					CraftingManager.getInstance().getRecipeList().remove(recipe);
					return;
				}
			}
	}
}