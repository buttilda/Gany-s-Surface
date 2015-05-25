package ganymedes01.ganyssurface.blocks.OnePointEight;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class CoarseDirt extends Block implements IConfigurable {

	public CoarseDirt() {
		super(Material.ground);
		setHardness(0.5F);
		setStepSound(soundTypeGravel);
		setBlockTextureName(Strings.COARSE_DIRT);
		setBlockName(Utils.getUnlocalisedName(Strings.COARSE_DIRT));
		setCreativeTab(GanysSurface.enableCoarseDirt ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plant) {
		return Blocks.dirt.canSustainPlant(world, x, y, z, direction, plant);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableCoarseDirt;
	}
}