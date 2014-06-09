package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.items.block.ItemChocolateCake;
import ganymedes01.ganyssurface.items.block.ItemColouredRedstoneBlock;
import ganymedes01.ganyssurface.items.block.ItemItemDisplay;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ModBlocks {

	public static final Block camelliaCrop = new CamelliaCrop();
	public static final Block rainDetector = new RainDetector();
	public static final Block blockDetector = new BlockDetector();
	public static final Block dislocator = new Dislocator();
	public static final Block sensoringDislocator = new SensoringDislocator();
	public static final Block cubicSensoringDislocator = new CubicSensoringDislocator();
	public static final Block[] disguisedTrapDoor = new Block[BlockWood.field_150096_a.length];
	public static final Block workTable = new WorkTable();
	public static final Block organicMatterCompressor = new OrganicMatterCompressor();
	public static final Block cushion = new Cushion();
	public static final Block chocolateCake = new ChocolateCakeBlock();
	public static final Block itemDisplay = new ItemDisplay();
	public static final Block chestPropellant = new ChestPropellant();
	public static final Block fertilizedSoil = new FertilizedSoil();
	public static final Block planter = new Planter();
	public static final Block lantern = new Lantern();
	public static final Block inkHarvester = new InkHarvester();
	public static final Block slimeBlock = new SlimeBlock();
	public static final Block[] colouredRedstone = new Block[16];
	public static final Block colouredRedstoneBlock = new ColouredRedstoneBlock();
	public static final Block dualWorkTable = new DualWorkTable();
	public static final Block poop = new PoopBlock();
	public static final Block farmManager = new FarmManager();
	public static final Block encasingBench = new EncasingBench();
	public static final Block autoEncaser = new AutoEncaser();

	static {
		for (int i = 0; i < disguisedTrapDoor.length; i++)
			disguisedTrapDoor[i] = new DisguisedTrapDoor(i);
		for (int i = 0; i < 16; i++)
			if (i != 1) // Skip Red
				colouredRedstone[i] = new ColouredRedstone(i);
	}

	public static void init() {
		registerBlock(camelliaCrop);
		registerBlock(rainDetector);
		registerBlock(blockDetector);
		registerBlock(dislocator);
		registerBlock(sensoringDislocator);
		registerBlock(cubicSensoringDislocator);
		for (int i = 0; i < disguisedTrapDoor.length; i++)
			registerBlock(disguisedTrapDoor[i]);
		registerBlock(workTable);
		registerBlock(organicMatterCompressor);
		registerBlock(cushion);
		registerBlock(chocolateCake, ItemChocolateCake.class);
		registerBlock(itemDisplay, ItemItemDisplay.class);
		registerBlock(chestPropellant);
		registerBlock(fertilizedSoil);
		registerBlock(planter);
		registerBlock(lantern);
		registerBlock(inkHarvester);
		registerBlock(slimeBlock);
		for (int i = 0; i < 16; i++)
			if (i != 1) // Skip Red
				registerBlock(colouredRedstone[i]);
		registerBlock(colouredRedstoneBlock, ItemColouredRedstoneBlock.class);
		registerBlock(dualWorkTable);
		registerBlock(poop);
		registerBlock(farmManager);
		registerBlock(encasingBench);
		registerBlock(autoEncaser);
	}

	private static void registerBlock(Block block) {
		String name = block.getUnlocalizedName();
		String[] strings = name.split("\\.");
		GameRegistry.registerBlock(block, strings[strings.length - 1]);
	}

	private static void registerBlock(Block block, Class<? extends ItemBlock> item) {
		String name = block.getUnlocalizedName();
		String[] strings = name.split("\\.");
		GameRegistry.registerBlock(block, item, strings[strings.length - 1]);
	}
}