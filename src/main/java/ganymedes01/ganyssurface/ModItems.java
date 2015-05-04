package ganymedes01.ganyssurface;

import ganymedes01.ganyssurface.dispenser.DispenserBahaviourShears;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorChargedCreeperSpawner;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorHorseSpawner;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorPocketBat;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorPoop;
import ganymedes01.ganyssurface.dispenser.DispenserBehaviorRot;
import ganymedes01.ganyssurface.items.BatNet;
import ganymedes01.ganyssurface.items.BatStew;
import ganymedes01.ganyssurface.items.Beetroot;
import ganymedes01.ganyssurface.items.BeetrootSeeds;
import ganymedes01.ganyssurface.items.BeetrootSoup;
import ganymedes01.ganyssurface.items.CamelliaSeeds;
import ganymedes01.ganyssurface.items.ChargedCreeperSpawner;
import ganymedes01.ganyssurface.items.ChocolateBar;
import ganymedes01.ganyssurface.items.ColouredRedstoneItem;
import ganymedes01.ganyssurface.items.CookedEgg;
import ganymedes01.ganyssurface.items.CupOfTea;
import ganymedes01.ganyssurface.items.DyedChainArmour;
import ganymedes01.ganyssurface.items.DyedIronArmour;
import ganymedes01.ganyssurface.items.EmptyMug;
import ganymedes01.ganyssurface.items.Gearalyser;
import ganymedes01.ganyssurface.items.Horsalyser;
import ganymedes01.ganyssurface.items.HorseSpawner;
import ganymedes01.ganyssurface.items.IcyPickaxe;
import ganymedes01.ganyssurface.items.ItemNewDoor;
import ganymedes01.ganyssurface.items.MankyCupOfTea;
import ganymedes01.ganyssurface.items.MuttonCooked;
import ganymedes01.ganyssurface.items.MuttonRaw;
import ganymedes01.ganyssurface.items.ObsidianHead;
import ganymedes01.ganyssurface.items.Painting;
import ganymedes01.ganyssurface.items.PineCone;
import ganymedes01.ganyssurface.items.PineNuts;
import ganymedes01.ganyssurface.items.PocketCritter;
import ganymedes01.ganyssurface.items.Poop;
import ganymedes01.ganyssurface.items.PortableDualWorkTable;
import ganymedes01.ganyssurface.items.PrismarineItems;
import ganymedes01.ganyssurface.items.Quiver;
import ganymedes01.ganyssurface.items.RoastedSquid;
import ganymedes01.ganyssurface.items.Rot;
import ganymedes01.ganyssurface.items.Stick;
import ganymedes01.ganyssurface.items.StorageCase;
import ganymedes01.ganyssurface.items.TeaBag;
import ganymedes01.ganyssurface.items.TeaLeaves;
import ganymedes01.ganyssurface.items.VillageFinder;
import ganymedes01.ganyssurface.items.WoodenArmour;
import ganymedes01.ganyssurface.items.WoodenWrench;

import java.lang.reflect.Field;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockWood;
import net.minecraft.init.Items;
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
	public static final Item gearalyser = new Gearalyser();
	public static final Item pineCone = new PineCone();
	public static final Item pineNuts = new PineNuts();
	public static final Item quiver = new Quiver();
	public static final Item painting = new Painting();
	public static final Item stick = new Stick();

	// 1.8 Stuff
	public static final Item rawMutton = new MuttonRaw();
	public static final Item cookedMutton = new MuttonCooked();
	public static final Item prismarineItems = new PrismarineItems();
	public static final Item[] doors = new Item[BlockWood.field_150096_a.length - 1];

	// MC:PE
	public static final Item beetroot = new Beetroot();
	public static final Item beetrootSoup = new BeetrootSoup();
	public static final Item beetrootSeeds = new BeetrootSeeds();

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

	static {
		for (int i = 0; i < doors.length; i++)
			doors[i] = new ItemNewDoor(i + 1);
	}

	public static void init() {
		try {
			for (Field f : ModItems.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Item)
					registerItem((Item) obj);
				else if (obj instanceof Item[])
					for (Item item : (Item[]) obj)
						registerItem(item);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		if (GanysSurface.enableTea)
			MinecraftForge.addGrassSeed(new ItemStack(camelliaSeeds), 5);

		if (GanysSurface.enablePocketCritters)
			BlockDispenser.dispenseBehaviorRegistry.putObject(pocketCritter, new DispenserBehaviorPocketBat());
		if (GanysSurface.enablePoop)
			BlockDispenser.dispenseBehaviorRegistry.putObject(poop, new DispenserBehaviorPoop());
		if (GanysSurface.enablePoop || GanysSurface.enableRot)
			BlockDispenser.dispenseBehaviorRegistry.putObject(rot, new DispenserBehaviorRot());
		if (GanysSurface.enableSpawnEggs) {
			BlockDispenser.dispenseBehaviorRegistry.putObject(horseSpawner, new DispenserBehaviorHorseSpawner());
			BlockDispenser.dispenseBehaviorRegistry.putObject(chargedCreeperSpawner, new DispenserBehaviorChargedCreeperSpawner());
		}
		if (GanysSurface.enableDispenserShears)
			BlockDispenser.dispenseBehaviorRegistry.putObject(Items.shears, new DispenserBahaviourShears());
	}

	private static void registerItem(Item item) {
		if (!(item instanceof IConfigurable) || ((IConfigurable) item).isEnabled()) {
			String name = item.getUnlocalizedName();
			String[] strings = name.split("\\.");
			GameRegistry.registerItem(item, strings[strings.length - 1]);
		}
	}
}