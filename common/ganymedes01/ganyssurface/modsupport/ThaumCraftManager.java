package ganymedes01.ganyssurface.modsupport;

import ganymedes01.ganyssurface.items.ModItems;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ThaumCraftManager {

	public static void init() {
		addAspectsToItem(ModItems.rot.itemID, 1, new Aspect[] { Aspect.SENSES, Aspect.SLIME }, new int[] { 1, 1 });
		addAspectsToItem(ModItems.camelliaSeeds.itemID, new Aspect[] { Aspect.SEED }, new int[] { 1 });
		addAspectsToItem(ModItems.teaLeaves.itemID, new Aspect[] { Aspect.PLANT }, new int[] { 1 });
		addAspectsToItem(ModItems.poop.itemID, new Aspect[] { Aspect.SLIME }, new int[] { 2 });
		addAspectsToItem(ModItems.cookedEgg.itemID, new Aspect[] { Aspect.SEED, Aspect.LIFE, Aspect.BEAST }, new int[] { 1, 1, 1 });
		addAspectsToItem(ModItems.pocketBat.itemID, new Aspect[] { Aspect.TRAP, Aspect.BEAST }, new int[] { 3, 1 });
		addAspectsToItem(ModItems.mankyCupOfTea.itemID, new Aspect[] { Aspect.POISON, Aspect.DEATH }, new int[] { 2, 1 });
	}

	private static void addAspectsToItem(int id, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(id, -1, aspects, amounts);
	}

	private static void addAspectsToItem(int id, int meta, Aspect[] aspects, int[] amounts) {
		AspectList list = new AspectList();
		for (int i = 0; i < aspects.length; i++)
			list.add(aspects[i], amounts[i]);

		ThaumcraftApi.registerObjectTag(id, meta, list);
	}
}