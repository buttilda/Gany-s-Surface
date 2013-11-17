package ganymedes01.ganyssurface.entities;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.lib.ParticleEffectsID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

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
		GanysSurface.proxy.handleParticleEffects(worldObj, posX, posY, posZ, ParticleEffectsID.POOP, 1);
	}
}