package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class HorseSpawner extends Item {

	@SideOnly(Side.CLIENT)
	private Icon overlay;

	public HorseSpawner() {
		super(ModIDs.HORSE_SPAWNER_ID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setTextureName("spawn_egg");
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.HORSE_SPAWNER_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() == 2;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + Utils.getUnlocalizedName(Strings.HORSE_SPAWNER_NAME) + stack.getItemDamage();
	}

	public static Entity spawnHorse(World world, double x, double y, double z, int type, String username) {
		if (!EntityList.entityEggs.containsKey(Integer.valueOf(100)))
			return null;
		else {
			EntityHorse horse = null;
			Entity entity = EntityList.createEntityByID(100, world);
			if (entity instanceof EntityHorse) {
				horse = (EntityHorse) entity;
				if (horse != null) {
					horse.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
					horse.rotationYawHead = horse.rotationYaw;
					horse.renderYawOffset = horse.rotationYaw;
					horse.onSpawnWithEgg((EntityLivingData) null);
					setHorseType(horse, type);
					if (username != null) {
						horse.setOwnerName(username);
						horse.setHorseTamed(true);
					}
					world.spawnEntityInWorld(horse);
					horse.playLivingSound();
				}
			}
			return horse;
		}
	}

	private static void setHorseType(EntityHorse horse, int type) {
		int newType = type;
		if (type > 4) {
			newType = new Random().nextInt(3);

			try {
				Attribute horseJumpStrength = null;
				for (Field field : EntityHorse.class.getDeclaredFields()) {
					field.setAccessible(true);
					if (field.get(null) instanceof Attribute) {
						horseJumpStrength = (Attribute) field.get(null);
						break;
					}
				}

				horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(30D);
				horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3375D);
				horse.getEntityAttribute(horseJumpStrength).setAttribute(1.0D);

			} catch (Exception e) {
			}
		}
		horse.setHorseType(newType);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		else {
			int id = world.getBlockId(x, y, z);
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];
			double yOffSet = 0.0D;

			if (side == 1 && Block.blocksList[id] != null && Block.blocksList[id].getRenderType() == 11)
				yOffSet = 0.5D;

			Entity entity = spawnHorse(world, x + 0.5D, y + yOffSet, z + 0.5D, stack.getItemDamage() + 3, player.getCommandSenderName());

			if (entity != null) {
				if (entity instanceof EntityLivingBase && stack.hasDisplayName())
					((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

				if (!player.capabilities.isCreativeMode)
					stack.stackSize--;
			}

			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		overlay = reg.registerIcon("spawn_egg_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass > 0 ? overlay : super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemID, 1, 0));
		list.add(new ItemStack(itemID, 1, 1));
		list.add(new ItemStack(itemID, 1, 2));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		switch (stack.getItemDamage()) {
			case 0:
				return pass == 0 ? Utils.getColour(43, 66, 43) : Utils.getColour(33, 51, 39);
			case 1:
				return pass == 0 ? Utils.getColour(228, 222, 218) : Utils.getColour(186, 181, 162);
			default:
				return pass == 0 ? 12623485 : 15656192;
		}
	}
}