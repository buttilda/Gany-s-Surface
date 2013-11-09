package ganymedes01.ganyssurface.configuration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.IdGenerator;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ConfigurationHandler {

	public static Configuration configuration;
	private static IdGenerator idGen = new IdGenerator(Reference.ITEM_ID_BASE, Reference.BLOCK_ID_BASE);

	private static int configBlock(String name) {
		return configuration.getBlock(name, idGen.getNextBlockID()).getInt(idGen.getLastBlockID());
	}

	private static int configItem(String name) {
		return configuration.getItem(name, idGen.getNextItemID()).getInt(idGen.getLastItemID());
	}

	public static void init(File configFile) {
		configuration = new Configuration(configFile);

		try {
			configuration.load();

			// Blocks
			ModIDs.CAMELLIA_CROP_ID = configBlock(Strings.CAMELLIA_CROP_NAME);
			ModIDs.RAIN_DETECTOR_ID = configBlock(Strings.RAIN_DETECTOR_NAME);
			ModIDs.BLOCK_DETECTOR_ID = configBlock(Strings.BLOCK_DETECTOR_NAME);
			ModIDs.DISLOCATOR_ID = configBlock(Strings.DISLOCATOR_NAME);
			ModIDs.SENSORING_DISLOCATOR_ID = configBlock(Strings.SENSORING_DISLOCATOR_NAME);
			ModIDs.CUBIC_SENSORING_DISLOCATOR_ID = configBlock(Strings.CUBIC_SENSORING_DISLOCATOR_NAME);
			ModIDs.DISGUISED_TRAP_DOOR_OAK_ID = configBlock(Strings.DISGUISED_TRAP_DOOR_OAK_NAME);
			ModIDs.DISGUISED_TRAP_DOOR_SPRUCE_ID = configBlock(Strings.DISGUISED_TRAP_DOOR_SPRUCE_NAME);
			ModIDs.DISGUISED_TRAP_DOOR_BIRCH_ID = configBlock(Strings.DISGUISED_TRAP_DOOR_BIRCH_NAME);
			ModIDs.DISGUISED_TRAP_DOOR_JUNGLE_ID = configBlock(Strings.DISGUISED_TRAP_DOOR_JUNGLE_NAME);
			ModIDs.WORK_TABLE_ID = configBlock(Strings.WORK_TABLE_NAME);
			ModIDs.ORGANIC_MATTER_COMPRESSOR_ID = configBlock(Strings.ORGANIC_MATTER_COMPRESSOR_NAME);
			ModIDs.CUSHION_ID = configBlock(Strings.CUSHION_NAME);
			ModIDs.CHOCOLATE_CAKE_ID = configBlock(Strings.CHOCOLATE_CAKE_NAME);
			ModIDs.ITEM_DISPLAY_ID = configBlock(Strings.ITEM_DISPLAY_NAME);
			ModIDs.CHEST_PROPELLANT_ID = configBlock(Strings.CHEST_PROPELLANT_NAME);
			ModIDs.FERTILIZED_SOIL_ID = configBlock(Strings.FERTILIZED_SOIL_NAME);
			ModIDs.PLANTER_ID = configBlock(Strings.PLANTER_NAME);
			ModIDs.LANTERN_ID = configBlock(Strings.LANTERN_NAME);
			ModIDs.INK_HARVESTER_ID = configBlock(Strings.INK_HARVESTER_NAME);
			ModIDs.SLIME_BLOCK_ID = configBlock(Strings.SLIME_BLOCK_NAME);

			// Armour
			ModIDs.WOODEN_HELMET_ID = configItem(Strings.WOODEN_HELMET_NAME);
			ModIDs.WOODEN_CHESTPLATE_ID = configItem(Strings.WOODEN_CHESTPLATE_NAME);
			ModIDs.WOODEN_LEGGINGS_ID = configItem(Strings.WOODEN_LEGGINGS_NAME);
			ModIDs.WOODEN_BOOTS_ID = configItem(Strings.WOODEN_BOOTS_NAME);

			// Items
			ModIDs.ROT_ID = configItem(Strings.ROT_NAME);
			ModIDs.CAMELLIA_SEEDS_ID = configItem(Strings.CAMELLIA_SEEDS_NAME);
			ModIDs.TEA_LEAVES_ID = configItem(Strings.TEA_LEAVES_NAME);
			ModIDs.TEA_BAG_ID = configItem(Strings.TEA_BAG_NAME);
			ModIDs.EMPTY_MUG_ID = configItem(Strings.EMPTY_MUG_NAME);
			ModIDs.CUP_OF_TEA_ID = configItem(Strings.CUP_OF_TEA_NAME);
			ModIDs.MANKY_CUP_OF_TEA_ID = configItem(Strings.MANKY_CUP_OF_TEA_NAME);
			ModIDs.POOP_ID = configItem(Strings.POOP_NAME);
			ModIDs.COOKED_EGG_ID = configItem(Strings.COOKED_EGG_NAME);
			ModIDs.OBSIDIAN_HEAD_ID = configItem(Strings.OBSIDIAN_HEAD_NAME);
			ModIDs.WOODEN_WRENCH_ID = configItem(Strings.WOODEN_WRENCH_NAME);
			ModIDs.BAT_NET_ID = configItem(Strings.BAT_NET_NAME);
			ModIDs.POCKET_BAT_ID = configItem(Strings.POCKET_BAT_NAME);
			ModIDs.BAT_STEW_ID = configItem(Strings.BAT_STEW_NAME);
			ModIDs.CHOCOLATE_BAR_ID = configItem(Strings.CHOCOLATE_BAR_NAME);
			ModIDs.HORSALYSER_ID = configItem(Strings.HORSALYSER_NAME);
			ModIDs.HORSE_SPAWNER_ID = configItem(Strings.HORSE_SPAWNER_NAME);
			ModIDs.CHARGED_CREEPER_SPAWNER_ID = configItem(Strings.CHARGED_CREEPER_SPAWNER_NAME);

			// Others
			GanysSurface.mobsShouldPoop = configuration.get("Others", Strings.MOBS_SHOULD_POOP, true).getBoolean(true);
			GanysSurface.activateChocolate = configuration.get("Others", Strings.ACTIVATE_CHOCOLATE, true).getBoolean(true);
			GanysSurface.shouldDoVersionCheck = configuration.get("Others", Strings.SHOULD_DO_VERSION_CHECK, true).getBoolean(true);
			GanysSurface.forceAllContainersOpen = configuration.get("Others", Strings.FORCE_ALL_CONTAINERS_OPEN, false).getBoolean(false);
			GanysSurface.maxLevelOMCWorks = configuration.get("Others", Strings.MAX_LEVEL_OMC_WORKS, 15).getInt(15);
			GanysSurface.inkHarvesterMaxStrike = configuration.get("Others", Strings.INK_HARVESTER_MAX_STRIKE, 5).getInt(5);

		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}
}