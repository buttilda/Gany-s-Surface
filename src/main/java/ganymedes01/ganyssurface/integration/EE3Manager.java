package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class EE3Manager extends Integration {

	@Override
	public void init() {
		addEMCValue(new ItemStack(ModItems.colouredRedstone, 1, OreDictionary.WILDCARD_VALUE), 32);
		addEMCValue(new ItemStack(ModBlocks.colouredRedstoneBlock, 1, OreDictionary.WILDCARD_VALUE), 256);
		addEMCValueToBlock(ModBlocks.chocolateCake, 360);
		addEMCValue(new ItemStack(ModItems.poop, 1, 1), 16);
		addEMCValue(new ItemStack(ModItems.poop, 1, 0), 8);
		addEMCValueToItem(ModItems.cookedEgg, 34);
		addEMCValue(new ItemStack(ModItems.pocketCritter, 1, OreDictionary.WILDCARD_VALUE), 64);
		addEMCValueToItem(ModItems.pocketCritter, 64);
		addEMCValueToItem(ModItems.camelliaSeeds, 20);
		addEMCValueToItem(ModItems.teaLeaves, 40);
		addEMCValueToItem(ModItems.roastedSquid, 64);
	}

	private void addEMCValueToBlock(Block block, float value) {
		addEMCValue(new ItemStack(block), value);
	}

	private void addEMCValueToItem(Item item, float value) {
		addEMCValue(new ItemStack(item), value);
	}

	private void addEMCValue(Object obj, float value) {
		String string = "{\"wrappedStack\":{\"className\":\"%s\",\"stackSize\":1,\"wrappedStack\":{%s}},\"emcValue\":{\"OMNI\":0.0,\"CORPOREAL\":%f,\"KINETIC\":0.0,\"TEMPORAL\":0.0,\"ESSENTIA\":0.0,\"AMORPHOUS\":0.0,\"VOID\":0.0}}";

		String stack = null;
		String className = null;
		if (obj instanceof ItemStack) {
			ItemStack s = (ItemStack) obj;
			stack = "\"stackSize\":" + s.stackSize + ",\"itemID\":" + s.itemID + ",\"itemDamage\":" + s.getItemDamage();
			className = "ItemStack";
		} else if (obj instanceof String) {
			stack = "\"oreName\":\"" + (String) obj + "\",\"stackSize\":1";
			className = "OreStack";
		}

		if (stack != null && className != null)
			FMLInterModComms.sendMessage(getModID(), "emc-assign-value-pre", String.format(string, className, stack, value));
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "EE3";
	}
}