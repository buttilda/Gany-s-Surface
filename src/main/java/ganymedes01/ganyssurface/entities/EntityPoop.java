package ganymedes01.ganyssurface.entities;

import ganymedes01.ganyssurface.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class EntityPoop extends EntityThrowable {

	public EntityPoop(World world) {
		super(world);
	}

	public EntityPoop(World world, EntityLivingBase player) {
		super(world, player);
	}

	public EntityPoop(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		if (target.entityHit != null)
			target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0);

		for (int i = 0; i < 8; i++)
			spawnParticle();

		if (!worldObj.isRemote)
			setDead();
	}

	protected void spawnParticle() {
		if (worldObj.isRemote) {
			worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(ModItems.poop) + "_0", posX, posY, posZ, (Math.random() * 2.0D - 1.0D) * 0.2D, (Math.random() * 2.0D - 1.0D) * 0.2D, (Math.random() * 2.0D - 1.0D) * 0.2D);
			worldObj.spawnParticle("mobSpell", posX, posY, posZ, 45.0D / 255.0D, 104.0D / 255.0D, 20.0D / 255.0D);
		}
	}
}