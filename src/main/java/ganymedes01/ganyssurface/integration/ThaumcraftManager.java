package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ThaumcraftManager extends Integration {

	@Override
	public void init() {
		if (GanysSurface.enableTea) {
			addAspectsToItem(ModItems.camelliaSeeds, new Aspect[] { Aspect.PLANT }, new int[] { 1 });
			addAspectsToItem(ModItems.teaLeaves, new Aspect[] { Aspect.CROP }, new int[] { 1 });
			addAspectsToItem(ModItems.mankyCupOfTea, new Aspect[] { Aspect.POISON, Aspect.DEATH }, new int[] { 2, 1 });
			addAspectsToItem(ModItems.teaBag, new Aspect[] { Aspect.CROP, Aspect.CLOTH }, new int[] { 1, 1 });
			addAspectsToItem(ModItems.cupOfTea, new Aspect[] { Aspect.CROP, Aspect.HUNGER, Aspect.WATER, Aspect.HEAL, Aspect.EARTH }, new int[] { 1, 2, 2, 2, 3 });
			addAspectsToItem(ModItems.emptyMug, new Aspect[] { Aspect.EARTH }, new int[] { 3 });
		}
		if (GanysSurface.enablePoop) {
			addAspectsToItem(ModItems.poop, new Aspect[] { Aspect.SLIME }, new int[] { 2 });
			addAspectsToItem(ModItems.poop, 1, new Aspect[] { Aspect.SLIME }, new int[] { 2 });
		}
		if (GanysSurface.enablePocketCritters) {
			addAspectsToItem(ModItems.pocketCritter, new Aspect[] { Aspect.TRAP, Aspect.BEAST }, new int[] { 3, 1 });
			addAspectsToItem(ModItems.pocketCritter, 1, new Aspect[] { Aspect.TRAP, Aspect.BEAST }, new int[] { 3, 1 });
			addAspectsToItem(ModItems.roastedSquid, new Aspect[] { Aspect.HUNGER, Aspect.BEAST }, new int[] { 3, 1 });
			addAspectsToItem(ModItems.batStew, new Aspect[] { Aspect.HUNGER, Aspect.BEAST, Aspect.DARKNESS }, new int[] { 2, 1, 1 });
			addAspectsToItem(ModItems.batNet, new Aspect[] { Aspect.TREE, Aspect.CLOTH }, new int[] { 2, 1 });
		}
		if (GanysSurface.enableCookedEgg)
			addAspectsToItem(ModItems.cookedEgg, new Aspect[] { Aspect.HUNGER, Aspect.LIFE, Aspect.BEAST }, new int[] { 1, 1, 1 });
		if (GanysSurface.enableChocolate)
			addAspectsToItem(ModItems.chocolateBar, new Aspect[] { Aspect.HUNGER, Aspect.SENSES }, new int[] { 2, 1 });
		if (GanysSurface.enablePineCones) {
			addAspectsToItem(ModItems.pineCone, new Aspect[] { Aspect.CROP, Aspect.PLANT }, new int[] { 2, 1 });
			addAspectsToItem(ModItems.pineNuts, new Aspect[] { Aspect.CROP, Aspect.HUNGER }, new int[] { 2, 1 });
		}
		if (GanysSurface.enableMutton) {
			addAspectsToItem(ModItems.cookedMutton, new Aspect[] { Aspect.FLESH, Aspect.HUNGER, Aspect.CRAFT }, new int[] { 2, 2, 1 });
			addAspectsToItem(ModItems.rawMutton, new Aspect[] { Aspect.FLESH, Aspect.LIFE, Aspect.BEAST }, new int[] { 2, 1, 1 });
		}
		if (GanysSurface.enableSpawnEggs) {
			addAspectsToItem(ModItems.horseSpawner, new Aspect[] { Aspect.SOUL, Aspect.TRAP }, new int[] { 1, 1 });
			addAspectsToItem(ModItems.chargedCreeperSpawner, new Aspect[] { Aspect.SOUL, Aspect.TRAP }, new int[] { 1, 1 });
		}
		if (GanysSurface.enableChests)
			for (Block chest : ModBlocks.chests)
				addAspectsToItem(chest, new Aspect[] { Aspect.TREE, Aspect.VOID }, new int[] { 6, 4 });
		if (GanysSurface.enableDoors) {
			for (Item door : ModItems.doors)
				addAspectsToItem(door, new Aspect[] { Aspect.TREE, Aspect.MOTION, Aspect.MECHANISM }, new int[] { 1, 1, 1 });
			for (Block door : ModBlocks.doors)
				addAspectsToItem(door, new Aspect[] { Aspect.TREE, Aspect.MOTION, Aspect.MECHANISM }, new int[] { 1, 1, 1 });
		}
		if (GanysSurface.enableDisguisedTrapdoors)
			for (Block trapdoor : ModBlocks.disguisedTrapdoors)
				addAspectsToItem(trapdoor, new Aspect[] { Aspect.TREE, Aspect.MOTION }, new int[] { 2, 1 });
		if (GanysSurface.enableIronTrapdoor)
			addAspectsToItem(ModBlocks.ironTrapdoor, new Aspect[] { Aspect.METAL, Aspect.MOTION }, new int[] { 4, 1 });
		if (GanysSurface.enableFences)
			for (Block gate : ModBlocks.gates)
				addAspectsToItem(gate, new Aspect[] { Aspect.TREE, Aspect.TRAVEL, Aspect.MECHANISM }, new int[] { 4, 1, 1 });
		if (GanysSurface.enable18Stones)
			for (int i = 1; i <= 6; i++)
				addAspectsToItem(ModBlocks.newStones, i, new Aspect[] { Aspect.EARTH }, new int[] { 2 });
		if (GanysSurface.enableBasalt) {
			addAspectsToItem(ModBlocks.basalt, new Aspect[] { Aspect.EARTH }, new int[] { 2 });
			addAspectsToItem(ModBlocks.basalt, 1, new Aspect[] { Aspect.EARTH }, new int[] { 2 });
		}
		if (GanysSurface.enableRedSandstone) {
			addAspectsToItem(ModBlocks.redSandstone, 1, new Aspect[] { Aspect.ENTROPY, Aspect.EARTH }, new int[] { 3, 3 });
			addAspectsToItem(ModBlocks.redSandstone, 1, new Aspect[] { Aspect.ENTROPY, Aspect.EARTH, Aspect.MAGIC }, new int[] { 3, 2, 1 });
			addAspectsToItem(ModBlocks.redSandstone, 2, new Aspect[] { Aspect.ENTROPY, Aspect.EARTH, Aspect.ORDER }, new int[] { 3, 2, 1 });
		}
		if (GanysSurface.enableSlimeBlock)
			addAspectsToItem(ModBlocks.slimeBlock, new Aspect[] { Aspect.SLIME }, new int[] { 12 });
		if (GanysSurface.enableSlowRail)
			addAspectsToItem(ModBlocks.slowRail, new Aspect[] { Aspect.METAL, Aspect.ENERGY, Aspect.MECHANISM }, new int[] { 2, 1, 1 });
		if (GanysSurface.enablePrismarineStuff) {
			addAspectsToItem(ModItems.prismarineItems, 0, new Aspect[] { Aspect.CRYSTAL, Aspect.WATER }, new int[] { 1, 1 });
			addAspectsToItem(ModItems.prismarineItems, 1, new Aspect[] { Aspect.CRYSTAL, Aspect.WATER }, new int[] { 1, 1 });
			addAspectsToItem(ModBlocks.prismarineBlocks, 0, new Aspect[] { Aspect.CRYSTAL, Aspect.WATER }, new int[] { 2, 2 });
			addAspectsToItem(ModBlocks.prismarineBlocks, 1, new Aspect[] { Aspect.CRYSTAL, Aspect.WATER }, new int[] { 6, 6 });
			addAspectsToItem(ModBlocks.prismarineBlocks, 2, new Aspect[] { Aspect.CRYSTAL, Aspect.WATER, Aspect.SENSES }, new int[] { 5, 5, 1 });
			addAspectsToItem(ModBlocks.seaLantern, new Aspect[] { Aspect.CRYSTAL, Aspect.WATER, Aspect.LIGHT }, new int[] { 6, 6, 1 });
		}
		if (GanysSurface.enableLantern)
			addAspectsToItem(ModBlocks.lantern, new Aspect[] { Aspect.LIGHT, Aspect.CRYSTAL }, new int[] { 1, 1 });
		if (GanysSurface.enableSilkTouchingMushrooms) {
			addAspectsToItem(ModBlocks.brown_mushroom_block, new Aspect[] { Aspect.PLANT, Aspect.DARKNESS, Aspect.EARTH }, new int[] { 2, 1, 1 });
			addAspectsToItem(ModBlocks.red_mushroom_block, new Aspect[] { Aspect.PLANT, Aspect.DARKNESS, Aspect.FIRE }, new int[] { 2, 1, 1 });
		}
		if (GanysSurface.enableItemDisplay)
			for (int i = 0; i < 16; i++)
				addAspectsToItem(ModBlocks.itemDisplay, i, new Aspect[] { Aspect.CRYSTAL, Aspect.CLOTH }, new int[] { 4, 1 });

		if (GanysSurface.enableWoodenTrapdoors)
			for (Block trapdoor : ModBlocks.trapdoors)
				addAspectsToItem(trapdoor, new Aspect[] { Aspect.TREE, Aspect.MOTION }, new int[] { 2, 1 });
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getModID() {
		return "Thaumcraft";
	}

	private void addAspectsToItem(Block block, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(new ItemStack(block), aspects, amounts);
	}

	private void addAspectsToItem(Block block, int meta, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(new ItemStack(block, 1, meta), aspects, amounts);
	}

	private void addAspectsToItem(Item item, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(new ItemStack(item), aspects, amounts);
	}

	private void addAspectsToItem(Item items, int meta, Aspect[] aspects, int[] amounts) {
		addAspectsToItem(new ItemStack(items, 1, meta), aspects, amounts);
	}

	private void addAspectsToItem(ItemStack stack, Aspect[] aspects, int[] amounts) {
		AspectList list = new AspectList();
		for (int i = 0; i < aspects.length; i++)
			list.add(aspects[i], amounts[i]);

		ThaumcraftApi.registerObjectTag(stack, list);
	}
}