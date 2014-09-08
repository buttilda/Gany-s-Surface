package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;

import java.util.ArrayList;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerHopper;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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

	@SubscribeEvent
	public void containerEvent(PlayerOpenContainerEvent event) {
		if (GanysSurface.enableChestPropellant) {
			Container openContainer = event.entityPlayer.openContainer;
			if (containerList.contains(openContainer.getClass()))
				event.setResult(Result.ALLOW);
		}
	}
}