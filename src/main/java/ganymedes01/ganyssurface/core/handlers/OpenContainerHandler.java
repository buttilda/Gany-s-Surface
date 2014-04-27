package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ModBlocks;

import java.util.ArrayList;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerHopper;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class OpenContainerHandler {

	private static ArrayList<Class<?>> containerList = new ArrayList<Class<?>>();

	static {
		containerList.add(ContainerFurnace.class);
		containerList.add(ContainerChest.class);
		containerList.add(ContainerBrewingStand.class);
		containerList.add(ContainerHopper.class);
		containerList.add(ContainerDispenser.class);
	}

	@ForgeSubscribe
	public void containerEvent(PlayerOpenContainerEvent event) {
		if (GanysSurface.forceAllContainersOpen)
			event.setResult(Result.ALLOW);
		else {
			Container openContainer = event.entityPlayer.openContainer;
			if (containerList.contains(openContainer.getClass()))
				event.setResult(Result.ALLOW);
		}
	}

	@ForgeSubscribe
	public void tooltip(ItemTooltipEvent event) {
		if (event.itemStack != null && event.itemStack.itemID == ModBlocks.market.blockID) {
			event.toolTip.add("BROKEN ON SERVERS! DO NOT USE!");
			event.toolTip.add("REMOVE ALL YOUR ITEMS FROM IT AS SOON AS POSSIBLE");
		}
	}
}