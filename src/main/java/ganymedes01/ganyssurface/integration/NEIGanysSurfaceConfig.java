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
		if (!GanysSurface.enableTea) {
			API.hideItem(new ItemStack(ModItems.teaBag));
			API.hideItem(new ItemStack(ModItems.teaLeaves));
			API.hideItem(new ItemStack(ModItems.camelliaSeeds));
			API.hideItem(new ItemStack(ModItems.emptyMug, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModItems.cupOfTea));
		}
		if (!GanysSurface.enablePoop) {
			API.hideItem(new ItemStack(ModItems.poop, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModItems.rot, 1, 1));
		}
		if (!GanysSurface.enablePaintings)
			API.hideItem(new ItemStack(ModItems.painting, 1, OreDictionary.WILDCARD_VALUE));
		if (!GanysSurface.enablePlanter) {
			API.hideItem(new ItemStack(ModBlocks.planter));
			API.hideItem(new ItemStack(ModBlocks.farmManager));
		}
		if (!GanysSurface.enableColouredRedstone) {
			API.hideItem(new ItemStack(ModBlocks.colouredRedstoneBlock, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModItems.colouredRedstone, 1, OreDictionary.WILDCARD_VALUE));
		}
		if (!GanysSurface.enableDislocators) {
			API.hideItem(new ItemStack(ModBlocks.blockDetector));
			API.hideItem(new ItemStack(ModBlocks.dislocator));
			API.hideItem(new ItemStack(ModBlocks.sensitiveDislocator));
			API.hideItem(new ItemStack(ModBlocks.cubicSensitiveDislocator));
			API.hideItem(new ItemStack(ModItems.obsidianHead));
		}
		if (!GanysSurface.enableItemDisplay)
			API.hideItem(new ItemStack(ModBlocks.itemDisplay, 1, OreDictionary.WILDCARD_VALUE));
		if (!GanysSurface.enablePineCones) {
			API.hideItem(new ItemStack(ModItems.pineCone));
			API.hideItem(new ItemStack(ModItems.pineNuts));
		}
		if (!GanysSurface.enablePocketCritters) {
			API.hideItem(new ItemStack(ModItems.pocketCritter, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModItems.batStew));
			API.hideItem(new ItemStack(ModItems.batNet));
			API.hideItem(new ItemStack(ModItems.roastedSquid));
		}
		if (!GanysSurface.enableLeafWalls)
			API.hideItem(new ItemStack(ModBlocks.leafWall, 1, OreDictionary.WILDCARD_VALUE));
		if (!GanysSurface.enableWorkTables) {
			API.hideItem(new ItemStack(ModItems.portalDualWorkTable));
			API.hideItem(new ItemStack(ModBlocks.dualWorkTable));
			API.hideItem(new ItemStack(ModBlocks.workTable));
		}
		if (!GanysSurface.enableDisguisedTrapdoors)
			for (Block trapdoor : ModBlocks.disguisedTrapDoor)
				API.hideItem(new ItemStack(trapdoor));
		if (!GanysSurface.enableEncasers) {
			API.hideItem(new ItemStack(ModBlocks.autoEncaser));
			API.hideItem(new ItemStack(ModBlocks.encasingBench));
		}
		if (!GanysSurface.enableOMC)
			API.hideItem(new ItemStack(ModBlocks.organicMatterCompressor));
		if (!GanysSurface.enableSpawnEggs) {
			API.hideItem(new ItemStack(ModItems.chargedCreeperSpawner));
			API.hideItem(new ItemStack(ModItems.horseSpawner, 1, OreDictionary.WILDCARD_VALUE));
		}
		if (!GanysSurface.enableSlimeBlock)
			API.hideItem(new ItemStack(ModBlocks.slimeBlock));
		if (!GanysSurface.enableAnalisers) {
			API.hideItem(new ItemStack(ModItems.gearalyser));
			API.hideItem(new ItemStack(ModItems.horsalyser));
		}
		if (!GanysSurface.enableIcyPick)
			API.hideItem(new ItemStack(ModItems.icyPickaxe));
		if (!GanysSurface.enableFertilisedSoil)
			API.hideItem(new ItemStack(ModBlocks.fertilizedSoil));
		if (!GanysSurface.enableChestPropellant)
			API.hideItem(new ItemStack(ModBlocks.chestPropellant));
		if (!GanysSurface.enableInkHarvester)
			API.hideItem(new ItemStack(ModBlocks.inkHarvester));
		if (!GanysSurface.enableRainDetector)
			API.hideItem(new ItemStack(ModBlocks.rainDetector));
		if (!GanysSurface.enableCushion)
			API.hideItem(new ItemStack(ModBlocks.cushion));
		if (!GanysSurface.enableLantern)
			API.hideItem(new ItemStack(ModBlocks.lantern));
		if (!GanysSurface.enableBlockOfCharcoal)
			API.hideItem(new ItemStack(ModBlocks.charcoalBlock));
		if (!GanysSurface.enableRot)
			API.hideItem(new ItemStack(ModItems.rot));
		if (!GanysSurface.enableWoodenWrench)
			API.hideItem(new ItemStack(ModItems.woodenWrench));
		if (!GanysSurface.enableVillageFinder)
			API.hideItem(new ItemStack(ModItems.villageFinder));
		if (!GanysSurface.enableCookedEgg)
			API.hideItem(new ItemStack(ModItems.cookedEgg));
		if (!GanysSurface.enableRot && !GanysSurface.enablePoop)
			API.hideItem(new ItemStack(ModItems.rot, 1, OreDictionary.WILDCARD_VALUE));

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
		if (!GanysSurface.enableInvertedDaylightSensor)
			API.hideItem(new ItemStack(ModBlocks.invertedDaylightDetector));

		if (!GanysSurface.enableFences) {
			API.hideItem(new ItemStack(ModBlocks.fenceAcacia));
			API.hideItem(new ItemStack(ModBlocks.fenceBirch));
			API.hideItem(new ItemStack(ModBlocks.fenceDarkOak));
			API.hideItem(new ItemStack(ModBlocks.fenceJungle));
			API.hideItem(new ItemStack(ModBlocks.fenceSpruce));
			API.hideItem(new ItemStack(ModBlocks.fenceOak));

			API.hideItem(new ItemStack(ModBlocks.gateAcacia));
			API.hideItem(new ItemStack(ModBlocks.gateBirch));
			API.hideItem(new ItemStack(ModBlocks.gateDarkOak));
			API.hideItem(new ItemStack(ModBlocks.gateJungle));
			API.hideItem(new ItemStack(ModBlocks.gateSpruce));
		} else
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