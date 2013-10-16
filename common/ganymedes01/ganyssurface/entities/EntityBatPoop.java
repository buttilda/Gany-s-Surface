package ganymedes01.ganyssurface.entities;

import ganymedes01.ganyssurface.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
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
		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBreakingFX(worldObj, posX, posY, posZ, ModItems.poop, 1));
	}
}