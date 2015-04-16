package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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
			if (GanysSurface.enableMutton) {
				int amount = rand.nextInt(3) + 1 + rand.nextInt(1 + event.lootingLevel);
				for (int i = 0; i < amount; i++)
					if (event.entityLiving.isBurning())
						addDrop(new ItemStack(ModItems.cookedMutton), event.entityLiving, event.drops);
					else
						addDrop(new ItemStack(ModItems.rawMutton), event.entityLiving, event.drops);
			}
		} else if (event.entityLiving instanceof EntityEnderman)
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
		entityItem.delayBeforeCanPickup = 10;
		list.add(entityItem);
	}
}