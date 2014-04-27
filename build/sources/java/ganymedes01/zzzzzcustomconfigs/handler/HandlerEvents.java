package ganymedes01.zzzzzcustomconfigs.handler;

import ganymedes01.zzzzzcustomconfigs.registers.BlacklistedEntities;
import net.minecraft.entity.EntityList;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerEvents {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void entitySpawnEvent(CheckSpawn event) {
		if (BlacklistedEntities.entityBlacklist.contains(EntityList.getEntityString(event.entityLiving).toLowerCase()))
			event.setResult(Result.DENY);
	}
}