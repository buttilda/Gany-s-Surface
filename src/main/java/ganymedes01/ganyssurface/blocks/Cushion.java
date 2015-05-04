package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Cushion extends BlockFalling implements IConfigurable {

	public Cushion() {
		super(Material.cloth);
		setHardness(0.2F);
		setStepSound(soundTypeCloth);
		setBlockName(Utils.getUnlocalisedName(Strings.CUSHION_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.CUSHION_NAME));
		setCreativeTab(GanysSurface.enableCushion ? GanysSurface.surfaceTab : null);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.875F, z + 1);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (entity.fallDistance > 20.0F)
			entity.fallDistance -= 20.0F;
		else
			entity.fallDistance = 0.0F;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableCushion;
	}
}