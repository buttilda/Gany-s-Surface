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
	public static final String DISGUISED_TRAP_DOOR_OAK_NAME = "disguisedTrapDoorOak";
	public static final String DISGUISED_TRAP_DOOR_SPRUCE_NAME = "disguisedTrapDoorSpruce";
	public static final String DISGUISED_TRAP_DOOR_BIRCH_NAME = "disguisedTrapDoorBirch";
	public static final String DISGUISED_TRAP_DOOR_JUNGLE_NAME = "disguisedTrapDoorJungle";
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
	public static final String MARKET_NAME = "market";
	public static final String[] COLOURED_REDSTONE_NAME = new String[16];
	static {
		for (int i = 0; i < 16; i++)
			if (i != 1) // Skip Red
				COLOURED_REDSTONE_NAME[i] = "colouredRedstone" + ColouredRedstone.COLOURS[i].toUpperCase();
	}
	public static final String COLOURED_REDSTONE_BLOCK_NAME = "colouredRedstoneBlock";
	public static final String DUAL_WORK_TABLE_NAME = "dualWorkTable";

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
	public static final String POCKET_BAT_NAME = "pocketBat";
	public static final String BAT_STEW_NAME = "batStew";
	public static final String CHOCOLATE_BAR_NAME = "chocolateBar";
	public static final String HORSALYSER_NAME = "horsalyser";
	public static final String HORSE_SPAWNER_NAME = "horseSpawner";
	public static final String CHARGED_CREEPER_SPAWNER_NAME = "chargedCreeperSpawner";
	public static final String COLOURED_REDSTONE_ITEM_NAME = "colouredRedstone";

	// Armour names
	public static final String WOODEN_HELMET_NAME = "woodenHelmet";
	public static final String WOODEN_CHESTPLATE_NAME = "woodenChestplate";
	public static final String WOODEN_LEGGINGS_NAME = "woodenLeggings";
	public static final String WOODEN_BOOTS_NAME = "woodenBoots";

	public static final String DYED_IRON_HELMET_NAME = "dyedIronHelmet";
	public static final String DYED_IRON_CHESTPLATE_NAME = "dyedIronChestplate";
	public static final String DYED_IRON_LEGGINGS_NAME = "dyedIronLeggings";
	public static final String DYED_IRON_BOOTS_NAME = "dyedIronBoots";

	// Entities
	public static final String ENTITY_POOP_NAME = "EntityPoop";
	public static final String ENTITY_BAT_POOP_NAME = "EntityBatPoop";

	// Others
	public static final String MOBS_SHOULD_POOP = "mobsShouldPoop";
	public static final String ACTIVATE_CHOCOLATE = "activateChocolate";
	public static final String SHOULD_DO_VERSION_CHECK = "shouldDoVersionCheck";
	public static final String FORCE_ALL_CONTAINERS_OPEN = "forceAllContainersOpen";
	public static final String ENABLE_MARKET = "enableMarket";
	public static final String ENABLE_WOODEN_ARMOUR = "enableWoodenArmour";
	public static final String MAX_LEVEL_OMC_WORKS = "maxLevelOMCWorks";
	public static final String INK_HARVESTER_MAX_STRIKE = "inkHarvesterMaxStrike";

	// Version check
	public static final String VERSION_CHECK_INIT = "Starting version check.";
	public static final String VERSION_CHECK_FAIL = "Version check failed.";
	public static final String CURRENT_MESSAGE = "The version " + Reference.VERSION_NUMBER + " is the current version.";
	public static final String OUTDATED_MESSAGE = "The version " + Reference.VERSION_NUMBER + " is outdated. Current version: " + Reference.LATEST_VERSION;
	public static final String VERSION_CHECK_FAIL_CONNECT = "Failed to connect to version check URL. Trying again...";
	public static final String VERSION_CHECK_FAIL_CONNECT_FINAL = "Version check stopped after too many unsuccessful attempts.";
}