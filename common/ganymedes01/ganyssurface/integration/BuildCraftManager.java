package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BuildCraftManager extends Integration {

	@Override
	public void init() {
		addFacade(ModBlocks.cushion.blockID);
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "BuildCraft|Transport";
	}

	private void addFacade(int blockID) {
		addFacade(blockID, 0);
	}

	private void addFacade(int blockID, int meta) {
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", blockID + "@" + meta);
	}
}