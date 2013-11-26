package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.integration.bc.FillerFillUndergroundCaves;
import buildcraft.api.filler.FillerManager;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BuildCraftManager {

	public static void init() {
		addFacade(ModBlocks.cushion.blockID);
	}

	public static void postInit() {
		FillerManager.registry.addPattern(new FillerFillUndergroundCaves());
	}

	private static void addFacade(int blockID) {
		addFacade(blockID, 0);
	}

	private static void addFacade(int blockID, int meta) {
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", blockID + "@" + meta);
	}
}