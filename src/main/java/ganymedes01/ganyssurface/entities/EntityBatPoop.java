package ganymedes01.ganyssurface.entities;

import ganymedes01.ganyssurface.items.ModItems;
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
		if (worldObj.isRemote)
			worldObj.spawnParticle("iconcrack_" + ModItems.poop + "_" + 1, posX, posY, posZ, (Math.random() * 2.0D - 1.0D) * 0.2D, (Math.random() * 2.0D - 1.0D) * 0.2D, (Math.random() * 2.0D - 1.0D) * 0.2D);
	}
}