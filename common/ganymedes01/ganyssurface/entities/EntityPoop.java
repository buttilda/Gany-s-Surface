package ganymedes01.ganyssurface.entities;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.lib.ParticleEffectsID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

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
		GanysSurface.proxy.handleParticleEffects(worldObj, posX, posY, posZ, ParticleEffectsID.POOP, 0);
	}
}