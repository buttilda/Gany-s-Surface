package ganymedes01.ganyssurface.configuration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.integration.Integration;
import ganymedes01.ganyssurface.integration.ModIntegrator;
import ganymedes01.ganyssurface.lib.Reference;

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
		GanysSurface.mobsShouldPoop = configBoolean("mobsShouldPoop", false, true);
		GanysSurface.enableChocolate = configBoolean("activateChocolate", true, true);
		GanysSurface.shouldDoVersionCheck = configBoolean("shouldDoVersionCheck", true, true);
		GanysSurface.forceAllContainersOpen = configBoolean("forceAllContainersOpen", false, false);
		GanysSurface.enableWoodenArmour = configBoolean("enableWoodenArmour", true, true);
		GanysSurface.enableCamilaSeedsToDropFromGrass = configBoolean("enableCamilaSeedsToDropFromGrass", true, true);
		GanysSurface.poopRandomBonemeals = configBoolean("poopRandomBonemeals", false, true);
		GanysSurface.enableDynamicSnow = configBoolean("enableDynamicSnow", "Snow layers will get taller when it snows and shorter when it stops snowing", false, true);

		GanysSurface.maxLevelOMCWorks = configInteger("maxLevelOMCWorks", false, 15);
		GanysSurface.inkHarvesterMaxStrike = configInteger("inkHarvesterMaxStrike", false, 5);
		GanysSurface.poopingChance = configInteger("poopingChance", false, 15000);
		GanysSurface.enable18Stones = configBoolean("Enable 1.8 Stones", true, true);
		GanysSurface.enableIronTrapdoor = configBoolean("Enable Iron Trapdoor", true, true);
		GanysSurface.enableMutton = configBoolean("Enable Mutton", true, true);
		GanysSurface.enableSpongeTexture = configBoolean("Enable new sponge texture", true, true);
		GanysSurface.enablePrismarineStuff = configBoolean("Enable Prismarine stuff", true, true);
		GanysSurface.enableDispenserShears = configBoolean("Enable dispenser action for shears", true, true);

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}