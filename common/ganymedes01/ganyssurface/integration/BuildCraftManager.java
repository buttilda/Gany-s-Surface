package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.integration.bc.FillerFillUndergroundCaves;
import net.minecraft.block.Block;
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

		FillerManager.registry.addRecipe(new FillerFillUndergroundCaves(), new Object[] { "bbb", "bbb", "bgb", 'g', Block.glass, 'b', Block.brick });
	}

	private static void addFacade(int blockID) {
		addFacade(blockID, 0);
	}

	private static void addFacade(int blockID, int meta) {
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", blockID + "@" + meta);
	}
}