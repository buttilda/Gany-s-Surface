package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorChargedCreeperSpawner;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorHorseSpawner;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorPocketBat;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorPoop;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorRot;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ModItems {

	// Items
	public static Item rot;
	public static Item camelliaSeeds;
	public static Item teaLeaves;
	public static Item teaBag;
	public static Item emptyMug;
	public static Item cupOfTea;
	public static Item mankyCupOfTea;
	public static Item poop;
	public static Item cookedEgg;
	public static Item obsidianHead;
	public static Item woodenWrench;
	public static Item batNet;
	public static Item pocketCritter;
	public static Item batStew;
	public static Item chocolateBar;
	public static Item horsalyser;
	public static Item horseSpawner;
	public static Item chargedCreeperSpawner;
	public static Item colouredRedstone;
	public static Item villageFinder;
	public static Item portalDualWorkTable;
	public static Item icyPickaxe;
	public static Item roastedSquid;
	public static Item storageCase;

	// Armour
	public static Item woodenHelmet;
	public static Item woodenChestplate;
	public static Item woodenLeggings;
	public static Item woodenBoots;

	public static Item dyedIronHelmet;
	public static Item dyedIronChestplate;
	public static Item dyedIronLeggings;
	public static Item dyedIronBoots;

	public static void init() {
		// Armour
		if (GanysSurface.enableWoodenArmour) {
			woodenHelmet = new WoodenArmour(ModIDs.WOODEN_HELMET_ID, 0);
			woodenChestplate = new WoodenArmour(ModIDs.WOODEN_CHESTPLATE_ID, 1);
			woodenLeggings = new WoodenArmour(ModIDs.WOODEN_LEGGINGS_ID, 2);
			woodenBoots = new WoodenArmour(ModIDs.WOODEN_BOOTS_ID, 3);
		}
		dyedIronHelmet = new DyedIronArmour(ModIDs.DYED_IRON_HELMET_ID, 0);
		dyedIronChestplate = new DyedIronArmour(ModIDs.DYED_IRON_CHESTPLATE_ID, 1);
		dyedIronLeggings = new DyedIronArmour(ModIDs.DYED_IRON_LEGGINGS_ID, 2);
		dyedIronBoots = new DyedIronArmour(ModIDs.DYED_IRON_BOOTS_ID, 3);

		// Items
		rot = new Rot();
		camelliaSeeds = new CamelliaSeeds();
		teaLeaves = new TeaLeaves();
		teaBag = new TeaBag();
		emptyMug = new EmptyMug();
		cupOfTea = new CupOfTea();
		mankyCupOfTea = new MankyCupOfTea();
		poop = new Poop();
		cookedEgg = new CookedEgg();
		obsidianHead = new ObsidianHead();
		woodenWrench = new WoodenWrench();
		batNet = new BatNet();
		pocketCritter = new PocketCritter();
		batStew = new BatStew();
		if (GanysSurface.activateChocolate)
			chocolateBar = new ChocolateBar();
		horsalyser = new Horsalyser();
		horseSpawner = new HorseSpawner();
		chargedCreeperSpawner = new ChargedCreeperSpawner();
		colouredRedstone = new ColouredRedstoneItem();
		villageFinder = new VillageFinder();
		portalDualWorkTable = new PortableDualWorkTable();
		icyPickaxe = new IcyPickaxe();
		roastedSquid = new RoastedSquid();
		storageCase = new StorageCase();

		registerNames();
		registerForge();
		registerDispenserActions();
	}

	private static void registerNames() {
		// Armour
		if (GanysSurface.enableWoodenArmour) {
			GameRegistry.registerItem(woodenHelmet, Strings.WOODEN_HELMET_NAME);
			GameRegistry.registerItem(woodenChestplate, Strings.WOODEN_CHESTPLATE_NAME);
			GameRegistry.registerItem(woodenLeggings, Strings.WOODEN_LEGGINGS_NAME);
			GameRegistry.registerItem(woodenBoots, Strings.WOODEN_BOOTS_NAME);
		}

		GameRegistry.registerItem(dyedIronHelmet, Strings.DYED_IRON_HELMET_NAME);
		GameRegistry.registerItem(dyedIronChestplate, Strings.DYED_IRON_CHESTPLATE_NAME);
		GameRegistry.registerItem(dyedIronLeggings, Strings.DYED_IRON_LEGGINGS_NAME);
		GameRegistry.registerItem(dyedIronBoots, Strings.DYED_IRON_BOOTS_NAME);

		// Items
		GameRegistry.registerItem(rot, Strings.ROT_NAME);
		GameRegistry.registerItem(camelliaSeeds, Strings.CAMELLIA_SEEDS_NAME);
		GameRegistry.registerItem(teaLeaves, Strings.TEA_LEAVES_NAME);
		GameRegistry.registerItem(teaBag, Strings.TEA_BAG_NAME);
		GameRegistry.registerItem(emptyMug, Strings.EMPTY_MUG_NAME);
		GameRegistry.registerItem(cupOfTea, Strings.CUP_OF_TEA_NAME);
		GameRegistry.registerItem(mankyCupOfTea, Strings.MANKY_CUP_OF_TEA_NAME);
		GameRegistry.registerItem(poop, Strings.POOP_NAME);
		GameRegistry.registerItem(cookedEgg, Strings.COOKED_EGG_NAME);
		GameRegistry.registerItem(obsidianHead, Strings.OBSIDIAN_HEAD_NAME);
		GameRegistry.registerItem(woodenWrench, Strings.WOODEN_WRENCH_NAME);
		GameRegistry.registerItem(batNet, Strings.BAT_NET_NAME);
		GameRegistry.registerItem(pocketCritter, Strings.POCKET_CRITTER_NAME);
		GameRegistry.registerItem(batStew, Strings.BAT_STEW_NAME);
		if (GanysSurface.activateChocolate)
			GameRegistry.registerItem(chocolateBar, Strings.CHOCOLATE_BAR_NAME);
		GameRegistry.registerItem(horsalyser, Strings.HORSALYSER_NAME);
		GameRegistry.registerItem(horseSpawner, Strings.HORSE_SPAWNER_NAME);
		GameRegistry.registerItem(chargedCreeperSpawner, Strings.CHARGED_CREEPER_SPAWNER_NAME);
		GameRegistry.registerItem(colouredRedstone, Strings.COLOURED_REDSTONE_ITEM_NAME);
		GameRegistry.registerItem(villageFinder, Strings.VILLAGE_FINDER);
		GameRegistry.registerItem(portalDualWorkTable, Strings.PORTABLE_DUAL_WORK_TABLE_NAME);
		GameRegistry.registerItem(icyPickaxe, Strings.ICY_PICKAXE_NAME);
		MinecraftForge.setToolClass(icyPickaxe, "pickaxe", 2);
		GameRegistry.registerItem(roastedSquid, Strings.ROASTED_SQUID_NAME);
	}

	private static void registerForge() {
		if (GanysSurface.enableCamilaSeedsToDropFromGrass)
			MinecraftForge.addGrassSeed(new ItemStack(camelliaSeeds), 5);
	}

	private static void registerDispenserActions() {
		BlockDispenser.dispenseBehaviorRegistry.putObject(pocketCritter, new DispenserBehaviorPocketBat());
		BlockDispenser.dispenseBehaviorRegistry.putObject(poop, new DispenserBehaviorPoop());
		BlockDispenser.dispenseBehaviorRegistry.putObject(rot, new DispenserBehaviorRot());
		BlockDispenser.dispenseBehaviorRegistry.putObject(horseSpawner, new DispenserBehaviorHorseSpawner());
		BlockDispenser.dispenseBehaviorRegistry.putObject(chargedCreeperSpawner, new DispenserBehaviorChargedCreeperSpawner());
	}
}