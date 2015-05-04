package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
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

public class HorseSpawner extends Item implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon overlay;

	public HorseSpawner() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setTextureName("spawn_egg");
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.HORSE_SPAWNER_NAME));
		setCreativeTab(GanysSurface.enableSpawnEggs ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		return stack.getItemDamage() == 2;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + Utils.getUnlocalisedName(Strings.HORSE_SPAWNER_NAME) + stack.getItemDamage();
	}

	public static Entity spawnHorse(World world, double x, double y, double z, int meta, String username) {
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
					horse.onSpawnWithEgg((IEntityLivingData) null);
					setHorseType(horse, meta);
					if (username != null) {
						horse.func_152120_b(username);
						horse.setHorseTamed(true);
					}
					world.spawnEntityInWorld(horse);
					horse.playLivingSound();
				}
			}
			return horse;
		}
	}

	private static void setHorseType(EntityHorse horse, int meta) {
		int type = meta == 0 || meta == 1 ? meta + 3 : meta - 2;
		if (meta == 2) {
			type = new Random().nextInt(3);

			try {
				IAttribute horseJumpStrength = null;
				for (Field field : EntityHorse.class.getDeclaredFields()) {
					field.setAccessible(true);
					if (field.get(null) instanceof IAttribute) {
						horseJumpStrength = (IAttribute) field.get(null);
						break;
					}
				}

				horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
				horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3375D);
				horse.getEntityAttribute(horseJumpStrength).setBaseValue(1.0D);

			} catch (Exception e) {
			}
		}
		horse.setHorseType(type);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		else {
			Block block = world.getBlock(x, y, z);
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];
			double yOffSet = 0.0D;

			if (side == 1 && block != null && block.getRenderType() == 11)
				yOffSet = 0.5D;

			Entity entity = spawnHorse(world, x + 0.5D, y + yOffSet, z + 0.5D, stack.getItemDamage(), player.getCommandSenderName());

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
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		overlay = reg.registerIcon("spawn_egg_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass > 0 ? overlay : super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 5; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		switch (stack.getItemDamage()) {
			case 0:
				return pass == 0 ? Utils.getColour(43, 66, 43) : Utils.getColour(33, 51, 39);
			case 1:
				return pass == 0 ? Utils.getColour(228, 222, 218) : Utils.getColour(186, 181, 162);
			case 3:
				return pass == 0 ? 0xC09E7D : Utils.getColour(107, 92, 77);
			case 4:
				return pass == 0 ? 0xC09E7D : Utils.getColour(25, 15, 9);
			default:
				return pass == 0 ? 0xC09E7D : 0xEEE500;
		}
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableSpawnEggs;
	}
}