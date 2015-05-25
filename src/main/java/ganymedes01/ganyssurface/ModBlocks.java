package ganymedes01.ganyssurface;

import ganymedes01.ganyssurface.blocks.AutoEncaser;
import ganymedes01.ganyssurface.blocks.Basalt;
import ganymedes01.ganyssurface.blocks.BlockBeetroot;
import ganymedes01.ganyssurface.blocks.BlockDetector;
import ganymedes01.ganyssurface.blocks.BlockDye;
import ganymedes01.ganyssurface.blocks.BlockOfPoop;
import ganymedes01.ganyssurface.blocks.BlockSlowRail;
import ganymedes01.ganyssurface.blocks.BlockStorage;
import ganymedes01.ganyssurface.blocks.BlockWoodBookshelf;
import ganymedes01.ganyssurface.blocks.BlockWoodButton;
import ganymedes01.ganyssurface.blocks.BlockWoodChest;
import ganymedes01.ganyssurface.blocks.BlockWoodDoor;
import ganymedes01.ganyssurface.blocks.BlockWoodFence;
import ganymedes01.ganyssurface.blocks.BlockWoodFenceGate;
import ganymedes01.ganyssurface.blocks.BlockWoodLadder;
import ganymedes01.ganyssurface.blocks.BlockWoodPressurePlate;
import ganymedes01.ganyssurface.blocks.BlockWoodSign;
import ganymedes01.ganyssurface.blocks.BlockWoodTrapdoor;
import ganymedes01.ganyssurface.blocks.CamelliaCrop;
import ganymedes01.ganyssurface.blocks.CharcoalBlock;
import ganymedes01.ganyssurface.blocks.ChestPropellant;
import ganymedes01.ganyssurface.blocks.ChocolateCakeBlock;
import ganymedes01.ganyssurface.blocks.ColouredRedstone;
import ganymedes01.ganyssurface.blocks.ColouredRedstoneBlock;
import ganymedes01.ganyssurface.blocks.CubicSensoringDislocator;
import ganymedes01.ganyssurface.blocks.Cushion;
import ganymedes01.ganyssurface.blocks.DisguisedTrapdoor;
import ganymedes01.ganyssurface.blocks.Dislocator;
import ganymedes01.ganyssurface.blocks.DualWorkTable;
import ganymedes01.ganyssurface.blocks.EncasingBench;
import ganymedes01.ganyssurface.blocks.FarmManager;
import ganymedes01.ganyssurface.blocks.FertilizedSoil;
import ganymedes01.ganyssurface.blocks.InkHarvester;
import ganymedes01.ganyssurface.blocks.InvertedDaylightDetector;
import ganymedes01.ganyssurface.blocks.ItemDisplay;
import ganymedes01.ganyssurface.blocks.Lantern;
import ganymedes01.ganyssurface.blocks.LeafWall;
import ganymedes01.ganyssurface.blocks.OrganicMatterCompressor;
import ganymedes01.ganyssurface.blocks.Planter;
import ganymedes01.ganyssurface.blocks.PoopBlock;
import ganymedes01.ganyssurface.blocks.RainDetector;
import ganymedes01.ganyssurface.blocks.SensoringDislocator;
import ganymedes01.ganyssurface.blocks.SlimeBlock;
import ganymedes01.ganyssurface.blocks.WorkTable;
import ganymedes01.ganyssurface.blocks.OnePointEight.BlockBanner;
import ganymedes01.ganyssurface.blocks.OnePointEight.BlockSilkedMushroom;
import ganymedes01.ganyssurface.blocks.OnePointEight.CoarseDirt;
import ganymedes01.ganyssurface.blocks.OnePointEight.IronTrapdoor;
import ganymedes01.ganyssurface.blocks.OnePointEight.PrismarineBlocks;
import ganymedes01.ganyssurface.blocks.OnePointEight.RedSandstone;
import ganymedes01.ganyssurface.blocks.OnePointEight.RedSandstoneSlab;
import ganymedes01.ganyssurface.blocks.OnePointEight.RedSandstoneStairs;
import ganymedes01.ganyssurface.blocks.OnePointEight.SeaLantern;
import ganymedes01.ganyssurface.blocks.OnePointEight.Sponge;
import ganymedes01.ganyssurface.blocks.OnePointEight.Stones18;

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
	public static final Block beetroot = new BlockBeetroot();
	public static final Block blockOfPoop = new BlockOfPoop();
	public static final Block bookshelf = new BlockWoodBookshelf();
	public static final BlockStorage storage = new BlockStorage();
	public static final BlockStorage dye = new BlockDye();

	public static final Block[] disguisedTrapdoors = new Block[BlockWood.field_150096_a.length];
	public static final Block[] chests = new Block[BlockWood.field_150096_a.length];
	public static final Block[] buttons = new Block[BlockWood.field_150096_a.length - 1];
	public static final Block[] pressurePlates = new Block[BlockWood.field_150096_a.length - 1];
	public static final Block[] trapdoors = new Block[BlockWood.field_150096_a.length - 1];
	public static final Block[] ladders = new Block[BlockWood.field_150096_a.length - 1];
	public static final Block[] signs = new Block[BlockWood.field_150096_a.length - 1];

	// 1.8 stuff
	public static final Block newStones = new Stones18();
	public static final Block ironTrapdoor = new IronTrapdoor();
	public static final Block prismarineBlocks = new PrismarineBlocks();
	public static final Block seaLantern = new SeaLantern();
	public static final Block[] doors = new Block[BlockWood.field_150096_a.length - 1];
	public static final Block invertedDaylightDetector = new InvertedDaylightDetector();
	public static final Block redSandstone = new RedSandstone();
	public static final Block redSandstoneSlab = new RedSandstoneSlab();
	public static final Block redSandstoneStairs = new RedSandstoneStairs();
	public static final Block[] fences = new Block[BlockWood.field_150096_a.length];
	public static final Block[] gates = new Block[BlockWood.field_150096_a.length - 1];
	public static final Block brown_mushroom_block = new BlockSilkedMushroom(Blocks.brown_mushroom_block, "brown");
	public static final Block red_mushroom_block = new BlockSilkedMushroom(Blocks.red_mushroom_block, "red");
	public static final Block coarseDirt = new CoarseDirt();
	public static final Block banner = new BlockBanner();
	public static final Block sponge = new Sponge();

	static {
		for (int i = 0; i < disguisedTrapdoors.length; i++)
			disguisedTrapdoors[i] = new DisguisedTrapdoor(i);

		for (int i = 0; i < 16; i++)
			if (i != 1) // Skip Red
				colouredRedstone[i] = new ColouredRedstone(i);

		for (int i = 0; i < doors.length; i++)
			doors[i] = new BlockWoodDoor(i + 1);

		for (int i = 0; i < fences.length; i++)
			fences[i] = new BlockWoodFence(i);

		for (int i = 0; i < gates.length; i++)
			gates[i] = new BlockWoodFenceGate(i + 1);

		for (int i = 0; i < chests.length; i++)
			chests[i] = new BlockWoodChest(Blocks.planks, i);

		for (int i = 0; i < buttons.length; i++)
			buttons[i] = new BlockWoodButton(i + 1);

		for (int i = 0; i < pressurePlates.length; i++)
			pressurePlates[i] = new BlockWoodPressurePlate(i + 1);

		for (int i = 0; i < trapdoors.length; i++)
			trapdoors[i] = new BlockWoodTrapdoor(i + 1);

		for (int i = 0; i < ladders.length; i++)
			ladders[i] = new BlockWoodLadder(i + 1);

		for (int i = 0; i < signs.length; i++)
			signs[i] = new BlockWoodSign(i + 1);
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
		if (!(block instanceof IConfigurable) || ((IConfigurable) block).isEnabled()) {
			String name = block.getUnlocalizedName();
			String[] strings = name.split("\\.");

			if (block instanceof ISubBlocksBlock)
				GameRegistry.registerBlock(block, ((ISubBlocksBlock) block).getItemBlockClass(), strings[strings.length - 1]);
			else
				GameRegistry.registerBlock(block, strings[strings.length - 1]);

			if (block instanceof IBurnableBlock)
				Blocks.fire.setFireInfo(block, 5, 20);
		}
	}

	public static interface ISubBlocksBlock {

		Class<? extends ItemBlock> getItemBlockClass();
	}

	public static interface IBurnableBlock {
	}
}