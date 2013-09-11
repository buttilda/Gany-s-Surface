package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PoopHandler {

	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityAnimal || event.entityLiving instanceof EntityTameable || event.entityLiving instanceof EntityBat) {
			int coolDown = event.entityLiving.worldObj.rand.nextInt(30000);
			if (coolDown == 15000)
				if (!event.entityLiving.isChild() && event.entityLiving.worldObj.isDaytime())
					if (!event.entityLiving.worldObj.isRemote) {
						if (event.entityLiving instanceof EntityBat)
							event.entityLiving.entityDropItem(new ItemStack(ModItems.poop, 1, 1), 0.0F);
						else
							event.entityLiving.entityDropItem(new ItemStack(ModItems.poop, 1, 0), 0.0F);
						event.entityLiving.playSound("mob.chicken.plop", 1.0F, (event.entityLiving.worldObj.rand.nextFloat() - event.entityLiving.worldObj.rand.nextFloat()) * 0.2F + 1.0F);
					}
		}
	}
}
