package ganymedes01.ganyssurface.entities;

import ganymedes01.ganyssurface.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class EntityVillageFinder extends Entity {

	private double targetX, targetY, targetZ;
	private int despawnTimer;
	private boolean shatterOrDrop;

	public EntityVillageFinder(World world) {
		super(world);
		setSize(0.25F, 0.25F);
	}

	public EntityVillageFinder(World world, double x, double y, double z) {
		super(world);
		despawnTimer = 0;
		setSize(0.25F, 0.25F);
		setPosition(x, y, z);
		yOffset = 0.0F;
	}

	@Override
	protected void entityInit() {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double dist) {
		double d1 = boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return dist < d1 * d1;
	}

	public void moveTowards(double x, int y, double z) {
		double d2 = x - posX;
		double d3 = z - posZ;
		float f = MathHelper.sqrt_double(d2 * d2 + d3 * d3);

		if (f > 12.0F) {
			targetX = posX + d2 / f * 12.0D;
			targetZ = posZ + d3 / f * 12.0D;
			targetY = posY + 8.0D;
		} else {
			targetX = x;
			targetY = y;
			targetZ = z;
		}

		despawnTimer = 0;
		shatterOrDrop = rand.nextInt(5) > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		motionX = x;
		motionY = y;
		motionZ = z;

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(x * x + z * z);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(y, f) * 180.0D / Math.PI);
		}
	}

	@Override
	public void onUpdate() {
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float) (Math.atan2(motionY, f) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
			;

		while (rotationPitch - prevRotationPitch >= 180.0F)
			prevRotationPitch += 360.0F;

		while (rotationYaw - prevRotationYaw < -180.0F)
			prevRotationYaw -= 360.0F;

		while (rotationYaw - prevRotationYaw >= 180.0F)
			prevRotationYaw += 360.0F;

		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;

		if (!worldObj.isRemote) {
			double d0 = targetX - posX;
			double d1 = targetZ - posZ;
			float f1 = (float) Math.sqrt(d0 * d0 + d1 * d1);
			float f2 = (float) Math.atan2(d1, d0);
			double d2 = f + (f1 - f) * 0.0025D;

			if (f1 < 1.0F) {
				d2 *= 0.8D;
				motionY *= 0.8D;
			}

			motionX = Math.cos(f2) * d2;
			motionZ = Math.sin(f2) * d2;

			if (posY < targetY)
				motionY += (1.0D - motionY) * 0.014999999664723873D;
			else
				motionY += (-1.0D - motionY) * 0.014999999664723873D;
		}

		float f3 = 0.25F;

		if (isInWater())
			for (int i = 0; i < 4; ++i)
				worldObj.spawnParticle("bubble", posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3, motionX, motionY, motionZ);
		else
			worldObj.spawnParticle("portal", posX - motionX * f3 + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * f3 - 0.5D, posZ - motionZ * f3 + rand.nextDouble() * 0.6D - 0.3D, motionX, motionY, motionZ);

		if (!worldObj.isRemote) {
			setPosition(posX, posY, posZ);
			++despawnTimer;

			if (despawnTimer > 80 && !worldObj.isRemote) {
				setDead();

				if (shatterOrDrop)
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(ModItems.villageFinder)));
				else
					worldObj.playAuxSFX(2003, (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ), 0);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound data) {
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound data) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public float getBrightness(float par1) {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1) {
		return 15728880;
	}

	@Override
	public boolean canAttackWithItem() {
		return false;
	}
}