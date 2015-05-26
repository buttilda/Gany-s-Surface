package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Basalt extends BlockGeneric implements ISubBlocksBlock, IConfigurable {

	public Basalt() {
		super(Material.rock, "", "smooth");
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName(Strings.BASALT));
		setBlockTextureName(Utils.getBlockTexture("stone_basalt"));
		setCreativeTab(GanysSurface.enableBasalt ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return this == target || target == Blocks.stone;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableBasalt;
	}
}