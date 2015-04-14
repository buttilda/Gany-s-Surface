package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ThermalExpansionManager extends Integration {

	@Override
	public void init() {
		ItemStack material = GameRegistry.findItemStack(getModID(), "material", 1);
		ItemStack fertilizer = material.copy();
		fertilizer.setItemDamage(516);
		ItemStack fertilizerRich = material.copy();
		fertilizerRich.setItemDamage(517);

		if (GanysSurface.enableTea) {
			addInsolatorRecipe(7200, new ItemStack(ModItems.camelliaSeeds), fertilizer, new ItemStack(ModItems.teaLeaves), new ItemStack(ModItems.camelliaSeeds), 150);
			addInsolatorRecipe(9600, new ItemStack(ModItems.camelliaSeeds), fertilizerRich, new ItemStack(ModItems.teaLeaves, 3), new ItemStack(ModItems.camelliaSeeds), 150);
		}
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "ThermalExpansion";
	}

	private void addInsolatorRecipe(int energy, ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, int chance) {
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("energy", energy);
		data.setTag("primaryInput", input1.writeToNBT(new NBTTagCompound()));
		data.setTag("secondaryInput", input2.writeToNBT(new NBTTagCompound()));
		data.setTag("primaryOutput", output1.writeToNBT(new NBTTagCompound()));
		data.setTag("secondaryOutput", output2.writeToNBT(new NBTTagCompound()));
		data.setInteger("secondaryChance", chance);

		FMLInterModComms.sendMessage(getModID(), "InsolatorRecipe", data);
	}
}