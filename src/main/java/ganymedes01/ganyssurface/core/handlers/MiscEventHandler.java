package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.items.Quiver;
import ganymedes01.ganyssurface.lib.GUIsID;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
				if (flag = count > 0)
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

				if (!event.entityPlayer.capabilities.isCreativeMode)
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
				if (event.world.rand.nextInt(200) == 0)
					event.drops.add(new ItemStack(ModItems.pineCone));

		if (GanysSurface.enableEatenCake)
			if (event.block == Blocks.cake)
				if (event.blockMetadata == 0)
					event.drops.add(new ItemStack(Items.cake));
				else
					event.drops.add(new ItemStack(ModItems.eatenCake, 1, event.blockMetadata));
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (GanysSurface.enableNoRecipeConflictForCrafTable)
			if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
				World world = event.world;
				EntityPlayer player = event.entityPlayer;
				int x = event.x;
				int y = event.y;
				int z = event.z;

				if (world != null && !world.isRemote && !player.isSneaking()) {
					Block block = world.getBlock(x, y, z);
					if (block == Blocks.crafting_table || "ganymedes01.woodstuff.blocks.BlockWoodCraftingTable".equals(block.getClass().getCanonicalName())) {
						player.openGui(GanysSurface.instance, GUIsID.CRAFTING_TABLE_NO_CONFLICT.ordinal(), world, x, y, z);
						event.setCanceled(true);
					}
				}
			}
	}

	@SubscribeEvent
	public void spawnEvent(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPig) {
			EntityPig pig = (EntityPig) event.entity;
			if (GanysSurface.enablePineCones)
				pig.tasks.addTask(4, new EntityAITempt(pig, 1.2, ModItems.pineCone, false));
		} else if (event.entity instanceof EntityChicken) {
			EntityChicken chicken = (EntityChicken) event.entity;
			if (GanysSurface.enableTea)
				chicken.tasks.addTask(3, new EntityAITempt(chicken, 1.0D, ModItems.camelliaSeeds, false));
			if (GanysSurface.enablePineCones)
				chicken.tasks.addTask(3, new EntityAITempt(chicken, 1.0D, ModItems.pineNuts, false));
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void interactEntityEvent(EntityInteractEvent event) {
		ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
		if (stack == null)
			return;

		if (event.target instanceof EntityPig) {
			if (stack.getItem() == ModItems.pineCone && GanysSurface.enablePineCones)
				setAnimalInLove((EntityPig) event.target, event.entityPlayer, stack);
		} else if (event.target instanceof EntityChicken) {
			if (stack.getItem() == ModItems.camelliaSeeds && GanysSurface.enableTea)
				setAnimalInLove((EntityChicken) event.target, event.entityPlayer, stack);
			if (stack.getItem() == ModItems.pineNuts && GanysSurface.enablePineCones)
				setAnimalInLove((EntityChicken) event.target, event.entityPlayer, stack);
		}
	}

	private void setAnimalInLove(EntityAnimal pig, EntityPlayer player, ItemStack stack) {
		if (!pig.isChild() && !pig.isInLove()) {
			pig.func_146082_f(player);
			if (!player.capabilities.isCreativeMode)
				if (--stack.stackSize <= 0)
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}
	}
}