package ganymedes01.ganyssurface.dispenser;

import ganymedes01.ganyssurface.items.HorseSpawner;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class DispenserBehaviorHorseSpawner extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource block, ItemStack stack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(block.getBlockMetadata());
		double x = block.getX() + enumfacing.getFrontOffsetX();
		double y = block.getYInt() + 0.2F;
		double z = block.getZ() + enumfacing.getFrontOffsetZ();
		Entity entity = HorseSpawner.spawnHorse(block.getWorld(), x, y, z, stack.getItemDamage(), null);

		if (entity instanceof EntityLivingBase && stack.hasDisplayName())
			((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

		stack.splitStack(1);
		return stack;
	}
}