package ganymedes01.ganyssurface.entities;

import ganymedes01.ganyssurface.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class EntityBatPoop extends EntityPoop {

	public EntityBatPoop(World world) {
		super(world);
	}

	public EntityBatPoop(World world, EntityLivingBase player) {
		super(world, player);
	}

	public EntityBatPoop(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void spawnParticle() {
		if (worldObj.isRemote) {
			worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(ModItems.poop) + "_0", posX, posY, posZ, (Math.random() * 2.0D - 1.0D) * 0.2D, (Math.random() * 2.0D - 1.0D) * 0.2D, (Math.random() * 2.0D - 1.0D) * 0.2D);
			worldObj.spawnParticle("mobSpell", posX, posY, posZ, 45.0D / 255.0D, 104.0D / 255.0D, 20.0D / 255.0D);
		}
	}
}