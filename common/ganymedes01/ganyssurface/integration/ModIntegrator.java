package ganymedes01.ganyssurface.integration;

import cpw.mods.fml.common.Loader;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ModIntegrator {

	public static void integrateMods() {
		// BuildCraft
		if (Loader.isModLoaded("BuildCraft|Transport"))
			BuildCraftManager.init();

		// ThaumCraft
		if (Loader.isModLoaded("Thaumcraft"))
			ThaumCraftManager.init();

		// Equivalent Exchange 3
		if (Loader.isModLoaded("EE3"))
			EE3Manager.init();

		// Forestry
		if (Loader.isModLoaded("Forestry"))
			ForestryManager.init();
	}
}