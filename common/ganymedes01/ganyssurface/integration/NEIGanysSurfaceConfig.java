package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.Reference;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class NEIGanysSurfaceConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.hideItem(ModBlocks.camelliaCrop.blockID);
		API.hideItem(ModItems.mankyCupOfTea.itemID);
	}

	@Override
	public String getName() {
		return Reference.MOD_NAME;
	}

	@Override
	public String getVersion() {
		return Reference.VERSION_NUMBER;
	}
}