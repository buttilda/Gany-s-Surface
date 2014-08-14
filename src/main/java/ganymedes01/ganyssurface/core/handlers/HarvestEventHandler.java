package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class HarvestEventHandler {

	@SubscribeEvent
	public void harvestEvent(BlockEvent.HarvestDropsEvent event) {
		if (event.block == Blocks.leaves && (event.blockMetadata & 3) == 1)
			if (event.world.rand.nextInt(20) == 0)
				event.drops.add(new ItemStack(ModItems.pineCone));
	}
}