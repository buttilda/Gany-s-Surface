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
	public static Item pocketBat;
	public static Item batStew;
	public static Item chocolateBar;
	public static Item horsalyser;
	public static Item horseSpawner;
	public static Item chargedCreeperSpawner;

	// Armour
	public static Item woodenHelmet;
	public static Item woodenChestplate;
	public static Item woodenLeggings;
	public static Item woodenBoots;

	public static void init() {
		// Armour
		woodenHelmet = new WoodenArmour(ModIDs.WOODEN_HELMET_ID, 0);
		woodenChestplate = new WoodenArmour(ModIDs.WOODEN_CHESTPLATE_ID, 1);
		woodenLeggings = new WoodenArmour(ModIDs.WOODEN_LEGGINGS_ID, 2);
		woodenBoots = new WoodenArmour(ModIDs.WOODEN_BOOTS_ID, 3);

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
		pocketBat = new PocketBat();
		batStew = new BatStew();
		if (GanysSurface.activateChocolate)
			chocolateBar = new ChocolateBar();
		horsalyser = new Horsalyser();
		horseSpawner = new HorseSpawner();
		chargedCreeperSpawner = new ChargedCreeperSpawner();

		registerNames();
		registerForge();
		registerDispenserActions();
	}

	private static void registerNames() {
		// Armour
		GameRegistry.registerItem(woodenHelmet, Strings.WOODEN_HELMET_NAME);
		GameRegistry.registerItem(woodenChestplate, Strings.WOODEN_CHESTPLATE_NAME);
		GameRegistry.registerItem(woodenLeggings, Strings.WOODEN_LEGGINGS_NAME);
		GameRegistry.registerItem(woodenBoots, Strings.WOODEN_BOOTS_NAME);

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
		GameRegistry.registerItem(pocketBat, Strings.POCKET_BAT_NAME);
		GameRegistry.registerItem(batStew, Strings.BAT_STEW_NAME);
		if (GanysSurface.activateChocolate)
			GameRegistry.registerItem(chocolateBar, Strings.CHOCOLATE_BAR_NAME);
		GameRegistry.registerItem(horsalyser, Strings.HORSALYSER_NAME);
		GameRegistry.registerItem(horseSpawner, Strings.HORSE_SPAWNER_NAME);
		GameRegistry.registerItem(chargedCreeperSpawner, Strings.CHARGED_CREEPER_SPAWNER_NAME);
	}

	private static void registerForge() {
		MinecraftForge.addGrassSeed(new ItemStack(camelliaSeeds), 8);
	}

	private static void registerDispenserActions() {
		BlockDispenser.dispenseBehaviorRegistry.putObject(pocketBat, new DispenserBehaviorPocketBat());
		BlockDispenser.dispenseBehaviorRegistry.putObject(poop, new DispenserBehaviorPoop());
		BlockDispenser.dispenseBehaviorRegistry.putObject(rot, new DispenserBehaviorRot());
		BlockDispenser.dispenseBehaviorRegistry.putObject(horseSpawner, new DispenserBehaviorHorseSpawner());
		BlockDispenser.dispenseBehaviorRegistry.putObject(chargedCreeperSpawner, new DispenserBehaviorChargedCreeperSpawner());
	}
}