package ganymedes01.ganyssurface.core.handlers;

import java.util.List;
import java.util.Random;

import ganymedes01.ganyssurface.GanysSurface;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
		if (event.entityLiving instanceof EntityEnderman)
			if (GanysSurface.enableEndermanDropsBlocks) {
				EntityEnderman enderman = (EntityEnderman) event.entityLiving;
				Block block = enderman.func_146080_bZ();
				int meta = enderman.getCarryingData();
				if (block != null && block != Blocks.air)
					addDrop(new ItemStack(block, 1, meta), event.entityLiving, event.drops);
			}
	}

	private void addDrop(ItemStack stack, EntityLivingBase entity, List<EntityItem> list) {
		if (stack.stackSize <= 0)
			return;

		EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
		entityItem.setDefaultPickupDelay();
		list.add(entityItem);
	}
}