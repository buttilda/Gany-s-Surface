package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.client.gui.inventory.GuiDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiPortableDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.integration.nei.MarketSalesHandler;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.block.Block;
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

		API.registerGuiOverlay(GuiDualWorkTable.class, "crafting", 5 + 75, 11);
		API.registerGuiOverlayHandler(GuiDualWorkTable.class, new DefaultOverlayHandler(5 + 75, 11), "crafting");
		API.registerGuiOverlay(GuiPortableDualWorkTable.class, "crafting", 5 + 75, 11);
		API.registerGuiOverlayHandler(GuiPortableDualWorkTable.class, new DefaultOverlayHandler(5 + 75, 11), "crafting");

		if (GanysSurface.enableMarket) {
			API.registerRecipeHandler(new MarketSalesHandler());
			API.registerUsageHandler(new MarketSalesHandler());
		}

		API.hideItem(ModBlocks.camelliaCrop.blockID);
		API.hideItem(ModItems.mankyCupOfTea.itemID);
		for (Block wire : ModBlocks.colouredRedstone)
			if (wire != null)
				API.hideItem(wire.blockID);
		API.hideItem(ModItems.dyedIronHelmet.itemID);
		API.hideItem(ModItems.dyedIronChestplate.itemID);
		API.hideItem(ModItems.dyedIronLeggings.itemID);
		API.hideItem(ModItems.dyedIronBoots.itemID);
		API.hideItem(ModBlocks.poop.blockID);
		API.hideItem(ModItems.dyedChainHelmet.itemID);
		API.hideItem(ModItems.dyedChainChestplate.itemID);
		API.hideItem(ModItems.dyedChainLeggings.itemID);
		API.hideItem(ModItems.dyedChainBoots.itemID);
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