package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.tileentities.TileEntityMarket;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class MarketSales {

	private static ArrayList<TileEntityMarket> markets = new ArrayList<TileEntityMarket>();

	public static void addMarket(TileEntityMarket market) {
		for (TileEntityMarket aMarket : markets)
			if (aMarket.xCoord == market.xCoord && aMarket.yCoord == market.yCoord && aMarket.zCoord == market.zCoord)
				return;
		System.out.println("MARKET ADDED");
		markets.add(market);
	}

	public static void removeMarket(TileEntityMarket market) {
		for (int i = 0; i < markets.size(); i++)
			if (markets.get(i).xCoord == market.xCoord && markets.get(i).yCoord == market.yCoord && markets.get(i).zCoord == market.zCoord)
				markets.remove(i);
	}

	public static ArrayList<Sale> getSales() {
		ArrayList<Sale> sales = new ArrayList<Sale>();
		for (TileEntityMarket market : markets) {
			Sale sale1 = getSaleONEFromMarket(market);
			if (Sale.isValidSale(sale1))
				sales.add(sale1);
			Sale sale2 = getSaleTWOFromMarket(market);
			if (Sale.isValidSale(sale2))
				sales.add(sale2);
		}

		return sales;
	}

	private static Sale getSaleONEFromMarket(TileEntityMarket market) {
		return new Sale(market.getStackInSlot(TileEntityMarket.PRICE_ONE), market.getStackInSlot(TileEntityMarket.OFFER_ONE), market.xCoord, market.yCoord, market.zCoord, market.worldObj.provider.dimensionId, market.getOwner());
	}

	private static Sale getSaleTWOFromMarket(TileEntityMarket market) {
		return new Sale(market.getStackInSlot(TileEntityMarket.PRICE_TWO), market.getStackInSlot(TileEntityMarket.OFFER_TWO), market.xCoord, market.yCoord, market.zCoord, market.worldObj.provider.dimensionId, market.getOwner());
	}

	public static class Sale {

		public final ItemStack price;
		public final ItemStack offer;
		public final int x, y, z, dim;
		public final String owner;

		public Sale(ItemStack price, ItemStack offer, int x, int y, int z, int dim, String owner) {
			this.price = price;
			this.offer = offer;
			this.x = x;
			this.y = y;
			this.z = z;
			this.dim = dim;
			this.owner = owner;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Sale))
				return false;

			Sale sale = (Sale) obj;
			return sale.x == x && sale.y == y && sale.z == z && ItemStack.areItemStacksEqual(price, sale.price) && ItemStack.areItemStacksEqual(offer, sale.offer) && sale.owner.equalsIgnoreCase(owner);
		}

		public static boolean isValidSale(Sale sale) {
			return sale.price != null && sale.offer != null;
		}
	}
}