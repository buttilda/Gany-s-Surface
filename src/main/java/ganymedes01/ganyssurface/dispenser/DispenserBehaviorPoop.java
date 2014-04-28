package ganymedes01.ganyssurface.dispenser;

import ganymedes01.ganyssurface.entities.EntityBatPoop;
import ganymedes01.ganyssurface.entities.EntityPoop;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class DispenserBehaviorPoop extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource block, ItemStack stack) {
		World world = block.getWorld();
		IPosition iposition = BlockDispenser.func_149939_a(block);
		EnumFacing enumfacing = BlockDispenser.func_149937_b(block.getBlockMetadata());
		IProjectile iprojectile = null;
		if (stack.getItemDamage() == 0)
			iprojectile = new EntityPoop(world, iposition.getX(), iposition.getY(), iposition.getZ());
		else
			iprojectile = new EntityBatPoop(world, iposition.getX(), iposition.getY(), iposition.getZ());

		iprojectile.setThrowableHeading(enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY() + 0.1F, enumfacing.getFrontOffsetZ(), 1.1F, 6.0F);
		world.spawnEntityInWorld((Entity) iprojectile);
		stack.splitStack(1);
		return stack;
	}

	@Override
	protected void playDispenseSound(IBlockSource block) {
		block.getWorld().playAuxSFX(1002, block.getXInt(), block.getYInt(), block.getZInt(), 0);
	}
}