package ganymedes01.ganyssurface.entities;

import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
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

		if (worldObj.isRemote)
			for (int i = 0; i < 8; i++)
				spawnParticle();

		if (!worldObj.isRemote)
			setDead();
	}

	protected void spawnParticle() {
		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBreakingFX(worldObj, posX, posY, posZ, ModItems.poop, 0));
	}
}