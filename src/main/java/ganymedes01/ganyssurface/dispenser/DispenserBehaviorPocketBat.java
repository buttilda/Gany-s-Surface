package ganymedes01.ganyssurface.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class DispenserBehaviorPocketBat extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource block, ItemStack stack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(block.getBlockMetadata());
		double x = block.getX() + enumfacing.getFrontOffsetX();
		double y = block.getYInt() + 0.2F;
		double z = block.getZ() + enumfacing.getFrontOffsetZ();

		int entityID;
		if (stack.getItemDamage() == 0)
			entityID = 65;
		else if (stack.getItemDamage() == 1)
			entityID = 94;
		else
			entityID = -1;

		if (entityID > 0) {
			Entity entity = ItemMonsterPlacer.spawnCreature(block.getWorld(), entityID, x, y, z);
			((EntityBat) entity).func_110163_bv();

			if (entity instanceof EntityLivingBase && stack.hasDisplayName())
				((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

			stack.splitStack(1);
		}
		return stack;
	}
}