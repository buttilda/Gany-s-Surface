package ganymedes01.ganyssurface.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class MarketSalesHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal(Utils.getConainerName(Strings.MARKET));
	}

	public String getRecipeId() {
		return Reference.MOD_ID + "." + Strings.MARKET;
	}

	@Override
	public String getGuiTexture() {
		return Utils.getGUITexture(Strings.MARKET);
	}

	@Override
	public String getOverlayIdentifier() {
		return Strings.MARKET;
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	@Override
	public void drawBackground(int index) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);

		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(2, 2, 5, 11, 160, 40);

		CachedRecipe recipe = arecipes.get(index);
		if (recipe instanceof CachedMarketSale) {
			CachedMarketSale marketSale = (CachedMarketSale) recipe;
			GuiDraw.fontRenderer.drawString(StatCollector.translateToLocalFormatted(Utils.getString("isselling"), marketSale.market.getOwner()), 4, 4, Utils.getColour(0, 0, 0));
			GuiDraw.fontRenderer.drawString(StatCollector.translateToLocal(Utils.getString("for")), 32, 23, Utils.getColour(0, 0, 0));
			GuiDraw.fontRenderer.drawString(StatCollector.translateToLocal(Utils.getString("at")) + marketSale.market.xCoord + ", " + marketSale.market.yCoord + ", " + marketSale.market.zCoord, 4, 44, Utils.getColour(0, 0, 0));
			GuiDraw.fontRenderer.drawString(StatCollector.translateToLocalFormatted(Utils.getString("inthe"), marketSale.market.getWorldObj().provider.getDimensionName()), 4, 54, Utils.getColour(0, 0, 0));
		}
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(32, 20, 16, 16), getRecipeId(), new Object[0]));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getRecipeId()))
			for (TileEntityMarket market : TileEntityMarket.markets.keySet()) {
				if (market.getProduct() != null)
					arecipes.add(new CachedMarketSale(market));
			}
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (TileEntityMarket market : TileEntityMarket.markets.keySet())
			if (market.isItemForSale(result))
				arecipes.add(new CachedMarketSale(market));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (TileEntityMarket market : TileEntityMarket.markets.keySet())
			if (market.isItemPayment(ingredient))
				arecipes.add(new CachedMarketSale(market));
	}

	private class CachedMarketSale extends CachedRecipe {

		private final TileEntityMarket market;

		public CachedMarketSale(TileEntityMarket market) {
			this.market = market;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> price = new ArrayList<PositionedStack>();
			ItemStack[] marketPrice = market.getPrice();
			for (int i = 0; i < marketPrice.length; i++)
				if (marketPrice[i] != null)
					price.add(new PositionedStack(marketPrice[i], 52 + i * 18, 20));
			return price;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(market.getProduct(), 9, 20);
		}
	}
}