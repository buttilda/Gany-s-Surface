package ganymedes01.ganyssurface.world;

import java.util.Random;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SurfaceWorldGen implements IWorldGenerator {

	private final WorldGenMinable generatorBasalt;

	public SurfaceWorldGen() {
		generatorBasalt = new WorldGenMinable(ModBlocks.basalt, 0, GanysSurface.basaltBlocksPerCluster, Blocks.stone);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId != 0)
			return;

		if (GanysSurface.enableBasalt && GanysSurface.basaltBlocksPerCluster > 0)
			for (int i = 0; i < 10; i++) {
				int x = chunkX * 16 + rand.nextInt(16);
				int y = rand.nextInt(80);
				int z = chunkZ * 16 + rand.nextInt(16);

				generatorBasalt.generate(world, rand, x, y, z);
			}
	}
}