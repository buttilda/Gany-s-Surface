package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.RenderIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
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

public class Lantern extends Block implements IConfigurable {

	public Lantern() {
		super(Material.glass);
		setHardness(0.3F);
		setLightLevel(0.9375F);
		setBlockTextureName("torch_on");
		setStepSound(soundTypeGlass);
		setBlockName(Utils.getUnlocalisedName(Strings.LANTERN_NAME));
		setCreativeTab(GanysSurface.enableLantern ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.LANTERN;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		world.spawnParticle("smoke", x + 0.5F, y + 0.7F, 0.5F, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("flame", x + 0.5F, y + 0.7F, 0.5F, 0.0D, 0.0D, 0.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
		Block block = access.getBlock(x, y, z);
		return block == this || block == Blocks.glass ? false : super.shouldSideBeRendered(access, x, y, z, side);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableLantern;
	}
}