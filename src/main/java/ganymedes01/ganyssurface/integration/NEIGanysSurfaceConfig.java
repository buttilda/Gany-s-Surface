package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.gui.inventory.GuiDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiPortableDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.integration.nei.BannerPatternHandler;
import ganymedes01.ganyssurface.integration.nei.OMCYieldHandler;
import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
		if (GanysSurface.enableWorkTables) {
			API.registerGuiOverlay(GuiWorkTable.class, "crafting");
			API.registerGuiOverlayHandler(GuiWorkTable.class, new DefaultOverlayHandler(), "crafting");

			API.registerGuiOverlay(GuiDualWorkTable.class, "crafting", 5 + 75, 11);
			API.registerGuiOverlayHandler(GuiDualWorkTable.class, new DefaultOverlayHandler(5 + 75, 11), "crafting");
			API.registerGuiOverlay(GuiPortableDualWorkTable.class, "crafting", 5 + 75, 11);
			API.registerGuiOverlayHandler(GuiPortableDualWorkTable.class, new DefaultOverlayHandler(5 + 75, 11), "crafting");
		}

		if (GanysSurface.enableOMC) {
			API.registerRecipeHandler(new OMCYieldHandler());
			API.registerUsageHandler(new OMCYieldHandler());
		}

		if (GanysSurface.enableBanners) {
			API.registerRecipeHandler(new BannerPatternHandler());
			API.registerUsageHandler(new BannerPatternHandler());
		}

		if (GanysSurface.enableTea) {
			API.hideItem(new ItemStack(ModBlocks.camelliaCrop));
			API.hideItem(new ItemStack(ModItems.mankyCupOfTea));
		}

		if (GanysSurface.enableColouredRedstone)
			for (Block wire : ModBlocks.colouredRedstone)
				if (wire != null)
					API.hideItem(new ItemStack(wire));

		if (GanysSurface.enableDyedArmour) {
			API.hideItem(new ItemStack(ModItems.dyedIronHelmet));
			API.hideItem(new ItemStack(ModItems.dyedIronChestplate));
			API.hideItem(new ItemStack(ModItems.dyedIronLeggings));
			API.hideItem(new ItemStack(ModItems.dyedIronBoots));
			API.hideItem(new ItemStack(ModItems.dyedChainHelmet));
			API.hideItem(new ItemStack(ModItems.dyedChainChestplate));
			API.hideItem(new ItemStack(ModItems.dyedChainLeggings));
			API.hideItem(new ItemStack(ModItems.dyedChainBoots));
		}

		if (GanysSurface.enablePoop)
			API.hideItem(new ItemStack(ModBlocks.poop));

		if (GanysSurface.enableEncasers)
			API.hideItem(new ItemStack(ModItems.storageCase));

		if (GanysSurface.enableBeetroots)
			API.hideItem(new ItemStack(ModBlocks.beetroot));

		if (GanysSurface.enableDoors)
			for (Block door : ModBlocks.doors)
				API.hideItem(new ItemStack(door));

		if (GanysSurface.enableFences)
			API.hideItem(new ItemStack(Blocks.fence));
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