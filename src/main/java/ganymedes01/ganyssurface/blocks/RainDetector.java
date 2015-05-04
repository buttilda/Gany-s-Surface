package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityRainDetector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class RainDetector extends BlockContainer implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon blockSide, blockTop;

	public RainDetector() {
		super(Material.circuits);
		setHardness(0.2F);
		setLightOpacity(0);
		setStepSound(soundTypeWood);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		setBlockName(Utils.getUnlocalisedName(Strings.RAIN_DETECTOR_NAME));
		setCreativeTab(GanysSurface.enableRainDetector ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRainDetector();
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.RAIN_DETECTOR_NAME) + "_side");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.RAIN_DETECTOR_NAME) + "_top");
	}

	public static void updateRainStatus(World world, int x, int y, int z, boolean isRaining) {
		if (isRaining)
			world.setBlockMetadataWithNotify(x, y, z, 15, 3);
		else
			world.setBlockMetadataWithNotify(x, y, z, 0, 3);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableRainDetector;
	}
}