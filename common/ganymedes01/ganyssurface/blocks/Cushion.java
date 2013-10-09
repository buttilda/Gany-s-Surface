package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockSand;
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

public class Cushion extends BlockSand {

	public Cushion() {
		super(ModIDs.CUSHION_ID, Material.cloth);
		setHardness(0.2F);
		setStepSound(soundClothFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getBlockTexture(Strings.CUSHION_NAME, false));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CUSHION_NAME));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1, y + 0.875F, z + 1);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (entity.fallDistance > 20.0F)
			entity.fallDistance -= 20.0F;
		else
			entity.fallDistance = 0.0F;
	}
}
