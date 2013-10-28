package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BonemealHandler {

	@ForgeSubscribe
	public void onLivingUpdate(BonemealEvent event) {
		event.world.notifyBlocksOfNeighborChange(event.X, event.Y, event.Z, ModBlocks.blockDetector.blockID);
	}
}