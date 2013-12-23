package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Facing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class PocketBat extends Item {

	public PocketBat() {
		super(ModIDs.POCKET_BAT_ID);
		setMaxStackSize(1);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.POCKET_BAT_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.POCKET_BAT_NAME));
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(StatCollector.translateToLocal("pleaseletmefree"));
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return ("" + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name")).trim();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		else {
			int blockID = world.getBlockId(x, y, z);
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];

			Entity entity = ItemMonsterPlacer.spawnCreature(world, 65, x + 0.5D, y + 0.5D, z + 0.5D);

			if (entity != null) {
				((EntityBat) entity).func_110163_bv();
				if (stack.hasDisplayName())
					((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

				if (!player.capabilities.isCreativeMode)
					stack.stackSize--;
			}

			return true;
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote)
			return stack;
		else {
			MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);

			if (movingobjectposition == null)
				return stack;
			else {
				if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
					int i = movingobjectposition.blockX;
					int j = movingobjectposition.blockY;
					int k = movingobjectposition.blockZ;

					if (!world.canMineBlock(player, i, j, k))
						return stack;

					if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack))
						return stack;

					if (world.getBlockMaterial(i, j, k) == Material.water) {
						Entity entity = ItemMonsterPlacer.spawnCreature(world, 65, i, j, k);

						if (entity != null) {
							if (entity instanceof EntityLivingBase && stack.hasDisplayName())
								((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

							if (!player.capabilities.isCreativeMode)
								--stack.stackSize;
						}
					}
				}

				return stack;
			}
		}
	}
}
