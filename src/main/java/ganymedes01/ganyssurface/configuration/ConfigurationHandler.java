package ganymedes01.ganyssurface.configuration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.integration.Integration;
import ganymedes01.ganyssurface.integration.ModIntegrator;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ConfigurationHandler {

	public static ConfigurationHandler INSTANCE = new ConfigurationHandler();
	public Configuration configFile;
	public String[] usedCategories = { Configuration.CATEGORY_GENERAL, "mod integration" };

	private int configInteger(String name, boolean requireRestart, int def) {
		int config = configFile.get(Configuration.CATEGORY_GENERAL, name, def).getInt(def);
		return config > 0 ? config : def;
	}

	private boolean configBoolean(String name, String tooltip, boolean requireRestart, boolean def) {
		return configFile.get(Configuration.CATEGORY_GENERAL, name, def, tooltip).getBoolean(def);
	}

	private boolean configBoolean(String name, boolean requireRestart, boolean def) {
		return configBoolean(name, null, requireRestart, def);
	}

	private boolean configIntegrationBoolean(String modID) {
		return configFile.get("Mod Integration", "Integrate " + modID, true).setRequiresMcRestart(true).getBoolean(true);
	}

	public void init(File file) {
		configFile = new Configuration(file);

		syncConfigs();
	}

	private void syncConfigs() {
		// Mod Integration
		for (Integration integration : ModIntegrator.modIntegrations)
			integration.setShouldIntegrate(configIntegrationBoolean(integration.getModID()));

		// Others
		GanysSurface.mobsShouldPoop = configBoolean(Strings.MOBS_SHOULD_POOP, false, true);
		GanysSurface.enableChocolate = configBoolean(Strings.ACTIVATE_CHOCOLATE, true, true);
		GanysSurface.shouldDoVersionCheck = configBoolean(Strings.SHOULD_DO_VERSION_CHECK, true, true);
		GanysSurface.forceAllContainersOpen = configBoolean(Strings.FORCE_ALL_CONTAINERS_OPEN, false, false);
		GanysSurface.enableWoodenArmour = configBoolean(Strings.ENABLE_WOODEN_ARMOUR, true, true);
		GanysSurface.enableCamilaSeedsToDropFromGrass = configBoolean(Strings.ENABLE_CAMILA_SEEDS_TO_DROP_FROM_GRASS, true, true);
		GanysSurface.poopRandomBonemeals = configBoolean(Strings.POOP_RANDOM_BONEMEALS, false, true);
		GanysSurface.enableCumulativeSnow = configBoolean(Strings.CUMULATIVE_SNOW, "Snow layers will get taller when it snows", false, true);

		GanysSurface.maxLevelOMCWorks = configInteger(Strings.MAX_LEVEL_OMC_WORKS, false, 15);
		GanysSurface.inkHarvesterMaxStrike = configInteger(Strings.INK_HARVESTER_MAX_STRIKE, false, 5);
		GanysSurface.poopingChance = configInteger(Strings.POOPING_CHANCE, false, 15000);

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}