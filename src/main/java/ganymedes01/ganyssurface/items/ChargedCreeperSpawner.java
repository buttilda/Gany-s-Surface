package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSimpleFoiled;
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

public class ChargedCreeperSpawner extends ItemSimpleFoiled implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon overlay;

	public ChargedCreeperSpawner() {
		setTextureName("spawn_egg");
		setCreativeTab(GanysSurface.enableSpawnEggs ? GanysSurface.surfaceTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.CHARGED_CREEPER_SPAWNER_NAME));
	}

	public static Entity spawnCreeper(World world, double x, double y, double z) {
		if (!EntityList.entityEggs.containsKey(Integer.valueOf(50)))
			return null;
		else {
			EntityCreeper creeper = null;
			Entity entity = EntityList.createEntityByID(50, world);
			if (entity instanceof EntityCreeper) {
				creeper = (EntityCreeper) entity;
				if (creeper != null) {
					creeper.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
					creeper.rotationYawHead = creeper.rotationYaw;
					creeper.renderYawOffset = creeper.rotationYaw;
					creeper.onSpawnWithEgg((IEntityLivingData) null);
					creeper.getDataWatcher().updateObject(17, Byte.valueOf((byte) 1));
					world.spawnEntityInWorld(creeper);
					creeper.playLivingSound();
				}
			}
			return creeper;
		}
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

			Entity entity = spawnCreeper(world, x + 0.5D, y + yOffSet, z + 0.5D);

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
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return pass == 0 ? 894731 : 0;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableSpawnEggs;
	}
}