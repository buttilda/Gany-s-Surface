package ganymedes01.ganyssurface.integration.nei;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemBanner;
import ganymedes01.ganyssurface.lib.EnumColour;
import ganymedes01.ganyssurface.tileentities.TileEntityBanner;
import ganymedes01.ganyssurface.tileentities.TileEntityBanner.EnumBannerPattern;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BannerPatternHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal(Utils.getConainerName("banner"));
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "banner"));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCrafting.class;
	}

	@Override
	public String getGuiTexture() {
		return "textures/gui/container/crafting_table.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (!outputId.equals("banner")) {
			super.loadCraftingRecipes(outputId, results);
			return;
		}

		for (EnumBannerPattern pattern : EnumBannerPattern.values()) {
			if (!pattern.hasValidCrafting())
				continue;
			if (pattern.hasCraftingStack()) {
				for (EnumColour colour : EnumColour.values())
					arecipes.add(new CachedPatternRecipe(pattern, new String[] { "   ", "xy ", " z " }, Arrays.asList('x', pattern.getCraftingStack(), 'y', colour.getOreName(), 'z', new ItemStack(ModBlocks.banner, 1, OreDictionary.WILDCARD_VALUE))));
				arecipes.add(new CachedPatternRecipe(pattern, new String[] { "   ", "xy ", "   " }, Arrays.asList('x', pattern.getCraftingStack(), 'y', new ItemStack(ModBlocks.banner, 1, OreDictionary.WILDCARD_VALUE))));
			} else
				for (EnumColour colour : EnumColour.values()) {
					String[] layers = pattern.getCraftingLayers();
					String[] layersCopy = new String[] { layers[0], layers[1], layers[2] };
					for (int i = 0; i < 3; i++) {
						String newLayer = layersCopy[i].replaceFirst(" ", "x");
						if (!newLayer.equals(layersCopy[i])) {
							layersCopy[i] = newLayer;
							break;
						}
					}
					arecipes.add(new CachedPatternRecipe(pattern, layersCopy, Arrays.asList('#', colour.getOreName(), 'x', new ItemStack(ModBlocks.banner, 1, OreDictionary.WILDCARD_VALUE))));
				}
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result.getItem() == Item.getItemFromBlock(ModBlocks.banner)) {
			NBTTagCompound nbt = ItemBanner.getSubTag(result, "BlockEntityTag", false);
			if (nbt.hasKey("Patterns", 9)) {
				NBTTagList patterns = nbt.getTagList("Patterns", 10);
				for (int i = 0; i < patterns.tagCount(); i++) {
					NBTTagCompound patternNBT = patterns.getCompoundTagAt(i);
					EnumBannerPattern pattern = EnumBannerPattern.getPatternByID(patternNBT.getString("Pattern"));
					if (pattern == null)
						continue;

					ItemStack copy = new ItemStack(ModBlocks.banner, 1, result.getItemDamage());
					copy.setTagCompound(null);
					EnumColour colour = patternNBT.hasKey("Color") ? EnumColour.fromDamage(patternNBT.getInteger("Color")) : null;
					if (!pattern.hasValidCrafting())
						continue;
					if (pattern.hasCraftingStack()) {
						if (colour != null)
							arecipes.add(new CachedPatternRecipe(pattern, new String[] { "   ", "xy ", " z " }, Arrays.asList('x', pattern.getCraftingStack(), 'y', colour.getOreName(), 'z', copy)));
						else
							arecipes.add(new CachedPatternRecipe(pattern, new String[] { "   ", "xy ", "   " }, Arrays.asList('x', pattern.getCraftingStack(), 'y', copy)));
					} else {
						String[] layers = pattern.getCraftingLayers();
						String[] layersCopy = new String[] { layers[0], layers[1], layers[2] };
						for (int j = 0; j < 3; j++) {
							String newLayer = layersCopy[j].replaceFirst(" ", "x");
							if (!newLayer.equals(layersCopy[j])) {
								layersCopy[j] = newLayer;
								break;
							}
						}
						arecipes.add(new CachedPatternRecipe(pattern, layersCopy, Arrays.asList('#', colour == null ? "dye" : colour.getOreName(), 'x', copy)));
					}
				}
			}

		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		EnumColour colour = getEnumColour(ingredient);
		for (EnumBannerPattern pattern : EnumBannerPattern.values()) {
			if (!pattern.hasValidCrafting())
				continue;
			if (pattern.hasCraftingStack()) {
				if (pattern.getCraftingStack().isItemEqual(ingredient)) {
					arecipes.add(new CachedPatternRecipe(pattern, new String[] { "   ", "xy ", " z " }, Arrays.asList('x', pattern.getCraftingStack(), 'y', "dye", 'z', new ItemStack(ModBlocks.banner, 1, OreDictionary.WILDCARD_VALUE))));
					arecipes.add(new CachedPatternRecipe(pattern, new String[] { "   ", "xy ", "   " }, Arrays.asList('x', pattern.getCraftingStack(), 'y', new ItemStack(ModBlocks.banner, 1, OreDictionary.WILDCARD_VALUE))));
				} else if (colour != null)
					arecipes.add(new CachedPatternRecipe(pattern, new String[] { "   ", "xy ", " z " }, Arrays.asList('x', pattern.getCraftingStack(), 'y', colour.getOreName(), 'z', new ItemStack(ModBlocks.banner, 1, OreDictionary.WILDCARD_VALUE))));
			} else if (ingredient.getItem() == Item.getItemFromBlock(ModBlocks.banner) && TileEntityBanner.getPatterns(ingredient) < 6) {
				String[] layers = pattern.getCraftingLayers();
				String[] layersCopy = new String[] { layers[0], layers[1], layers[2] };
				for (int i = 0; i < 3; i++) {
					String newLayer = layersCopy[i].replaceFirst(" ", "x");
					if (!newLayer.equals(layersCopy[i])) {
						layersCopy[i] = newLayer;
						break;
					}
				}
				ItemStack copy = ingredient.copy();
				copy.stackSize = 1;
				arecipes.add(new CachedPatternRecipe(pattern, layersCopy, Arrays.asList('#', "dye", 'x', copy)));
			}
		}
	}

	private EnumColour getEnumColour(ItemStack stack) {
		for (String ore : InventoryUtils.getOreNames(stack))
			for (EnumColour colour : EnumColour.values())
				if (ore.equals(colour.getOreName()))
					return colour;

		return null;
	}

	public class CachedPatternRecipe extends CachedRecipe {

		private final List<PositionedStack> ingredients = new ArrayList<PositionedStack>();
		private final EnumBannerPattern pattern;

		public CachedPatternRecipe(EnumBannerPattern pattern, String[] grid, List<Object> inputs) {
			this.pattern = pattern;

			for (int y = 0; y < 3; y++)
				for (int x = 0; x < 3; x++) {
					char c = grid[y].charAt(x);
					if (c != ' ') {
						Object input = inputs.get(inputs.indexOf(c) + 1);
						if (input instanceof String)
							input = OreDictionary.getOres((String) input);
						PositionedStack stack = new PositionedStack(input, 25 + x * 18, 6 + y * 18);
						stack.setMaxSize(1);
						ingredients.add(stack);
					}
				}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			for (PositionedStack stack : ingredients)
				if (stack.items.length > 1)
					stack.setPermutationToRender(cycleticks / 20 % stack.items.length);
			return ingredients;
		}

		@Override
		public PositionedStack getResult() {
			EnumColour colour = null;
			ItemStack banner = null;
			for (PositionedStack stack : getIngredients()) {
				if (stack.item.getItem() == Item.getItemFromBlock(ModBlocks.banner))
					banner = stack.item.copy();
				if (colour == null)
					colour = getEnumColour(stack.item);
			}
			if (banner == null)
				banner = new ItemStack(ModBlocks.banner);

			NBTTagCompound nbt = ItemBanner.getSubTag(banner, "BlockEntityTag", true);
			NBTTagList nbttaglist;
			if (nbt.hasKey("Patterns", 9))
				nbttaglist = nbt.getTagList("Patterns", 10);
			else {
				nbttaglist = new NBTTagList();
				nbt.setTag("Patterns", nbttaglist);
			}
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			nbttagcompound.setString("Pattern", pattern.getPatternID());
			if (colour != null)
				nbttagcompound.setInteger("Color", colour.getDamage());
			nbttaglist.appendTag(nbttagcompound);
			return new PositionedStack(banner, 119, 24);

		}
	}
}