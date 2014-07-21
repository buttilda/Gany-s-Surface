package ganymedes01.ganyssurface.dispenser;

import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class DispenserBahaviourShears extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource block, ItemStack stack) {
		World world = block.getWorld();
		EnumFacing enumfacing = BlockDispenser.func_149937_b(block.getBlockMetadata());
		int x = block.getXInt() + enumfacing.getFrontOffsetX();
		int y = block.getYInt() + enumfacing.getFrontOffsetY();
		int z = block.getZInt() + enumfacing.getFrontOffsetZ();

		if (shearBlock(world, x, y, z, stack) || shearEntity(world, x, y, z, stack))
			stack.damageItem(1, Utils.getPlayer(world));

		if (stack.getItemDamage() >= stack.getItem().getMaxDamage(stack))
			stack.stackSize = 0;
		return stack;
	}

	private boolean shearBlock(World world, int x, int y, int z, ItemStack stack) {
		Block block = world.getBlock(x, y, z);
		if (block instanceof IShearable) {
			IShearable shearable = (IShearable) block;
			if (shear(shearable, stack, world, x, y, z))
				return world.setBlockToAir(x, y, z);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean shearEntity(World world, int x, int y, int z, ItemStack stack) {
		List<IShearable> list = world.getEntitiesWithinAABB(IShearable.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1));
		if (list != null && !list.isEmpty()) {
			IShearable shearable = list.get(world.rand.nextInt(list.size()));
			return shear(shearable, stack, world, x, y, z);
		}
		return false;
	}

	private boolean shear(IShearable shearable, ItemStack stack, World world, int x, int y, int z) {
		if (shearable.isShearable(stack, world, x, y, z)) {
			List<ItemStack> drops = shearable.onSheared(stack, world, x, y, z, 0);
			if (drops != null && !drops.isEmpty())
				for (ItemStack drop : drops)
					InventoryUtils.dropStack(world, x, y, z, drop);
			return true;
		}
		return false;
	}
}