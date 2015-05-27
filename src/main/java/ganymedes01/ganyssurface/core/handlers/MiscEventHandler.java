package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.client.renderer.tileentity.TileEntityWoodChestRenderer;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerEnchantment;
import ganymedes01.ganyssurface.items.Quiver;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Reference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class MiscEventHandler {

	@SubscribeEvent
	public void interactEvent(PlayerInteractEvent event) {
		if (!GanysSurface.enable18Enchants)
			return;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			World world = event.world;
			EntityPlayer player = event.entityPlayer;
			int x = event.x;
			int y = event.y;
			int z = event.z;

			if (world == null || world.isRemote)
				return;
			if (player.isSneaking())
				return;
			else {
				TileEntityEnchantmentTable tile = Utils.getTileEntity(world, x, y, z, TileEntityEnchantmentTable.class);
				if (tile != null && world.getBlock(x, y, z) == Blocks.enchanting_table) {
					player.openGui(GanysSurface.instance, GUIsID.ENCHANTING_TABLE, world, x, y, z);
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLoadFromFileEvent(PlayerEvent.LoadFromFile event) {
		if (!GanysSurface.enable18Enchants)
			return;
		try {
			File file = event.getPlayerFile(Reference.MOD_ID);
			if (!file.exists()) {
				file.createNewFile();
				return;
			}

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if (line != null) {
				int seed = Integer.parseInt(line);
				ContainerEnchantment.seeds.put(event.playerUUID, seed);
				br.close();
			}
		} catch (Exception e) {
		}
	}

	@SubscribeEvent
	public void onPlayerSaveFromFileEvent(PlayerEvent.SaveToFile event) {
		if (!GanysSurface.enable18Enchants)
			return;
		try {
			File file = event.getPlayerFile(Reference.MOD_ID);
			if (!file.exists()) {
				file.createNewFile();
				return;
			}

			Integer seed = ContainerEnchantment.seeds.get(event.playerUUID);
			if (seed != null) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(seed.toString());
				bw.close();
			}
		} catch (IOException e) {
		}
	}

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
				if (event.world.rand.nextInt(200) == 0)
					event.drops.add(new ItemStack(ModItems.pineCone));

		if (GanysSurface.enableSilkTouchingMushrooms && event.isSilkTouching)
			if (event.block == Blocks.brown_mushroom_block) {
				event.drops.clear();
				event.drops.add(new ItemStack(ModBlocks.brown_mushroom_block));
			} else if (event.block == Blocks.red_mushroom_block) {
				event.drops.clear();
				event.drops.add(new ItemStack(ModBlocks.red_mushroom_block));
			}
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
			if (world.getBlock(event.x, event.y, event.z) == ModBlocks.coarseDirt) {
				world.setBlock(event.x, event.y, event.z, Blocks.dirt);
				world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
				event.setResult(Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public void spawnEvent(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPig) {
			EntityPig pig = (EntityPig) event.entity;
			pig.tasks.addTask(4, new EntityAITempt(pig, 1.2, ModItems.beetroot, false));
			pig.tasks.addTask(4, new EntityAITempt(pig, 1.2, ModItems.pineCone, false));
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void interactEntityEvent(EntityInteractEvent event) {
		ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
		if (stack != null && event.target instanceof EntityPig)
			if (stack.getItem() == ModItems.beetroot || stack.getItem() == ModItems.pineCone) {
				EntityPig pig = (EntityPig) event.target;
				if (!pig.isChild() && !pig.isInLove()) {
					pig.func_146082_f(event.entityPlayer);
					if (!event.entityPlayer.capabilities.isCreativeMode) {
						stack.stackSize--;
						if (stack.stackSize <= 0)
							event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
					}
				}
			}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void stitchEventPost(TextureStitchEvent.Post event) {
		if (GanysSurface.enableDynamicTextureChests)
			if (event.map.getTextureType() == 1) {
				TileEntityWoodChestRenderer.large_textures.clear();
				TileEntityWoodChestRenderer.normal_textures.clear();
			}
	}
}