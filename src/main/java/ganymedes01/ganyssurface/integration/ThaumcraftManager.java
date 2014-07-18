package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ThaumcraftManager extends Integration {

	@Override
	public void init() {
		FMLInterModComms.sendMessage("Thaumcraft", "harvestStandardCrop", new ItemStack(ModBlocks.poop, 1, OreDictionary.WILDCARD_VALUE));

		addAspectsToItem(ModItems.rot, new Aspect[] { Aspect.SENSES, Aspect.SLIME }, new int[] { 1, 1 });
		addAspectsToItem(ModItems.camelliaSeeds, new Aspect[] { Aspect.PLANT }, new int[] { 1 });
		addAspectsToItem(ModItems.teaLeaves, new Aspect[] { Aspect.PLANT }, new int[] { 1 });
		addAspectsToItem(ModItems.poop, new Aspect[] { Aspect.SLIME }, new int[] { 2 });
		addAspectsToItem(ModItems.cookedEgg, new Aspect[] { Aspect.PLANT, Aspect.LIFE, Aspect.BEAST }, new int[] { 1, 1, 1 });
		addAspectsToItem(ModItems.pocketCritter, new Aspect[] { Aspect.TRAP, Aspect.BEAST }, new int[] { 3, 1 });
		addAspectsToItem(new ItemStack(ModItems.pocketCritter, 1, 1), new Aspect[] { Aspect.TRAP, Aspect.BEAST }, new int[] { 3, 1 });
		addAspectsToItem(ModItems.mankyCupOfTea, new Aspect[] { Aspect.POISON, Aspect.DEATH }, new int[] { 2, 1 });
		addAspectsToItem(ModItems.horseSpawner, new Aspect[] { Aspect.SOUL, Aspect.TRAP }, new int[] { 1, 1 });
		addAspectsToItem(ModItems.chargedCreeperSpawner, new Aspect[] { Aspect.SOUL, Aspect.TRAP }, new int[] { 1, 1 });
		addAspectsToItem(ModItems.colouredRedstone, new Aspect[] { Aspect.ENERGY, Aspect.MECHANISM }, new int[] { 1, 1 });
		addAspectsToItem(ModItems.roastedSquid, new Aspect[] { Aspect.HUNGER, Aspect.BEAST }, new int[] { 3, 1 });
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "Thaumcraft";
	}

	private void addAspectsToItem(Item id, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(new ItemStack(id), aspects, amounts);
	}

	private void addAspectsToItem(ItemStack stack, Aspect[] aspects, int[] amounts) {
		AspectList list = new AspectList();
		for (int i = 0; i < aspects.length; i++)
			list.add(aspects[i], amounts[i]);

		ThaumcraftApi.registerObjectTag(stack, list);
	}
}