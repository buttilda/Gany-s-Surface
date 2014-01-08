package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.pahimar.ee3.addon.AddonHandler;
import com.pahimar.ee3.api.OreStack;
import com.pahimar.ee3.emc.EmcValue;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class EE3Manager extends Integration {

	@Override
	public void init() {
		addEMCValueToStack(new ItemStack(ModItems.colouredRedstone, 1, OreDictionary.WILDCARD_VALUE), 32);
		addPostEMCValueToStack(new ItemStack(ModBlocks.colouredRedstoneBlock, 1, OreDictionary.WILDCARD_VALUE), 256);
		addEMCValueToBlock(ModBlocks.chocolateCake, 360);
		addEMCValueToStack(new ItemStack(ModItems.poop, 1, 1), 16);
		addEMCValueToStack(new ItemStack(ModItems.poop, 1, 0), 8);
		addEMCValueToItem(ModItems.cookedEgg, 34);
		addEMCValueToItem(ModItems.pocketBat, 64);
		addEMCValueToItem(ModItems.camelliaSeeds, 20);
		addEMCValueToItem(ModItems.teaLeaves, 40);
	}

	private void addEMCValueToBlock(Block block, float value) {
		addEMCValueToStack(new ItemStack(block), value);
	}

	private void addEMCValueToItem(Item item, float value) {
		addEMCValueToStack(new ItemStack(item), value);
	}

	private void addEMCValueToStack(ItemStack stack, float value) {
		AddonHandler.sendPreValueAssignment(stack, new EmcValue(value));
	}

	private void addEMCValueToOre(String ore, float value) {
		AddonHandler.sendPreValueAssignment(new OreStack(ore), new EmcValue(value));
	}

	private void addPostEMCValueToStack(ItemStack stack, float value) {
		AddonHandler.sendPostValueAssignment(stack, new EmcValue(value));
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "EE3";
	}
}