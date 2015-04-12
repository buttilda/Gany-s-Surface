package ganymedes01.ganyssurface.integration.nei;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.client.gui.inventory.GuiOrganicMatterCompressor;
import ganymedes01.ganyssurface.core.utils.UnsizedStack;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.recipes.OrganicMatterRegistry;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class OMCYieldHandler extends TemplateRecipeHandler {

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiOrganicMatterCompressor.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal(Utils.getConainerName(Strings.ORGANIC_MATTER_COMPRESSOR_NAME));
	}

	public String getRecipeId() {
		return Reference.MOD_ID + "." + Strings.ORGANIC_MATTER_COMPRESSOR_NAME;
	}

	@Override
	public String getGuiTexture() {
		return Utils.getGUITexture(Strings.ORGANIC_MATTER_COMPRESSOR_NAME);
	}

	@Override
	public String getOverlayIdentifier() {
		return Strings.ORGANIC_MATTER_COMPRESSOR_NAME;
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	@Override
	public void drawBackground(int index) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);

		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(-2, -5, 5, 11, 120, 65);
		GuiDraw.drawTexturedModalRect(88, 20, 177, 14, cycleticks % 25, 15);

		CachedRecipe recipe = arecipes.get(index);
		if (recipe instanceof CachedMatterYield) {
			CachedMatterYield yieldRecipe = (CachedMatterYield) recipe;
			GuiDraw.fontRenderer.drawString(String.format("%,3d", yieldRecipe.getYield()) + " m", 110, 24, Utils.getColour(0, 0, 0));
			GuiDraw.fontRenderer.drawString("144 m = ", 90, 46, Utils.getColour(0, 0, 0));
		}
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(90, 26, 25, 16), getRecipeId(), new Object[0]));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		LinkedHashMap<UnsizedStack, Integer> map = new LinkedHashMap<UnsizedStack, Integer>();
		if (outputId.equals(getRecipeId())) {
			for (ItemStack stack : ItemList.items) {
				int yield = OrganicMatterRegistry.getOrganicYield(stack);
				if (yield > 0)
					map.put(new UnsizedStack(stack), yield);
			}
			LinkedHashMap<UnsizedStack, Integer> sortedMap = sortHashMapByValues(map);
			for (Entry<UnsizedStack, Integer> entry : sortedMap.entrySet())
				arecipes.add(new CachedMatterYield(entry.getKey().getStack(), entry.getValue()));
		} else
			super.loadCraftingRecipes(outputId, results);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T, V> LinkedHashMap<T, V> sortHashMapByValues(LinkedHashMap<T, V> map) {
		List mapKeys = new ArrayList(map.keySet());
		List mapValues = new ArrayList(map.values());

		try {
			Collections.sort(mapValues);
			Collections.reverse(mapValues);
			Collections.sort(mapKeys);
			Collections.reverse(mapKeys);
		} catch (IllegalArgumentException e) {
			return map;
		}

		LinkedHashMap sortedMap = new LinkedHashMap();

		Iterator valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = map.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					map.remove(key);
					mapKeys.remove(key);
					sortedMap.put(key, val);
					break;
				}
			}
		}
		return sortedMap;
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		int yield = OrganicMatterRegistry.getOrganicYield(ingredient);
		if (yield > 0)
			arecipes.add(new CachedMatterYield(ingredient.copy().splitStack(1), yield));
	}

	private class CachedMatterYield extends CachedRecipe {

		private final PositionedStack material;
		private final int yield;

		public CachedMatterYield(ItemStack stack, int yield) {
			material = new PositionedStack(stack, 45, 20);
			this.yield = yield;
		}

		public int getYield() {
			return yield;
		}

		@Override
		public PositionedStack getIngredient() {
			return material;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(new ItemStack(Blocks.coal_block), 130, 40);
		}
	}
}