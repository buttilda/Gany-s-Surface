package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
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
	public static Item fertilizer;
	public static Item cookedEgg;
	public static Item obsidianHead;
	public static Item woodenWrench;
	public static Item batNet;
	public static Item pocketBat;
	public static Item batStew;
	public static Item chocolateBar;

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
		rot = new Rot(ModIDs.ROT_ID);
		camelliaSeeds = new CamelliaSeeds(ModIDs.CAMELLIA_SEEDS_ID);
		teaLeaves = new TeaLeaves(ModIDs.TEA_LEAVES_ID);
		teaBag = new TeaBag(ModIDs.TEA_BAG_ID);
		emptyMug = new EmptyMug(ModIDs.EMPTY_MUG_ID);
		cupOfTea = new CupOfTea(ModIDs.CUP_OF_TEA_ID).setCreativeTab(GanysSurface.surfaceTab);
		mankyCupOfTea = new MankyCupOfTea(ModIDs.MANKY_CUP_OF_TEA_ID);
		poop = new Poop(ModIDs.POOP_ID);
		fertilizer = new Fertilizer(ModIDs.FERTILIZER_ID);
		cookedEgg = new CookedEgg(ModIDs.COOKED_EGG_ID);
		obsidianHead = new ObsidianHead(ModIDs.OBSIDIAN_HEAD_ID);
		woodenWrench = new WoodenWrench(ModIDs.WOODEN_WRENCH_ID);
		batNet = new BatNet(ModIDs.BAT_NET_ID);
		pocketBat = new PocketBat(ModIDs.POCKET_BAT_ID);
		batStew = new BatStew(ModIDs.BAT_STEW_ID);
		chocolateBar = new ChocolateBar(ModIDs.CHOCOLATE_BAR_ID);

		registerNames();
		registerForge();
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
		GameRegistry.registerItem(poop, Strings.POOP_NAME);
		GameRegistry.registerItem(fertilizer, Strings.FERTILIZER_NAME);
		GameRegistry.registerItem(cookedEgg, Strings.COOKED_EGG_NAME);
		GameRegistry.registerItem(obsidianHead, Strings.OBSIDIAN_HEAD_NAME);
		GameRegistry.registerItem(woodenWrench, Strings.WOODEN_WRENCH_NAME);
		GameRegistry.registerItem(batNet, Strings.BAT_NET_NAME);
		GameRegistry.registerItem(pocketBat, Strings.POCKET_BAT_NAME);
		GameRegistry.registerItem(batStew, Strings.BAT_STEW_NAME);
		if (GanysSurface.activateChocolate)
			GameRegistry.registerItem(chocolateBar, Strings.CHOCOLATE_BAR_NAME);
	}

	private static void registerForge() {
		MinecraftForge.addGrassSeed(new ItemStack(camelliaSeeds), 8);
	}
}
