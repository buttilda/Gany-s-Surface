package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;

public class FishingHandler {

	public static void init() {
		if (GanysSurface.enablePrismarineStuff) {
			addTreasure(new ItemStack(ModItems.prismarineItems, 1, 0), 1);
			addTreasure(new ItemStack(ModItems.prismarineItems, 1, 1), 1);
		}

		addJunk(new ItemStack(ModItems.poop), 8);

		addFish(new ItemStack(ModItems.pocketCritter, 1, 1), 30);
	}

	public static void addFish(ItemStack stack, int weight) {
		addToList("field_146036_f", stack, weight);
	}

	public static void addTreasure(ItemStack stack, int weight) {
		addToList("field_146041_e", stack, weight);
	}

	public static void addJunk(ItemStack stack, int weight) {
		addToList("field_146039_d", stack, weight);
	}

	private static void addToList(String list, ItemStack stack, int weight) {
		getList(list).add(new WeightedRandomFishable(stack, weight));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<WeightedRandomFishable> getList(String name) {
		try {
			Field field = EntityFishHook.class.getDeclaredField(name);
			field.setAccessible(true);
			return (List) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}