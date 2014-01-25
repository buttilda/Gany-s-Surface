package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		addPostEMCValue(new ItemStack(ModBlocks.colouredRedstoneBlock, 1, OreDictionary.WILDCARD_VALUE), 256);
		addEMCValueToBlock(ModBlocks.chocolateCake, 360);
		addEMCValue(new ItemStack(ModItems.poop, 1, 1), 16);
		addEMCValue(new ItemStack(ModItems.poop, 1, 0), 8);
		addEMCValueToItem(ModItems.cookedEgg, 34);
		addEMCValueToItem(ModItems.pocketBat, 64);
		addEMCValueToItem(ModItems.camelliaSeeds, 20);
		addEMCValueToItem(ModItems.teaLeaves, 40);
	}

	private void addEMCValueToBlock(Block block, float value) {
		addEMCValue(new ItemStack(block), value);
	}

	private void addEMCValueToItem(Item item, float value) {
		addEMCValue(new ItemStack(item), value);
	}

	private void addEMCValue(Object obj, float value) {
		NBTTagCompound data = new NBTTagCompound();

		data.setFloat("emcValue", value);

		if (obj instanceof ItemStack) {
			NBTTagCompound stackCompound = new NBTTagCompound();
			((ItemStack) obj).writeToNBT(stackCompound);
			data.setCompoundTag("itemStack", stackCompound);
		} else if (obj instanceof String)
			data.setString("oreName", (String) obj);

		FMLInterModComms.sendMessage(getModID(), "emc-assign-value-pre", data);
	}

	private void addPostEMCValue(Object obj, float value) {
		NBTTagCompound data = new NBTTagCompound();

		data.setFloat("emcValue", value);

		if (obj instanceof ItemStack) {
			NBTTagCompound stackCompound = new NBTTagCompound();
			((ItemStack) obj).writeToNBT(stackCompound);
			data.setCompoundTag("itemStack", stackCompound);
		} else if (obj instanceof String)
			data.setString("oreName", (String) obj);

		FMLInterModComms.sendMessage(getModID(), "emc-assign-value-post", data);
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "EE3";
	}
}