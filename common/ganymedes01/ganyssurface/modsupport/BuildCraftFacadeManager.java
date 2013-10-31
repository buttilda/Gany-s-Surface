package ganymedes01.ganyssurface.modsupport;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BuildCraftFacadeManager {

	public static void registerFacades() {
		addFacade(ModBlocks.cushion.blockID);
	}

	private static void addFacade(int blockID) {
		addFacade(blockID, 0);
	}

	private static void addFacade(int blockID, int meta) {
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", String.format("%d@%d", blockID, meta));
	}
}
