package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.api.ISlimeBlockSpreable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModSounds;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockOfPoop extends BlockGeneric implements ISlimeBlockSpreable, ISubBlocksBlock, IConfigurable {

	public BlockOfPoop() {
		super(Material.cloth, "", "bat");
		setHardness(2.0F);
		setHarvestLevel("shovel", 0);
		setStepSound(ModSounds.soundSlime);
		setBlockName(Utils.getUnlocalisedName(Strings.BLOCK_OF_POOP_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.BLOCK_OF_POOP_NAME));
		setCreativeTab(GanysSurface.enablePoop ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		world.spawnParticle("mobSpell", x + 0.5D, y + 1.0D, z + 0.5D, 45.0D / 255.0D, 104.0D / 255.0D, 20.0D / 255.0D);
	}

	@Override
	public float getSpreadChance(World world, int x, int y, int z) {
		float chance = 0.075F;
		if (world.getBlockMetadata(x, y, z) == 1)
			chance *= 2;

		return chance;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePoop;
	}
}