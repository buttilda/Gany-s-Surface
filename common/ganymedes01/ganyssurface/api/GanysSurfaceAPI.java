package ganymedes01.ganyssurface.api;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.FMLLog;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class GanysSurfaceAPI {

	// BLOCKS
	/*
	 * Here's a list of the blocks that can/should be retrieved by this method
	 * 
	 * camelliaCrop
	 * rainDetector
	 * blockDetector
	 * dislocator
	 * sensoringDislocator
	 * cubicSensoringDislocator
	 * disguisedTrapDoorOak
	 * disguisedTrapDoorSpruce
	 * disguisedTrapDoorBirch
	 * disguisedTrapDoorJungle
	 * workTable
	 * organicMatterCompressor
	 * cushion
	 * chocolateCake
	 * itemDisplay
	 * chestPropellant
	 * fertilizedSoil
	 * planter
	 * lantern
	 * inkHarvester;
	 * 
	 */
	public static final Block getBlock(String blockName) {
		try {
			Class<?> modBlocks = Class.forName("ganymedes01.ganyssurface.blocks.ModBlocks");
			Field block = modBlocks.getField(blockName);
			return (Block) block.get(null);
		} catch (Exception e) {
			FMLLog.warning("[ganyssurface] Problems retrieving block: " + blockName);
			return null;
		}
	}

	// ITEMS
	/*
	 * Here's a list of the items that can/should be retrieved by this method
	 * 
	 * rot
	 * camelliaSeeds
	 * teaLeaves
	 * teaBag
	 * emptyMug
	 * cupOfTea
	 * mankyCupOfTea
	 * poop
	 * cookedEgg
	 * obsidianHead
	 * woodenWrench
	 * batNet
	 * pocketBat
	 * batStew
	 * chocolateBar
	 * horsalyser
	 * horseSpawner
	 * chargedCreeperSpawner
	 * 
	 * woodenHelmet
	 * woodenChestplate
	 * woodenLeggings
	 * woodenBoots
	 * 
	 */
	public static final Item getItem(String itemName) {
		try {
			Class<?> modBlocks = Class.forName("ganymedes01.ganyssurface.items.ModItems");
			Field item = modBlocks.getField(itemName);
			return (Item) item.get(null);
		} catch (Exception e) {
			FMLLog.warning("[ganyssurface] Problems retrieving item: " + itemName);
			return null;
		}
	}
}