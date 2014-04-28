package ganymedes01.ganyssurface.configuration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.integration.Integration;
import ganymedes01.ganyssurface.integration.ModIntegrator;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLLog;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ConfigurationHandler {

	public static Configuration configuration;

	private static int configInteger(String name, int def) {
		int config = configuration.get("Others", name, def).getInt(def);
		return config > 0 ? config : def;
	}

	private static boolean configBoolean(String name, boolean def) {
		return configuration.get("Others", name, def).getBoolean(def);
	}

	private static boolean configIntegrationBoolean(String modID) {
		return configuration.get("Mod Integration", "Integrate " + modID, true).getBoolean(true);
	}

	public static void init(File configFile) {
		configuration = new Configuration(configFile);

		try {
			configuration.load();

			// Mod Integration
			for (Integration integration : ModIntegrator.modIntegrations)
				integration.setShouldIntegrate(configIntegrationBoolean(integration.getModID()));

			// Others
			GanysSurface.mobsShouldPoop = configBoolean(Strings.MOBS_SHOULD_POOP, true);
			GanysSurface.activateChocolate = configBoolean(Strings.ACTIVATE_CHOCOLATE, true);
			GanysSurface.shouldDoVersionCheck = configBoolean(Strings.SHOULD_DO_VERSION_CHECK, true);
			GanysSurface.forceAllContainersOpen = configBoolean(Strings.FORCE_ALL_CONTAINERS_OPEN, false);
			GanysSurface.enableMarket = configBoolean(Strings.ENABLE_MARKET, true);
			GanysSurface.enableWoodenArmour = configBoolean(Strings.ENABLE_WOODEN_ARMOUR, true);
			GanysSurface.enableCamilaSeedsToDropFromGrass = configBoolean(Strings.ENABLE_CAMILA_SEEDS_TO_DROP_FROM_GRASS, true);
			GanysSurface.enableNormalWitchSpawn = configBoolean(Strings.ENABLE_NORMAL_WITCH_SPAWN, true);

			GanysSurface.maxLevelOMCWorks = configInteger(Strings.MAX_LEVEL_OMC_WORKS, 15);
			GanysSurface.inkHarvesterMaxStrike = configInteger(Strings.INK_HARVESTER_MAX_STRIKE, 5);
			GanysSurface.poopingChance = configInteger(Strings.POOPING_CHANCE, 15000);

		} catch (Exception e) {
			FMLLog.severe(Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}
}