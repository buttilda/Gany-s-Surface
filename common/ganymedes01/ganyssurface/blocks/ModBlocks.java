package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.items.ItemChocolateCake;
import ganymedes01.ganyssurface.lib.BlocksID;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block camelliaCrop;
	public static Block rainDetector;
	public static Block blockDetector;
	public static Block dislocator;
	public static Block sensoringDislocator;
	public static Block cubicSensoringDislocator;
	public static Block disguisedTrapDoorOak;
	public static Block disguisedTrapDoorSpruce;
	public static Block disguisedTrapDoorBirch;
	public static Block disguisedTrapDoorJungle;
	public static Block workTable;
	public static Block organicMatterCompressor;
	public static Block cushion;
	public static Block chocolateCake;
	public static Block itemDisplay;
	public static Block chestPropellant;

	public static void init() {
		camelliaCrop = new CamelliaCrop(BlocksID.CAMELLIA_CROP_ID);
		rainDetector = new RainDetector(BlocksID.RAIN_DETECTOR_ID);
		blockDetector = new BlockDetector(BlocksID.BLOCK_DETECTOR_ID);
		dislocator = new Dislocator(BlocksID.DISLOCATOR_ID);
		sensoringDislocator = new SensoringDislocator(BlocksID.SENSORING_DISLOCATOR_ID);
		cubicSensoringDislocator = new CubicSensoringDislocator(BlocksID.CUBIC_SENSORING_DISLOCATOR_ID);
		disguisedTrapDoorOak = new DisguisedTrapDoor(BlocksID.DISGUISED_TRAP_DOOR_OAK_ID, 0);
		disguisedTrapDoorSpruce = new DisguisedTrapDoor(BlocksID.DISGUISED_TRAP_DOOR_SPRUCE_ID, 1);
		disguisedTrapDoorBirch = new DisguisedTrapDoor(BlocksID.DISGUISED_TRAP_DOOR_BIRCH_ID, 2);
		disguisedTrapDoorJungle = new DisguisedTrapDoor(BlocksID.DISGUISED_TRAP_DOOR_JUNGLE_ID, 3);
		workTable = new WorkTable(BlocksID.WORK_TABLE_ID);
		organicMatterCompressor = new OrganicMatterCompressor(BlocksID.ORGANIC_MATTER_COMPRESSOR_ID);
		cushion = new Cushion(BlocksID.CUSHION_ID);
		chocolateCake = new ChocolateCakeBlock(BlocksID.CHOCOLATE_CAKE_ID);
		itemDisplay = new ItemDisplay(BlocksID.ITEM_DISPLAY_ID);
		chestPropellant = new ChestPropellant(BlocksID.CHEST_PROPELLANT_ID);

		registerNames();
	}

	private static void registerNames() {
		GameRegistry.registerBlock(camelliaCrop, Strings.CAMELLIA_CROP_NAME);
		GameRegistry.registerBlock(rainDetector, Strings.RAIN_DETECTOR_NAME);
		GameRegistry.registerBlock(blockDetector, Strings.BLOCK_DETECTOR_NAME);
		GameRegistry.registerBlock(dislocator, Strings.DISLOCATOR_NAME);
		GameRegistry.registerBlock(sensoringDislocator, Strings.SENSORING_DISLOCATOR_NAME);
		GameRegistry.registerBlock(cubicSensoringDislocator, Strings.CUBIC_SENSORING_DISLOCATOR_NAME);
		GameRegistry.registerBlock(disguisedTrapDoorOak, Strings.DISGUISED_TRAP_DOOR_OAK_NAME);
		GameRegistry.registerBlock(disguisedTrapDoorSpruce, Strings.DISGUISED_TRAP_DOOR_SPRUCE_NAME);
		GameRegistry.registerBlock(disguisedTrapDoorBirch, Strings.DISGUISED_TRAP_DOOR_BIRCH_NAME);
		GameRegistry.registerBlock(disguisedTrapDoorJungle, Strings.DISGUISED_TRAP_DOOR_JUNGLE_NAME);
		GameRegistry.registerBlock(workTable, Strings.WORK_TABLE_NAME);
		GameRegistry.registerBlock(organicMatterCompressor, Strings.ORGANIC_MATTER_COMPRESSOR_NAME);
		GameRegistry.registerBlock(cushion, Strings.CUSHION_NAME);
		if (GanysSurface.activateChocolate)
			GameRegistry.registerBlock(chocolateCake, ItemChocolateCake.class, Strings.CHOCOLATE_CAKE_NAME);
		GameRegistry.registerBlock(itemDisplay, Strings.ITEM_DISPLAY_NAME);
		GameRegistry.registerBlock(chestPropellant, Strings.CHEST_PROPELLANT_NAME);
	}
}
