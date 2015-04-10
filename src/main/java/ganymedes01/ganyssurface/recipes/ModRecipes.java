package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.blocks.Stones18;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityPainting.EnumArt;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.commons.lang3.ArrayUtils;

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
		if (GanysSurface.enableEncasers)
			RecipeSorter.register("StorageCaseRecipe", StorageCaseRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("RecipeArmourDyes", RecipeArmourDyes.class, Category.SHAPELESS, "after:minecraft:shapeless");

		registerOreDictionary();

		registerBlockRecipes();
		registerItemRecipes();

		add18Recipes();

		if (GanysSurface.enableOMC)
			OrganicMatterRegistry.init();
	}

	private static void registerOreDictionary() {
		OreDictionary.registerOre("chestWood", new ItemStack(Blocks.chest));

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

		if (GanysSurface.enableBlockOfFlint)
			OreDictionary.registerOre("blockFlint", new ItemStack(ModBlocks.flint));

		if (GanysSurface.enableBeetroots)
			OreDictionary.registerOre("cropBeetroot", ModItems.beetroot);
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
			addShapedRecipe(new ItemStack(ModItems.batNet), "xyx", " x ", " x ", 'x', Items.stick, 'y', Items.string);
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
			addShapedRecipe(new ItemStack(ModItems.horsalyser), "xyx", "xzx", "xwx", 'x', Items.leather, 'y', Items.flint, 'z', Blocks.glass_pane, 'w', Items.redstone);
			addShapedRecipe(new ItemStack(ModItems.gearalyser), "xyx", "xzx", "xwx", 'x', "ingotIron", 'y', Items.flint, 'z', Blocks.glass_pane, 'w', Items.redstone);
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
				addShapelessRecipe(new ItemStack(ModBlocks.disguisedTrapdoors[i]), new ItemStack(Blocks.planks, 1, i), Blocks.trapdoor);

		if (GanysSurface.enableEncasers) {
			addShapedRecipe(new ItemStack(ModBlocks.encasingBench), "xyx", "yzy", "xyx", 'x', Items.gold_ingot, 'y', Blocks.piston, 'z', "chestWood");
			addShapedRecipe(new ItemStack(ModBlocks.autoEncaser), "xyx", "yzy", "xyx", 'x', Items.diamond, 'y', Blocks.piston, 'z', ModBlocks.encasingBench);
		}
		if (GanysSurface.enableOMC)
			addShapedRecipe(new ItemStack(ModBlocks.organicMatterCompressor), "zzz", "zxz", "zyz", 'x', Items.cauldron, 'y', Items.emerald, 'z', Blocks.obsidian);

		if (GanysSurface.enableRainDetector)
			addShapedRecipe(new ItemStack(ModBlocks.rainDetector), "xyx", "yyy", "xyx", 'x', Items.emerald, 'y', "slabWood");

		if (GanysSurface.enableCushion)
			addShapedRecipe(new ItemStack(ModBlocks.cushion), "zxz", "xyx", "zxz", 'x', Blocks.wool, 'y', dyes[5], 'z', "nuggetGold");

		if (GanysSurface.enableChestPropellant)
			addShapedRecipe(new ItemStack(ModBlocks.chestPropellant), "ywy", "xzx", "xyx", 'x', "ingotIron", 'y', "nuggetGold", 'z', new ItemStack(Blocks.sandstone, 1, 2), 'w', Items.redstone);

		if (GanysSurface.enableFertilisedSoil)
			if (GanysSurface.enablePoop)
				addShapedRecipe(new ItemStack(ModBlocks.fertilizedSoil), "yyy", "xzx", "yyy", 'x', new ItemStack(ModItems.poop, 1, 1), 'y', new ItemStack(ModItems.rot, 1, 1), 'z', Blocks.dirt);
			else
				addShapedRecipe(new ItemStack(ModBlocks.fertilizedSoil), "yyy", "xzx", "yyy", 'x', new ItemStack(Items.fermented_spider_eye), 'y', new ItemStack(ModItems.rot), 'z', Blocks.dirt);

		if (GanysSurface.enableLantern)
			addShapelessRecipe(new ItemStack(ModBlocks.lantern), Blocks.glass, Blocks.torch);

		if (GanysSurface.enableInkHarvester)
			addShapedRecipe(new ItemStack(ModBlocks.inkHarvester), "xyx", "xzx", "xwx", 'x', new ItemStack(ModItems.pocketCritter, 1, 1), 'y', Items.redstone, 'z', Blocks.iron_block, 'w', Items.golden_sword);

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
			addShapedRecipe(new ItemStack(ModBlocks.slowRail, 6), "w w", "xyx", "wzw", 'x', "slimeball", 'y', "stickWood", 'z', "dustRedstone", 'w', "ingotIron");

		if (GanysSurface.enableBasalt) {
			if (GanysSurface.enable18Stones)
				addShapelessRecipe(new ItemStack(ModBlocks.basalt, 2, 0), new ItemStack(ModBlocks.newStones, 1, Stones18.DIORITE), new ItemStack(ModBlocks.newStones, 1, Stones18.ANDESITE));
			else
				addShapelessRecipe(new ItemStack(ModBlocks.basalt, 2, 0), new ItemStack(Blocks.stone), new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE));

			addShapedRecipe(new ItemStack(ModBlocks.basalt, 4, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.basalt, 1, 0));
		}

		if (GanysSurface.enableBlockOfFlint) {
			addShapedRecipe(new ItemStack(ModBlocks.flint), "xxx", "xxx", "xxx", 'x', new ItemStack(Items.flint));
			addShapelessRecipe(new ItemStack(Items.flint, 9), new ItemStack(ModBlocks.flint));
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

		// Vanilla
		if (GanysSurface.enableExtraVanillaRecipes)
			addShapedRecipe(new ItemStack(Blocks.web), "x x", " y ", "x x", 'y', Items.slime_ball, 'x', Items.string);

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
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.DIORITE), "xy", "yx", 'x', new ItemStack(Blocks.cobblestone), 'y', Items.quartz);
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_DIORITE), "xx", "xx", 'x', new ItemStack(ModBlocks.newStones, 1, Stones18.DIORITE));
			// Andesite
			addShapelessRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.ANDESITE), new ItemStack(Blocks.cobblestone), new ItemStack(ModBlocks.newStones, 1, Stones18.DIORITE));
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_ANDESITE), "xx", "xx", 'x', new ItemStack(ModBlocks.newStones, 1, Stones18.ANDESITE));
			// Granite
			addShapelessRecipe(new ItemStack(ModBlocks.newStones, 2, Stones18.GRANITE), Items.quartz, new ItemStack(ModBlocks.newStones, 1, Stones18.DIORITE));
			addShapedRecipe(new ItemStack(ModBlocks.newStones, 4, Stones18.POLISHED_GRANITE), "xx", "xx", 'x', new ItemStack(ModBlocks.newStones, 1, Stones18.GRANITE));
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

		if (GanysSurface.enableDoors)
			for (int i = 0; i < ModItems.doors.length; i++)
				addShapedRecipe(new ItemStack(ModItems.doors[i], 3), "xx", "xx", "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));

		if (GanysSurface.enableRedSandstone) {
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone), "xx", "xx", 'x', new ItemStack(Blocks.sand, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone, 1, 1), "x", "x", 'x', new ItemStack(ModBlocks.redSandstoneSlab));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstone, 1, 2), "xx", "xx", 'x', new ItemStack(ModBlocks.redSandstone));
			addShapedRecipe(new ItemStack(ModBlocks.redSandstoneSlab, 6), "xxx", 'x', ModBlocks.redSandstone);
			addShapedRecipe(new ItemStack(ModBlocks.redSandstoneStairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.redSandstone);
		}

		if (GanysSurface.enableFences) {
			for (int i = 0; i < ModBlocks.fences.length; i++) {
				Object wood = i == 0 ? "plankWood" : new ItemStack(Blocks.planks, 1, i);
				addShapedRecipe(new ItemStack(ModBlocks.fences[i], 3), "xyx", "xyx", 'x', wood, 'y', "stickWood");
			}
			addShapelessRecipe(new ItemStack(Blocks.fence), ModBlocks.fences[0]);
			addShapelessRecipe(new ItemStack(ModBlocks.fences[0]), Blocks.fence);

			for (int i = 0; i < ModBlocks.gates.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.gates[i]), "yxy", "yxy", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', "stickWood");
			addShapedRecipe(new ItemStack(Blocks.fence_gate), "yxy", "yxy", 'x', "plankWood", 'y', "stickWood");
		}
	}

	private static void addShapedRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, objects));
	}

	private static void addShapelessRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(output, objects));
	}
}