package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.client.gui.inventory.GuiDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiPortableDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.integration.nei.MarketSalesHandler;
import ganymedes01.ganyssurface.integration.nei.OMCYieldHandler;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
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
		} else
			API.hideItem(new ItemStack(ModBlocks.market));

		API.registerRecipeHandler(new OMCYieldHandler());
		API.registerUsageHandler(new OMCYieldHandler());

		API.hideItem(new ItemStack(ModBlocks.camelliaCrop));
		API.hideItem(new ItemStack(ModItems.mankyCupOfTea));
		for (Block wire : ModBlocks.colouredRedstone)
			if (wire != null)
				API.hideItem(new ItemStack(wire));
		API.hideItem(new ItemStack(ModItems.dyedIronHelmet));
		API.hideItem(new ItemStack(ModItems.dyedIronChestplate));
		API.hideItem(new ItemStack(ModItems.dyedIronLeggings));
		API.hideItem(new ItemStack(ModItems.dyedIronBoots));
		API.hideItem(new ItemStack(ModBlocks.poop));
		API.hideItem(new ItemStack(ModItems.dyedChainHelmet));
		API.hideItem(new ItemStack(ModItems.dyedChainChestplate));
		API.hideItem(new ItemStack(ModItems.dyedChainLeggings));
		API.hideItem(new ItemStack(ModItems.dyedChainBoots));
		API.hideItem(new ItemStack(ModItems.storageCase));
		if (!GanysSurface.enableChocolate) {
			API.hideItem(new ItemStack(ModBlocks.chocolateCake));
			API.hideItem(new ItemStack(ModItems.chocolateBar));
		}
		if (!GanysSurface.enableWoodenArmour) {
			API.hideItem(new ItemStack(ModItems.woodenHelmet));
			API.hideItem(new ItemStack(ModItems.woodenChestplate));
			API.hideItem(new ItemStack(ModItems.woodenLeggings));
			API.hideItem(new ItemStack(ModItems.woodenBoots));
		}
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