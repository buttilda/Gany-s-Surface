package ganymedes01.ganyssurface.inventory.slots;

import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class MarketSlot extends Slot {

	private final TileEntityMarket market;

	public MarketSlot(TileEntityMarket market, int slot, int posX, int posY) {
		super(market, slot, posX, posY);
		this.market = market;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}

	public boolean isOwner(EntityPlayer player) {
		return player.getCommandSenderName().equals(market.getOwner());
	}
}