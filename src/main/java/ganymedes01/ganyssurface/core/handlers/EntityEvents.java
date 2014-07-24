package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class EntityEvents {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void dropEvent(LivingDropsEvent event) {
		if (event.entityLiving.worldObj.isRemote)
			return;
		Random rand = event.entityLiving.worldObj.rand;
		if (event.entityLiving instanceof EntitySheep) {
			int amount = rand.nextInt(3) + 1 + rand.nextInt(1 + event.lootingLevel);
			for (int i = 0; i < amount; i++)
				if (event.entityLiving.isBurning())
					addDrop(new ItemStack(ModItems.cookedMutton), event.entityLiving, event.drops);
				else
					addDrop(new ItemStack(ModItems.rawMutton), event.entityLiving, event.drops);
		}
	}

	@SuppressWarnings("unchecked")
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void itemExpire(ItemExpireEvent event) {
		int radius = GanysSurface.noDespawnRadius;
		if (radius == 0 || event.isCanceled())
			return;

		if (radius < 0) {
			event.extraLife = event.entityItem.lifespan;
			event.setCanceled(true);
			return;
		}

		World world = event.entityItem.worldObj;
		double posX = event.entityItem.posX;
		double posY = event.entityItem.posY;
		double posZ = event.entityItem.posZ;
		List<EntityPlayerMP> playersNearby = world.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius));

		if (!playersNearby.isEmpty()) {
			event.extraLife = event.entityItem.lifespan;
			event.setCanceled(true);
		}
	}

	private void addDrop(ItemStack stack, EntityLivingBase entity, List<EntityItem> list) {
		if (stack.stackSize <= 0)
			return;

		EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
		entityItem.delayBeforeCanPickup = 10;
		list.add(entityItem);
	}
}