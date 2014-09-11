package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.items.Quiver;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class MiscEventHandler {

	@SubscribeEvent
	public void arrowNock(ArrowNockEvent event) {
		ItemStack stack = event.result;
		if (stack == null)
			return;

		boolean flag = false;

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0)
			flag = true;
		else
			for (int i = 0; i < event.entityPlayer.inventory.getSizeInventory(); i++) {
				int count = Quiver.getArrowCount(event.entityPlayer.inventory.getStackInSlot(i));
				flag = count > 0;
				if (flag)
					break;
			}

		if (flag) {
			event.setCanceled(true);
			event.entityPlayer.setItemInUse(stack, stack.getItem().getMaxItemUseDuration(stack));
		}
	}

	@SubscribeEvent
	public void arrowLoose(ArrowLooseEvent event) {
		if (event.entityPlayer.inventory.hasItem(Items.arrow) || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, event.bow) > 0)
			return;

		float charge = event.charge / 20.0F;
		charge = (charge * charge + charge * 2.0F) / 3.0F;

		if (charge < 0.1D)
			return;
		if (charge > 1.0F)
			charge = 1.0F;

		for (int i = 0; i < event.entityPlayer.inventory.getSizeInventory(); i++) {
			ItemStack quiver = event.entityPlayer.inventory.getStackInSlot(i);
			int count = Quiver.getArrowCount(quiver);
			if (count > 0) {

				EntityArrow arrow = new EntityArrow(event.entityPlayer.worldObj, event.entityPlayer, charge * 2.0F);

				if (charge == 1.0F)
					arrow.setIsCritical(true);

				int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, event.bow);
				if (power > 0)
					arrow.setDamage(arrow.getDamage() + power * 0.5D + 0.5D);

				int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, event.bow);
				if (punch > 0)
					arrow.setKnockbackStrength(punch);

				if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, event.bow) > 0)
					arrow.setFire(100);

				event.bow.damageItem(1, event.entityPlayer);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.bow", 1.0F, 1.0F / (event.entityPlayer.worldObj.rand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

				Quiver.setArrowCount(quiver, count - 1);

				if (!event.entityPlayer.worldObj.isRemote)
					event.entityPlayer.worldObj.spawnEntityInWorld(arrow);
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent
	public void harvestEvent(BlockEvent.HarvestDropsEvent event) {
		if (GanysSurface.enablePineCones)
			if (event.block == Blocks.leaves && (event.blockMetadata & 3) == 1)
				if (event.world.rand.nextInt(20) == 0)
					event.drops.add(new ItemStack(ModItems.pineCone));
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (GanysSurface.enableInvertedDaylightSensor)
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

	@SubscribeEvent
	public void onHoeUseEvent(UseHoeEvent event) {
		if (GanysSurface.enableCoarseDirt) {
			World world = event.world;
			if (world.getBlock(event.x, event.y, event.z) == Blocks.dirt && world.getBlockMetadata(event.x, event.y, event.z) == 1) {
				world.setBlockMetadataWithNotify(event.x, event.y, event.z, 0, 3);
				world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
				event.setResult(Result.ALLOW);
			}
		}
	}
}