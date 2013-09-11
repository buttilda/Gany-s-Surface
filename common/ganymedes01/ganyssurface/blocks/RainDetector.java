package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityRainDetector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
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

public class RainDetector extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon blockSide, blockTop;

	public RainDetector(int id) {
		super(id, Material.circuits);
		setHardness(0.2F);
		setLightOpacity(0);
		setStepSound(soundWoodFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.RAIN_DETECTOR_NAME));
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityRainDetector();
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int side) {
		return access.getBlockMetadata(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? blockTop : blockSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.RAIN_DETECTOR_NAME, true) + "side");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.RAIN_DETECTOR_NAME, true) + "top");
	}

	public void updateRainStatus(World world, int x, int y, int z, boolean isRaining) {
		if (isRaining)
			world.setBlockMetadataWithNotify(x, y, z, 15, 3);
		else
			world.setBlockMetadataWithNotify(x, y, z, 0, 3);
	}
}
