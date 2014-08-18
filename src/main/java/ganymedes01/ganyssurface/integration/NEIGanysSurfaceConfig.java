package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.gui.inventory.GuiDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiPortableDualWorkTable;
import ganymedes01.ganyssurface.client.gui.inventory.GuiWorkTable;
import ganymedes01.ganyssurface.integration.nei.OMCYieldHandler;
import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
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
		if (!GanysSurface.enableQuiver)
			API.hideItem(new ItemStack(ModItems.quiver));

		// 1.8 Stuff
		API.addItemListEntry(new ItemStack(Blocks.dirt, 1, 0));
		API.addItemListEntry(new ItemStack(Blocks.dirt, 1, 1));
		API.addItemListEntry(new ItemStack(Blocks.dirt, 1, 2));
		if (!GanysSurface.enableMutton) {
			API.hideItem(new ItemStack(ModItems.rawMutton));
			API.hideItem(new ItemStack(ModItems.cookedMutton));
		}
		if (!GanysSurface.enable18Stones)
			API.hideItem(new ItemStack(ModBlocks.newStones, 1, OreDictionary.WILDCARD_VALUE));
		if (!GanysSurface.enableIronTrapdoor)
			API.hideItem(new ItemStack(ModBlocks.ironTrapdoor));
		if (!GanysSurface.enablePrismarineStuff) {
			API.hideItem(new ItemStack(ModItems.prismarineItems, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModBlocks.prismarineBlocks, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModBlocks.seaLantern));
		}

		API.hideItem(new ItemStack(ModBlocks.doorAcacia));
		API.hideItem(new ItemStack(ModBlocks.doorBirch));
		API.hideItem(new ItemStack(ModBlocks.doorDarkOak));
		API.hideItem(new ItemStack(ModBlocks.doorJungle));
		API.hideItem(new ItemStack(ModBlocks.doorSpruce));
		if (!GanysSurface.enableDoors) {
			API.hideItem(new ItemStack(ModItems.doorAcacia));
			API.hideItem(new ItemStack(ModItems.doorBirch));
			API.hideItem(new ItemStack(ModItems.doorDarkOak));
			API.hideItem(new ItemStack(ModItems.doorJungle));
			API.hideItem(new ItemStack(ModItems.doorSpruce));
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