package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraftforge.common.FishingHooks;

public class FishingHandler {

	public static void init() {
		if (GanysSurface.enablePrismarineStuff) {
			addTreasure(new ItemStack(ModItems.prismarineItems, 1, 0), 1);
			addTreasure(new ItemStack(ModItems.prismarineItems, 1, 1), 1);
		}

		addJunk(new ItemStack(ModItems.poop), 8);
		addFish(new ItemStack(ModItems.pocketCritter, 1, 1), 30);
		addTreasure(new ItemStack(ModItems.icyPickaxe, 1, 1), 1);
	}

	public static void addFish(ItemStack stack, int weight) {
		FishingHooks.addFish(new WeightedRandomFishable(stack, weight));
	}

	public static void addTreasure(ItemStack stack, int weight) {
		FishingHooks.addTreasure(new WeightedRandomFishable(stack, weight));
	}

	public static void addJunk(ItemStack stack, int weight) {
		FishingHooks.addJunk(new WeightedRandomFishable(stack, weight));
	}
}