package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorChargedCreeperSpawner;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorHorseSpawner;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorPocketBat;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorPoop;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorRot;
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
	public static final Item rot = new Rot();
	public static final Item camelliaSeeds = new CamelliaSeeds();
	public static final Item teaLeaves = new TeaLeaves();
	public static final Item teaBag = new TeaBag();
	public static final Item emptyMug = new EmptyMug();
	public static final Item cupOfTea = new CupOfTea();
	public static final Item mankyCupOfTea = new MankyCupOfTea();
	public static final Item poop = new Poop();
	public static final Item cookedEgg = new CookedEgg();
	public static final Item obsidianHead = new ObsidianHead();
	public static final Item woodenWrench = new WoodenWrench();
	public static final Item batNet = new BatNet();
	public static final Item pocketCritter = new PocketCritter();
	public static final Item batStew = new BatStew();
	public static final Item chocolateBar = new ChocolateBar();
	public static final Item horsalyser = new Horsalyser();
	public static final Item horseSpawner = new HorseSpawner();
	public static final Item chargedCreeperSpawner = new ChargedCreeperSpawner();
	public static final Item colouredRedstone = new ColouredRedstoneItem();
	public static final Item villageFinder = new VillageFinder();
	public static final Item portalDualWorkTable = new PortableDualWorkTable();
	public static final Item icyPickaxe = new IcyPickaxe();
	public static final Item roastedSquid = new RoastedSquid();
	public static final Item storageCase = new StorageCase();

	// Armour
	public static final Item woodenHelmet = new WoodenArmour(0);
	public static final Item woodenChestplate = new WoodenArmour(1);
	public static final Item woodenLeggings = new WoodenArmour(2);
	public static final Item woodenBoots = new WoodenArmour(3);

	public static final Item dyedIronHelmet = new DyedIronArmour(0);
	public static final Item dyedIronChestplate = new DyedIronArmour(1);
	public static final Item dyedIronLeggings = new DyedIronArmour(2);
	public static final Item dyedIronBoots = new DyedIronArmour(3);

	public static final Item dyedChainHelmet = new DyedChainArmour(0);
	public static final Item dyedChainChestplate = new DyedChainArmour(1);
	public static final Item dyedChainLeggings = new DyedChainArmour(2);
	public static final Item dyedChainBoots = new DyedChainArmour(3);

	public static void init() {
		// Armour
		if (GanysSurface.enableWoodenArmour) {
			registerItem(woodenHelmet);
			registerItem(woodenChestplate);
			registerItem(woodenLeggings);
			registerItem(woodenBoots);
		}

		registerItem(dyedIronHelmet);
		registerItem(dyedIronChestplate);
		registerItem(dyedIronLeggings);
		registerItem(dyedIronBoots);

		registerItem(dyedChainHelmet);
		registerItem(dyedChainChestplate);
		registerItem(dyedChainLeggings);
		registerItem(dyedChainBoots);

		// Items
		registerItem(rot);
		registerItem(camelliaSeeds);
		registerItem(teaLeaves);
		registerItem(teaBag);
		registerItem(emptyMug);
		registerItem(cupOfTea);
		registerItem(mankyCupOfTea);
		registerItem(poop);
		registerItem(cookedEgg);
		registerItem(obsidianHead);
		registerItem(woodenWrench);
		registerItem(batNet);
		registerItem(pocketCritter);
		registerItem(batStew);
		if (GanysSurface.activateChocolate)
			registerItem(chocolateBar);
		registerItem(horsalyser);
		registerItem(horseSpawner);
		registerItem(chargedCreeperSpawner);
		registerItem(colouredRedstone);
		registerItem(villageFinder);
		registerItem(portalDualWorkTable);
		registerItem(icyPickaxe);
		registerItem(roastedSquid);

		if (GanysSurface.enableCamilaSeedsToDropFromGrass)
			MinecraftForge.addGrassSeed(new ItemStack(camelliaSeeds), 5);

		BlockDispenser.dispenseBehaviorRegistry.putObject(pocketCritter, new DispenserBehaviorPocketBat());
		BlockDispenser.dispenseBehaviorRegistry.putObject(poop, new DispenserBehaviorPoop());
		BlockDispenser.dispenseBehaviorRegistry.putObject(rot, new DispenserBehaviorRot());
		BlockDispenser.dispenseBehaviorRegistry.putObject(horseSpawner, new DispenserBehaviorHorseSpawner());
		BlockDispenser.dispenseBehaviorRegistry.putObject(chargedCreeperSpawner, new DispenserBehaviorChargedCreeperSpawner());
	}

	private static void registerItem(Item item) {
		String name = item.getUnlocalizedName();
		String[] strings = name.split("\\.");
		GameRegistry.registerItem(item, strings[strings.length - 1]);
	}
}