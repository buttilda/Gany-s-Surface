package ganymedes01.ganyssurface.configuration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.lib.BlocksID;
import ganymedes01.ganyssurface.lib.ItemsID;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

public class ConfigurationHandler {

	public static Configuration configuration;

	public static void init(File configFile) {
		configuration = new Configuration(configFile);

		try {
			configuration.load();

			// Blocks
			BlocksID.CAMELLIA_CROP_ID = configuration.getBlock(Strings.CAMELLIA_CROP_NAME, BlocksID.CAMELLIA_CROP_ID_DEFAULT).getInt(BlocksID.CAMELLIA_CROP_ID_DEFAULT);
			BlocksID.RAIN_DETECTOR_ID = configuration.getBlock(Strings.RAIN_DETECTOR_NAME, BlocksID.RAIN_DETECTOR_ID_DEFAULT).getInt(BlocksID.RAIN_DETECTOR_ID_DEFAULT);
			BlocksID.BLOCK_DETECTOR_ID = configuration.getBlock(Strings.BLOCK_DETECTOR_NAME, BlocksID.BLOCK_DETECTOR_ID_DEFAULT).getInt(BlocksID.BLOCK_DETECTOR_ID_DEFAULT);
			BlocksID.DISLOCATOR_ID = configuration.getBlock(Strings.DISLOCATOR_NAME, BlocksID.DISLOCATOR_ID_DEFAULT).getInt(BlocksID.DISLOCATOR_ID_DEFAULT);
			BlocksID.SENSORING_DISLOCATOR_ID = configuration.getBlock(Strings.SENSORING_DISLOCATOR_NAME, BlocksID.SENSORING_DISLOCATOR_ID_DEFAULT).getInt(BlocksID.SENSORING_DISLOCATOR_ID_DEFAULT);
			BlocksID.CUBIC_SENSORING_DISLOCATOR_ID = configuration.getBlock(Strings.CUBIC_SENSORING_DISLOCATOR_NAME, BlocksID.CUBIC_SENSORING_DISLOCATOR_ID_DEFAULT).getInt(BlocksID.CUBIC_SENSORING_DISLOCATOR_ID_DEFAULT);
			BlocksID.DISGUISED_TRAP_DOOR_OAK_ID = configuration.getBlock(Strings.DISGUISED_TRAP_DOOR_OAK_NAME, BlocksID.DISGUISED_TRAP_DOOR_OAK_ID_DEFAULT).getInt(BlocksID.DISGUISED_TRAP_DOOR_OAK_ID_DEFAULT);
			BlocksID.DISGUISED_TRAP_DOOR_SPRUCE_ID = configuration.getBlock(Strings.DISGUISED_TRAP_DOOR_SPRUCE_NAME, BlocksID.DISGUISED_TRAP_DOOR_SPRUCE_ID_DEFAULT).getInt(BlocksID.DISGUISED_TRAP_DOOR_SPRUCE_ID_DEFAULT);
			BlocksID.DISGUISED_TRAP_DOOR_BIRCH_ID = configuration.getBlock(Strings.DISGUISED_TRAP_DOOR_BIRCH_NAME, BlocksID.DISGUISED_TRAP_DOOR_BIRCH_ID_DEFAULT).getInt(BlocksID.DISGUISED_TRAP_DOOR_BIRCH_ID_DEFAULT);
			BlocksID.DISGUISED_TRAP_DOOR_JUNGLE_ID = configuration.getBlock(Strings.DISGUISED_TRAP_DOOR_JUNGLE_NAME, BlocksID.DISGUISED_TRAP_DOOR_JUNGLE_ID_DEFAULT).getInt(BlocksID.DISGUISED_TRAP_DOOR_JUNGLE_ID_DEFAULT);
			BlocksID.WORK_TABLE_ID = configuration.getBlock(Strings.WORK_TABLE_NAME, BlocksID.WORK_TABLE_ID_DEFAULT).getInt(BlocksID.WORK_TABLE_ID_DEFAULT);
			BlocksID.ORGANIC_MATTER_COMPRESSOR_ID = configuration.getBlock(Strings.ORGANIC_MATTER_COMPRESSOR_NAME, BlocksID.ORGANIC_MATTER_COMPRESSOR_ID_DEFAULT).getInt(BlocksID.ORGANIC_MATTER_COMPRESSOR_ID_DEFAULT);
			BlocksID.CUSHION_ID = configuration.getBlock(Strings.CUSHION_NAME, BlocksID.CUSHION_ID_DEFAULT).getInt(BlocksID.CUSHION_ID_DEFAULT);
			BlocksID.CHOCOLATE_CAKE_ID = configuration.getBlock(Strings.CHOCOLATE_CAKE_NAME, BlocksID.CHOCOLATE_CAKE_ID_DEFAULT).getInt(BlocksID.CHOCOLATE_CAKE_ID_DEFAULT);
			BlocksID.ITEM_DISPLAY_ID = configuration.getBlock(Strings.ITEM_DISPLAY_NAME, BlocksID.ITEM_DISPLAY_ID_DEFAULT).getInt(BlocksID.ITEM_DISPLAY_ID_DEFAULT);
			BlocksID.CHEST_PROPELLANT_ID = configuration.getBlock(Strings.CHEST_PROPELLANT_NAME, BlocksID.CHEST_PROPELLANT_ID_DEFAULT).getInt(BlocksID.CHEST_PROPELLANT_ID_DEFAULT);

			// Armour
			ItemsID.WOODEN_HELMET_ID = configuration.getItem(Strings.WOODEN_HELMET_NAME, ItemsID.WOODEN_HELMET_ID_DEFAULT).getInt(ItemsID.WOODEN_HELMET_ID_DEFAULT);
			ItemsID.WOODEN_CHESTPLATE_ID = configuration.getItem(Strings.WOODEN_CHESTPLATE_NAME, ItemsID.WOODEN_CHESTPLATE_ID_DEFAULT).getInt(ItemsID.WOODEN_CHESTPLATE_ID_DEFAULT);
			ItemsID.WOODEN_LEGGINGS_ID = configuration.getItem(Strings.WOODEN_LEGGINGS_NAME, ItemsID.WOODEN_LEGGINGS_ID_DEFAULT).getInt(ItemsID.WOODEN_LEGGINGS_ID_DEFAULT);
			ItemsID.WOODEN_BOOTS_ID = configuration.getItem(Strings.WOODEN_BOOTS_NAME, ItemsID.WOODEN_BOOTS_ID_DEFAULT).getInt(ItemsID.WOODEN_BOOTS_ID_DEFAULT);

			// Items
			ItemsID.ROT_ID = configuration.getItem(Strings.ROT_NAME, ItemsID.ROT_ID_DEFAULT).getInt(ItemsID.ROT_ID_DEFAULT);
			ItemsID.CAMELLIA_SEEDS_ID = configuration.getItem(Strings.CAMELLIA_SEEDS_NAME, ItemsID.CAMELLIA_SEEDS_ID_DEFAULT).getInt(ItemsID.CAMELLIA_SEEDS_ID_DEFAULT);
			ItemsID.TEA_LEAVES_ID = configuration.getItem(Strings.TEA_LEAVES_NAME, ItemsID.TEA_LEAVES_ID_DEFAULT).getInt(ItemsID.TEA_LEAVES_ID_DEFAULT);
			ItemsID.TEA_BAG_ID = configuration.getItem(Strings.TEA_BAG_NAME, ItemsID.TEA_BAG_ID_DEFAULT).getInt(ItemsID.TEA_BAG_ID_DEFAULT);
			ItemsID.EMPTY_MUG_ID = configuration.getItem(Strings.EMPTY_MUG_NAME, ItemsID.EMPTY_MUG_ID_DEFAULT).getInt(ItemsID.EMPTY_MUG_ID_DEFAULT);
			ItemsID.CUP_OF_TEA_ID = configuration.getItem(Strings.CUP_OF_TEA_NAME, ItemsID.CUP_OF_TEA_ID_DEFAULT).getInt(ItemsID.CUP_OF_TEA_ID_DEFAULT);
			ItemsID.MANKY_CUP_OF_TEA_ID = configuration.getItem(Strings.MANKY_CUP_OF_TEA_NAME, ItemsID.MANKY_CUP_OF_TEA_ID_DEFAULT).getInt(ItemsID.MANKY_CUP_OF_TEA_ID_DEFAULT);
			ItemsID.POOP_ID = configuration.getItem(Strings.POOP_NAME, ItemsID.POOP_ID_DEFAULT).getInt(ItemsID.POOP_ID_DEFAULT);
			ItemsID.FERTILIZER_ID = configuration.getItem(Strings.FERTILIZER_NAME, ItemsID.FERTILIZER_ID_DEFAULT).getInt(ItemsID.FERTILIZER_ID_DEFAULT);
			ItemsID.COOKED_EGG_ID = configuration.getItem(Strings.COOKED_EGG_NAME, ItemsID.COOKED_EGG_ID_DEFAULT).getInt(ItemsID.COOKED_EGG_ID_DEFAULT);
			ItemsID.OBSIDIAN_HEAD_ID = configuration.getItem(Strings.OBSIDIAN_HEAD_NAME, ItemsID.OBSIDIAN_HEAD_ID_DEFAULT).getInt(ItemsID.OBSIDIAN_HEAD_ID_DEFAULT);
			ItemsID.WOODEN_WRENCH_ID = configuration.getItem(Strings.WOODEN_WRENCH_NAME, ItemsID.WOODEN_WRENCH_ID_DEFAULT).getInt(ItemsID.WOODEN_WRENCH_ID_DEFAULT);
			ItemsID.BAT_NET_ID = configuration.getItem(Strings.BAT_NET_NAME, ItemsID.BAT_NET_ID_DEFAULT).getInt(ItemsID.BAT_NET_ID_DEFAULT);
			ItemsID.POCKET_BAT_ID = configuration.getItem(Strings.POCKET_BAT_NAME, ItemsID.POCKET_BAT_ID_DEFAULT).getInt(ItemsID.POCKET_BAT_ID_DEFAULT);
			ItemsID.BAT_STEW_ID = configuration.getItem(Strings.BAT_STEW_NAME, ItemsID.BAT_STEW_ID_DEFAULT).getInt(ItemsID.BAT_STEW_ID_DEFAULT);
			ItemsID.CHOCOLATE_BAR_ID = configuration.getItem(Strings.CHOCOLATE_BAR_NAME, ItemsID.CHOCOLATE_BAR_ID_DEFAULT).getInt(ItemsID.CHOCOLATE_BAR_ID_DEFAULT);

			// Others
			GanysSurface.mobsShouldPoop = configuration.get("Others", Strings.MOBS_SHOULD_POOP, true).getBoolean(true);
			GanysSurface.activateChocolate = configuration.get("Others", Strings.ACTIVATE_CHOCOLATE, true).getBoolean(true);

		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}
}
