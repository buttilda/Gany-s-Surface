package ganymedes01.ganyssurface.api;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class GanysSurfaceAPI {

	/**
	 * Sets a custom yield on the Organic Matter Fabricator (OMF) for a determined item/block.
	 * 
	 * Pass a yield of -1 to make the item not acceptable on the OMF.
	 * 
	 * Most items have a yield of 2. The amount needed for the machine to start to work is 144.
	 * 
	 * This is metadata sensitive.
	 * 
	 * @param matter
	 *            : ItemStack of item/block to which the yield will be registered
	 * @param burnTime
	 *            : Yield for specified matter
	 */
	public static void addYieldForOrganicMatter(ItemStack matter, int yield) {
		if (matter != null) {
			NBTTagCompound data = new NBTTagCompound();

			data.setInteger("yield", yield);

			NBTTagCompound tagCompound = new NBTTagCompound();
			matter.writeToNBT(tagCompound);
			data.setTag("matter", tagCompound);

			FMLInterModComms.sendMessage("ganyssurface", "registerOrganicMatter", data);
		}
	}

	// BLOCKS
	/*
	 * Here's a list of the blocks that can/should be retrieved by this method camelliaCrop rainDetector blockDetector dislocator sensoringDislocator cubicSensoringDislocator disguisedTrapDoorOak disguisedTrapDoorSpruce disguisedTrapDoorBirch disguisedTrapDoorJungle workTable organicMatterCompressor cushion chocolateCake itemDisplay chestPropellant fertilizedSoil planter lantern inkHarvester;
	 */
	public static Block getBlock(String blockName) {
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
	 * Here's a list of the items that can/should be retrieved by this method rot camelliaSeeds teaLeaves teaBag emptyMug cupOfTea mankyCupOfTea poop cookedEgg obsidianHead woodenWrench batNet pocketBat batStew chocolateBar horsalyser horseSpawner chargedCreeperSpawner woodenHelmet woodenChestplate woodenLeggings woodenBoots
	 */
	public static Item getItem(String itemName) {
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