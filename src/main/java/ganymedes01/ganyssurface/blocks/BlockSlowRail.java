package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockSlowRail extends BlockRailPowered implements IConfigurable {

	public BlockSlowRail() {
		setHardness(0.7F);
		setStepSound(soundTypeMetal);
		setBlockName(Utils.getUnlocalisedName(Strings.SLOW_RAIL));
		setBlockTextureName(Utils.getBlockTexture(Strings.SLOW_RAIL));
		setCreativeTab(GanysSurface.enableSlowRail ? GanysSurface.surfaceTab : null);
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z) {
		return 0.05F;
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, int y, int x, int z) {
		int meta = world.getBlockMetadata(y, x, z);
		if ((meta & 7) == meta)
			return;

		meta &= 7;
		if (cart.shouldDoRailFunctions()) {
			double d15 = Math.sqrt(cart.motionX * cart.motionX + cart.motionZ * cart.motionZ);

			if (d15 > 0.01D) {
				double d16 = 0.06D;
				cart.motionX += cart.motionX / d15 * d16;
				cart.motionZ += cart.motionZ / d15 * d16;
			} else if (meta == 1) {
				if (world.getBlock(y - 1, x, z).isNormalCube())
					cart.motionX = 0.02D;
				else if (world.getBlock(y + 1, x, z).isNormalCube())
					cart.motionX = -0.02D;
			} else if (meta == 0)
				if (world.getBlock(y, x, z - 1).isNormalCube())
					cart.motionZ = 0.02D;
				else if (world.getBlock(y, x, z + 1).isNormalCube())
					cart.motionZ = -0.02D;
		}
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableSlowRail;
	}
}