package ganymedes01.ganyssurface.configuration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.BlockStorage;
import ganymedes01.ganyssurface.integration.Integration;
import ganymedes01.ganyssurface.integration.ModIntegrator;
import ganymedes01.ganyssurface.lib.EnumColour;
import ganymedes01.ganyssurface.lib.Reference;

import java.awt.Color;
import java.io.File;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
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
	public String[] usedCategories = { Configuration.CATEGORY_GENERAL, "mod integration", "wool colours" };

	private int configInteger(String name, boolean requireRestart, int def) {
		return configInteger(name, null, requireRestart, def);
	}

	private int configInteger(String name, String tooltip, boolean requireRestart, int def) {
		int config = configFile.get(Configuration.CATEGORY_GENERAL, name, def, tooltip).getInt(def);
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

		// Blocks/Items
		GanysSurface.enableChocolate = configBoolean("activateChocolate", true, true);
		GanysSurface.enablePoop = configBoolean("enablePoop", true, true);
		GanysSurface.enableWoodenArmour = configBoolean("enableWoodenArmour", true, true);
		GanysSurface.enableTea = configBoolean("enableTea", true, true);
		GanysSurface.enableQuiver = configBoolean("enableQuiver", true, true);
		GanysSurface.enablePaintings = configBoolean("Enable paintings", true, true);
		GanysSurface.enableDispenserShears = configBoolean("Enable dispenser action for shears", true, true);
		GanysSurface.enable3DRendering = configBoolean("Enable 3D rendering for vanilla items", "Vanilla items (hoppers, minecarts, etc) render in 3D in your inventory", true, true);
		GanysSurface.enablePlanter = configBoolean("Enable planter", true, true);
		GanysSurface.enableColouredRedstone = configBoolean("Enable coloured redsotne", true, true);
		GanysSurface.enableDislocators = configBoolean("Enable dislocators and block detector", true, true);
		GanysSurface.enableItemDisplay = configBoolean("Enable item display", true, true);
		GanysSurface.enablePineCones = configBoolean("Enable pine cones", true, true);
		GanysSurface.enableCookedEgg = configBoolean("Enable cooked egg", true, true);
		GanysSurface.enablePocketCritters = configBoolean("Enable pocket critters", true, true);
		GanysSurface.enableWorkTables = configBoolean("Enable work tables", true, true);
		GanysSurface.enableLeafWalls = configBoolean("Enable leaf walls", true, true);
		GanysSurface.enableDisguisedTrapdoors = configBoolean("Enable disguised trapdoors", true, true);
		GanysSurface.enableEncasers = configBoolean("Enable encasers", true, true);
		GanysSurface.enableOMC = configBoolean("Enable organic matter compressor", true, true);
		GanysSurface.enableSpawnEggs = configBoolean("Enable spawn eggs", true, true);
		GanysSurface.enableSlimeBlock = configBoolean("Enable slime block", true, true);
		GanysSurface.enableAnalisers = configBoolean("Enable analisers", true, true);
		GanysSurface.enableIcyPick = configBoolean("Enable Icy Pickaxe", true, true);
		GanysSurface.enableFertilisedSoil = configBoolean("Enable Fertilised Soil", true, true);
		GanysSurface.enableChestPropellant = configBoolean("Enable Chest Propellant", true, true);
		GanysSurface.enableInkHarvester = configBoolean("Enable Ink Harvester", true, true);
		GanysSurface.enableRainDetector = configBoolean("Enable Rain Detector", true, true);
		GanysSurface.enableCushion = configBoolean("Enable Cushion", true, true);
		GanysSurface.enableLantern = configBoolean("Enable Lantern", true, true);
		GanysSurface.enableBlockOfCharcoal = configBoolean("Enable Block of Charcoal", true, true);
		GanysSurface.enableRot = configBoolean("Enable Rot", true, true);
		GanysSurface.enableWoodenWrench = configBoolean("Enable Wooden Wrench", true, true);
		GanysSurface.enableVillageFinder = configBoolean("Enable Village Finder", true, true);
		GanysSurface.enableDyedArmour = configBoolean("Enable Iron/Chainmail armour dyeing", true, true);
		GanysSurface.enableRedyeingBlocks = configBoolean("Enable re-dyeing carpets, clay, glass and panes", true, true);
		GanysSurface.enableExtraVanillaRecipes = configBoolean("Enable extra vanilla recipes (for name tags, cobwebs and etc)", true, true);
		GanysSurface.enableEndermanDropsBlocks = configBoolean("Enable enderman to drop the blocks they are carrying", true, true);
		GanysSurface.enableChests = configBoolean("Enable wood specific chests", true, true);
		GanysSurface.enableDynamicTextureChests = configBoolean("Enable dynamic texture for wood chests", true, false);
		GanysSurface.enableSlowRail = configBoolean("Enable Slow Rail", true, true);
		GanysSurface.enableBasalt = configBoolean("Enable Basalt", true, true);
		GanysSurface.enableFlingablePoop = configBoolean("Enable Poop Throwing", false, true);
		GanysSurface.enableBurnableBlocks = configBoolean("Make fences, gates and deadbushes burnable", "They aren't burnable in 1.7.10 but are in 1.8, set this to true to mimic the 1.8 behaviour", false, true);
		GanysSurface.enableWoodenButtons = configBoolean("Enable wooden buttons", true, true);
		GanysSurface.enableWoodenPressurePlates = configBoolean("Enable wooden pressure plates", true, true);
		GanysSurface.enableWoodenTrapdoors = configBoolean("Enable wooden trapdoors", true, true);
		GanysSurface.enableWoodenLadders = configBoolean("Enable wooden ladders", true, true);
		GanysSurface.enableBeetroots = configBoolean("Enable MC:PE beetroots", true, true);
		GanysSurface.enableWoodenSigns = configBoolean("Enable wooden signs", true, true);
		GanysSurface.enableWoodenBookshelves = configBoolean("Enable wooden bookshelves", true, true);
		GanysSurface.enableStorageBlocks = configBoolean("Enable storage blocks", "More specifically: " + BlockStorage.getTypesString(), true, true);
		GanysSurface.enableDyeBlocks = configBoolean("Enable dye blocks", true, true);

		// 1.8 Stuff
		GanysSurface.enable18Stones = configBoolean("Enable 1.8 Stones", true, true);
		GanysSurface.enableIronTrapdoor = configBoolean("Enable Iron Trapdoor", true, true);
		GanysSurface.enableMutton = configBoolean("Enable Mutton", true, true);
		GanysSurface.enablePrismarineStuff = configBoolean("Enable Prismarine stuff", true, true);
		GanysSurface.enableDoors = configBoolean("Enable 1.8 style doors", true, true);
		GanysSurface.enableInvertedDaylightSensor = configBoolean("Enable inverted daylight sensor", true, true);
		GanysSurface.enableCoarseDirt = configBoolean("Enable coarse dirt", true, true);
		GanysSurface.enable18Enchants = configBoolean("Enable 1.8 Enchanting Table", true, true);
		GanysSurface.enableFences = configBoolean("Enable 1.8 wood fences", true, true);
		GanysSurface.enableSilkTouchingMushrooms = configBoolean("Enable Silk Touching of mushroom blocks", true, true);
		GanysSurface.max18StonesPerCluster = configInteger("Max number of 1.8 stones in a cluster", true, GanysSurface.max18StonesPerCluster);
		GanysSurface.enableBanners = configBoolean("Enable 1.8 banners", true, true);
		GanysSurface.enableRedSandstone = configBoolean("Enable 1.8 red sandstone", true, true);

		// Others
		GanysSurface.shouldDoVersionCheck = configBoolean("shouldDoVersionCheck", true, true);
		GanysSurface.poopRandomBonemeals = configBoolean("poopRandomBonemeals", false, true);
		GanysSurface.enableDynamicSnow = configBoolean("enableDynamicSnow", "Snow layers will get taller when it snows and shorter when it stops snowing", false, true);

		GanysSurface.maxLevelOMCWorks = configInteger("maxLevelOMCWorks", false, 15);
		GanysSurface.inkHarvesterMaxStrike = configInteger("inkHarvesterMaxStrike", false, 5);
		GanysSurface.poopingChance = configInteger("poopingChance", "Larger number means poop is LESS likely to happen", false, 15000);

		// Sheep wool colours
		for (int i = 0; i < 16; i++) {
			String def = "";
			for (int j = 0; j < 3; j++)
				def += Integer.toHexString((int) (EntitySheep.fleeceColorTable[i][j] * 255));
			def = "0x" + def.toUpperCase();
			String colour = configFile.get("wool colours", EnumColour.values()[BlockColored.func_150031_c(i)].toString(), def, "Default: " + def).getString();
			float red = Color.decode(colour).getRed() / 255F;
			float green = Color.decode(colour).getGreen() / 255F;
			float blue = Color.decode(colour).getBlue() / 255F;
			EntitySheep.fleeceColorTable[i] = new float[] { red, green, blue };
		}

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}