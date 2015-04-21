package ganymedes01.ganyssurface.lib;

import ganymedes01.ganyssurface.blocks.ColouredRedstone;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Strings {

	// Block names
	public static final String CAMELLIA_CROP_NAME = "camelliaCrop";
	public static final String RAIN_DETECTOR_NAME = "rainDetector";
	public static final String BLOCK_DETECTOR_NAME = "blockDetector";
	public static final String DISLOCATOR_NAME = "dislocator";
	public static final String SENSORING_DISLOCATOR_NAME = "sensoringDislocator";
	public static final String CUBIC_SENSORING_DISLOCATOR_NAME = "cubicSensoringDislocator";
	public static final String DISGUISED_TRAPDOOR_NAME = "disguisedTrapDoor";
	public static final String WORK_TABLE_NAME = "workTable";
	public static final String ORGANIC_MATTER_COMPRESSOR_NAME = "organicMatterCompressor";
	public static final String CUSHION_NAME = "cushion";
	public static final String CHOCOLATE_CAKE_NAME = "chocolateCake";
	public static final String ITEM_DISPLAY_NAME = "itemDisplay";
	public static final String CHEST_PROPELLANT_NAME = "chestPropellant";
	public static final String FERTILIZED_SOIL_NAME = "fertilizedSoil";
	public static final String PLANTER_NAME = "planter";
	public static final String LANTERN_NAME = "lantern";
	public static final String INK_HARVESTER_NAME = "inkHarvester";
	public static final String SLIME_BLOCK_NAME = "slimeBlock";
	public static final String[] COLOURED_REDSTONE_NAME = new String[16];
	static {
		for (int i = 0; i < 16; i++)
			if (i != 1) // Skip Red
				COLOURED_REDSTONE_NAME[i] = "colouredRedstone" + ColouredRedstone.COLOURS[i].toUpperCase();
	}
	public static final String COLOURED_REDSTONE_BLOCK_NAME = "colouredRedstoneBlock";
	public static final String DUAL_WORK_TABLE_NAME = "dualWorkTable";
	public static final String POOP_BLOCK_NAME = "poopBlock";
	public static final String FARM_MANAGER_NAME = "farmManager";
	public static final String ENCASING_BENCH_NAME = "encasingBench";
	public static final String AUTO_ENCASER_NAME = "autoEncaser";
	public static final String NEW_STONES_NAME = "18Stones";
	public static final String IRON_TRAPDOOR = "iron_trapdoor";
	public static final String PRISMARINE_BLOCKS = "prismarineBlocks";
	public static final String SEA_LANTERN = "sea_lantern";
	public static final String INVERTED_DAYLIGHT_SENSOR = "daylight_detector_inverted";
	public static final String CHARCOAL_BLOCK = "charcoalBlock";
	public static final String LEAF_WALL = "leafWall";
	public static final String SLOW_RAIL = "slowRail";
	public static final String BASALT = "basalt";
	public static final String BLOCK_BEETROOT_NAME = "beetrootBlock";
	public static final String BLOCK_OF_POOP_NAME = "blockOfPoop";
	public static final String COARSE_DIRT = "coarse_dirt";
	public static final String STORAGE = "storage";
	public static final String DYE = "dye";

	// Item names
	public static final String ROT_NAME = "rot";
	public static final String CAMELLIA_SEEDS_NAME = "camelliaSeeds";
	public static final String TEA_LEAVES_NAME = "teaLeaves";
	public static final String TEA_BAG_NAME = "teaBag";
	public static final String EMPTY_MUG_NAME = "emptyMug";
	public static final String CUP_OF_TEA_NAME = "cupOfTea";
	public static final String MANKY_CUP_OF_TEA_NAME = "mankyCupOfTea";
	public static final String POOP_NAME = "poop";
	public static final String FERTILIZER_NAME = "fertilizer";
	public static final String COOKED_EGG_NAME = "cookedEgg";
	public static final String OBSIDIAN_HEAD_NAME = "obsidianHead";
	public static final String WOODEN_WRENCH_NAME = "woodenWrench";
	public static final String BAT_NET_NAME = "batNet";
	public static final String POCKET_CRITTER_NAME = "pocketCritter";
	public static final String BAT_STEW_NAME = "batStew";
	public static final String CHOCOLATE_BAR_NAME = "chocolateBar";
	public static final String HORSALYSER_NAME = "horsalyser";
	public static final String HORSE_SPAWNER_NAME = "horseSpawner";
	public static final String CHARGED_CREEPER_SPAWNER_NAME = "chargedCreeperSpawner";
	public static final String COLOURED_REDSTONE_ITEM_NAME = "colouredRedstone";
	public static final String VILLAGE_FINDER = "villageFinder";
	public static final String PORTABLE_DUAL_WORK_TABLE_NAME = "portableDualWorkTable";
	public static final String ICY_PICKAXE_NAME = "icyPickaxe";
	public static final String ROASTED_SQUID_NAME = "roastedSquid";
	public static final String STORAGE_CASE_NAME = "storageCase";
	public static final String GEARALYSER_NAME = "gearalyser";
	public static final String RAW_MUTTON = "mutton_raw";
	public static final String COOKED_MUTTON = "mutton_cooked";
	public static final String PRISMARINE_ITEMS = "prismarineItems";
	public static final String PINE_CONE_NAME = "pineCone";
	public static final String PINE_NUTS_NAME = "pineNuts";
	public static final String QUIVER_NAME = "quiver";
	public static final String PAINTING_NAME = "painting";
	public static final String BEETROOT_NAME = "beetroot";
	public static final String BEETROOT_SOUP_NAME = "beetrootSoup";
	public static final String BEETROOT_SEEDS_NAME = "beetrootSeeds";
	public static final String STICK_NAME = "stick";

	// Armour names
	public static final String WOODEN_HELMET_NAME = "woodenHelmet";
	public static final String WOODEN_CHESTPLATE_NAME = "woodenChestplate";
	public static final String WOODEN_LEGGINGS_NAME = "woodenLeggings";
	public static final String WOODEN_BOOTS_NAME = "woodenBoots";

	public static final String DYED_IRON_HELMET_NAME = "dyedIronHelmet";
	public static final String DYED_IRON_CHESTPLATE_NAME = "dyedIronChestplate";
	public static final String DYED_IRON_LEGGINGS_NAME = "dyedIronLeggings";
	public static final String DYED_IRON_BOOTS_NAME = "dyedIronBoots";

	public static final String DYED_CHAIN_HELMET_NAME = "dyedChainHelmet";
	public static final String DYED_CHAIN_CHESTPLATE_NAME = "dyedChainChestplate";
	public static final String DYED_CHAIN_LEGGINGS_NAME = "dyedChainLeggings";
	public static final String DYED_CHAIN_BOOTS_NAME = "dyedChainBoots";

	// Entities
	public static final String ENTITY_POOP_NAME = "EntityPoop";
	public static final String ENTITY_BAT_POOP_NAME = "EntityBatPoop";

	// Version check
	public static final String VERSION_CHECK_INIT = "Starting version check.";
	public static final String VERSION_CHECK_FAIL = "Version check failed.";
	public static final String CURRENT_MESSAGE = "The version " + Reference.VERSION_NUMBER + " is the current version.";
	public static final String OUTDATED_MESSAGE = "The version " + Reference.VERSION_NUMBER + " is outdated. Current version: " + Reference.LATEST_VERSION;
	public static final String VERSION_CHECK_FAIL_CONNECT = "Failed to connect to version check URL. Trying again...";
	public static final String VERSION_CHECK_FAIL_CONNECT_FINAL = "Version check stopped after too many unsuccessful attempts.";
}