package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.RenderIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class Lantern extends Block {

	protected Lantern() {
		super(ModIDs.LANTERN_ID, Material.glass);
		setHardness(0.3F);
		setLightValue(0.9375F);
		setTextureName("torch_on");
		setStepSound(soundGlassFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.LANTERN_NAME));
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
		int id = access.getBlockId(x, y, z);
		return id == blockID || id == Block.glass.blockID ? false : super.shouldSideBeRendered(access, x, y, z, side);
	}
}