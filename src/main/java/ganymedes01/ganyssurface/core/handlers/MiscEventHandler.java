package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class MiscEventHandler {

	@SubscribeEvent
	public void harvestEvent(BlockEvent.HarvestDropsEvent event) {
		if (event.block == Blocks.leaves && (event.blockMetadata & 3) == 1)
			if (event.world.rand.nextInt(20) == 0)
				event.drops.add(new ItemStack(ModItems.pineCone));
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.entityPlayer != null) {
			World world = event.entityPlayer.worldObj;
			if (event.action == Action.RIGHT_CLICK_BLOCK)
				if (world.getBlock(event.x, event.y, event.z) == Blocks.daylight_detector) {
					world.setBlock(event.x, event.y, event.z, ModBlocks.invertedDaylightDetector);
					event.entityPlayer.swingItem();
				} else if (world.getBlock(event.x, event.y, event.z) == ModBlocks.invertedDaylightDetector) {
					world.setBlock(event.x, event.y, event.z, Blocks.daylight_detector);
					event.entityPlayer.swingItem();
				}
		}
	}
}