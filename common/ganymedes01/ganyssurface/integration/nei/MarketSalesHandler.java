package ganymedes01.ganyssurface.integration.nei;

import ganymedes01.ganyssurface.client.gui.inventory.GuiMarketPublic;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.recipes.MarketSales;
import ganymedes01.ganyssurface.recipes.MarketSales.Sale;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.core.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class MarketSalesHandler extends TemplateRecipeHandler {

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiMarketPublic.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal(Utils.getConainerName(Strings.MARKET_NAME));
	}

	public String getRecipeId() {
		return Reference.MOD_ID + "." + Strings.MARKET_NAME;
	}

	@Override
	public String getGuiTexture() {
		return Utils.getGUITexture(Strings.MARKET_NAME + "_public");
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(38, 28, 6, 6), getRecipeId(), new Object[0]));
	}

	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 51, 65);

		CachedMarketSale sale = (CachedMarketSale) arecipes.get(recipe);
		GuiDraw.fontRenderer.drawString(sale.owner, 55, 5, Utils.getColour(0, 0, 0));

		GuiDraw.fontRenderer.drawString("X: " + sale.x, 55, 15, Utils.getColour(0, 0, 0));
		GuiDraw.fontRenderer.drawString("Y: " + sale.y, 55, 24, Utils.getColour(0, 0, 0));
		GuiDraw.fontRenderer.drawString("Z: " + sale.z, 55, 33, Utils.getColour(0, 0, 0));

		Entity p = Minecraft.getMinecraft().thePlayer;
		GuiDraw.fontRenderer.drawString("D: " + Math.sqrt(Math.pow(sale.x - p.posX, 2) + Math.pow(sale.y - p.posY, 2) + Math.pow(sale.z - p.posZ, 2)), 55, 51, Utils.getColour(0, 0, 0));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getRecipeId()))
			for (Sale sale : MarketSales.getSales())
				arecipes.add(new CachedMarketSale(sale));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (Sale sale : MarketSales.getSales())
			if (Utils.areStacksTheSame(sale.offer, result, false))
				arecipes.add(new CachedMarketSale(sale));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (Sale sale : MarketSales.getSales())
			if (Utils.areStacksTheSame(sale.price, ingredient, false))
				arecipes.add(new CachedMarketSale(sale));
	}

	private class CachedMarketSale extends CachedRecipe {

		private final PositionedStack price, offer;
		public final int x, y, z;
		public final String owner;

		public CachedMarketSale(Sale sale) {
			price = new PositionedStack(sale.price, 34, 7);
			offer = new PositionedStack(sale.offer, 34, 36);

			x = sale.x;
			y = sale.y;
			z = sale.z;
			owner = sale.owner;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> list = new ArrayList<PositionedStack>();
			list.add(price);
			return list;
		}

		@Override
		public PositionedStack getResult() {
			return offer;
		}
	}
}