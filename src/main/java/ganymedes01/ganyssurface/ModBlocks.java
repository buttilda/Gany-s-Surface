package ganymedes01.ganyssurface;

import ganymedes01.ganyssurface.blocks.AutoEncaser;
import ganymedes01.ganyssurface.blocks.Basalt;
import ganymedes01.ganyssurface.blocks.BlockDetector;
import ganymedes01.ganyssurface.blocks.BlockNewDoor;
import ganymedes01.ganyssurface.blocks.BlockSilkedMushroom;
import ganymedes01.ganyssurface.blocks.BlockSlowRail;
import ganymedes01.ganyssurface.blocks.BlockWoodChest;
import ganymedes01.ganyssurface.blocks.BlockWoodFence;
import ganymedes01.ganyssurface.blocks.BlockWoodFenceGate;
import ganymedes01.ganyssurface.blocks.CamelliaCrop;
import ganymedes01.ganyssurface.blocks.CharcoalBlock;
import ganymedes01.ganyssurface.blocks.ChestPropellant;
import ganymedes01.ganyssurface.blocks.ChocolateCakeBlock;
import ganymedes01.ganyssurface.blocks.ColouredRedstone;
import ganymedes01.ganyssurface.blocks.ColouredRedstoneBlock;
import ganymedes01.ganyssurface.blocks.CubicSensoringDislocator;
import ganymedes01.ganyssurface.blocks.Cushion;
import ganymedes01.ganyssurface.blocks.DisguisedTrapDoor;
import ganymedes01.ganyssurface.blocks.Dislocator;
import ganymedes01.ganyssurface.blocks.DualWorkTable;
import ganymedes01.ganyssurface.blocks.EncasingBench;
import ganymedes01.ganyssurface.blocks.FarmManager;
import ganymedes01.ganyssurface.blocks.FertilizedSoil;
import ganymedes01.ganyssurface.blocks.FlintBlock;
import ganymedes01.ganyssurface.blocks.InkHarvester;
import ganymedes01.ganyssurface.blocks.InvertedDaylightDetector;
import ganymedes01.ganyssurface.blocks.IronTrapdoor;
import ganymedes01.ganyssurface.blocks.ItemDisplay;
import ganymedes01.ganyssurface.blocks.Lantern;
import ganymedes01.ganyssurface.blocks.LeafWall;
import ganymedes01.ganyssurface.blocks.OrganicMatterCompressor;
import ganymedes01.ganyssurface.blocks.Planter;
import ganymedes01.ganyssurface.blocks.PoopBlock;
import ganymedes01.ganyssurface.blocks.PrismarineBlocks;
import ganymedes01.ganyssurface.blocks.RainDetector;
import ganymedes01.ganyssurface.blocks.RedSandstone;
import ganymedes01.ganyssurface.blocks.RedSandstoneSlab;
import ganymedes01.ganyssurface.blocks.RedSandstoneStairs;
import ganymedes01.ganyssurface.blocks.SeaLantern;
import ganymedes01.ganyssurface.blocks.SensoringDislocator;
import ganymedes01.ganyssurface.blocks.SlimeBlock;
import ganymedes01.ganyssurface.blocks.Stones18;
import ganymedes01.ganyssurface.blocks.WorkTable;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.init.Blocks;
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
	public static final Block sensitiveDislocator = new SensoringDislocator();
	public static final Block cubicSensitiveDislocator = new CubicSensoringDislocator();
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
	public static final Block leafWall = new LeafWall();
	public static final Block charcoalBlock = new CharcoalBlock();
	public static final Block slowRail = new BlockSlowRail();
	public static final Block basalt = new Basalt();
	public static final Block flint = new FlintBlock();

	public static final Block chestOak = new BlockWoodChest(Blocks.planks, 0);
	public static final Block chestSpruce = new BlockWoodChest(Blocks.planks, 1);
	public static final Block chestBirch = new BlockWoodChest(Blocks.planks, 2);
	public static final Block chestJungle = new BlockWoodChest(Blocks.planks, 3);
	public static final Block chestAcacia = new BlockWoodChest(Blocks.planks, 4);
	public static final Block chestDarkOak = new BlockWoodChest(Blocks.planks, 5);

	// 1.8 stuff
	public static final Block newStones = new Stones18();
	public static final Block ironTrapdoor = new IronTrapdoor();
	public static final Block prismarineBlocks = new PrismarineBlocks();
	public static final Block seaLantern = new SeaLantern();
	public static final Block doorAcacia = new BlockNewDoor("acacia");
	public static final Block doorBirch = new BlockNewDoor("birch");
	public static final Block doorDarkOak = new BlockNewDoor("dark_oak");
	public static final Block doorJungle = new BlockNewDoor("jungle");
	public static final Block doorSpruce = new BlockNewDoor("spruce");
	public static final Block invertedDaylightDetector = new InvertedDaylightDetector();
	public static final Block redSandstone = new RedSandstone();
	public static final Block redSandstoneSlab = new RedSandstoneSlab();
	public static final Block redSandstoneStairs = new RedSandstoneStairs();
	public static final Block fenceAcacia = new BlockWoodFence(4);
	public static final Block fenceBirch = new BlockWoodFence(2);
	public static final Block fenceDarkOak = new BlockWoodFence(5);
	public static final Block fenceJungle = new BlockWoodFence(3);
	public static final Block fenceSpruce = new BlockWoodFence(1);
	public static final Block fenceOak = new BlockWoodFence(0);
	public static final Block gateAcacia = new BlockWoodFenceGate(4);
	public static final Block gateBirch = new BlockWoodFenceGate(2);
	public static final Block gateDarkOak = new BlockWoodFenceGate(5);
	public static final Block gateJungle = new BlockWoodFenceGate(3);
	public static final Block gateSpruce = new BlockWoodFenceGate(1);
	public static final Block brown_mushroom_block = new BlockSilkedMushroom(Blocks.brown_mushroom_block, "brown");
	public static final Block red_mushroom_block = new BlockSilkedMushroom(Blocks.red_mushroom_block, "red");

	static {
		for (int i = 0; i < disguisedTrapDoor.length; i++)
			disguisedTrapDoor[i] = new DisguisedTrapDoor(i);
		for (int i = 0; i < 16; i++)
			if (i != 1) // Skip Red
				colouredRedstone[i] = new ColouredRedstone(i);
	}

	public static void init() {
		try {
			for (Field f : ModBlocks.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Block)
					registerBlock((Block) obj);
				else if (obj instanceof Block[])
					for (Block block : (Block[]) obj)
						if (block != null)
							registerBlock(block);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static void registerBlock(Block block) {
		String name = block.getUnlocalizedName();
		String[] strings = name.split("\\.");

		if (block instanceof ISubBlocksBlock)
			GameRegistry.registerBlock(block, ((ISubBlocksBlock) block).getItemBlockClass(), strings[strings.length - 1]);
		else
			GameRegistry.registerBlock(block, strings[strings.length - 1]);

		if (block instanceof IBurnableBlock)
			Blocks.fire.setFireInfo(block, 5, 20);
	}

	public static interface ISubBlocksBlock {

		Class<? extends ItemBlock> getItemBlockClass();
	}

	public static interface IBurnableBlock {
	}
}