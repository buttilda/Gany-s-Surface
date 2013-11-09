package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.Reference;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class NEIGanysSurfaceConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerGuiOverlay(GuiWorkTable.class, "crafting");
		API.registerGuiOverlayHandler(GuiWorkTable.class, new DefaultOverlayHandler(), "crafting");

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