package ganymedes01.ganyssurface.world;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SurfaceWorldGen implements IWorldGenerator {

	private final List<WorldGenMinable> generators = new LinkedList<WorldGenMinable>();
	private final WorldGenMinable generatorBasalt;

	public SurfaceWorldGen() {
		generators.add(new WorldGenMinable(ModBlocks.newStones, 1, GanysSurface.max18StonesPerCluster, Blocks.stone));
		generators.add(new WorldGenMinable(ModBlocks.newStones, 3, GanysSurface.max18StonesPerCluster, Blocks.stone));
		generators.add(new WorldGenMinable(ModBlocks.newStones, 5, GanysSurface.max18StonesPerCluster, Blocks.stone));

		generatorBasalt = new WorldGenMinable(ModBlocks.basalt, 0, GanysSurface.max18StonesPerCluster, Blocks.stone);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId != 0)
			return;

		if (GanysSurface.enableCoarseDirt)
			for (int x = chunkX * 16; x < chunkX * 16 + 16; x++)
				for (int z = chunkZ * 16; z < chunkZ * 16 + 16; z++)
					for (int y = 0; y < world.getActualHeight(); y++)
						if (world.getBlock(x, y, z) == Blocks.dirt && world.getBlockMetadata(x, y, z) == 1)
							world.setBlock(x, y, z, ModBlocks.coarseDirt, 0, 2);

		if (GanysSurface.enable18Stones)
			for (Iterator<WorldGenMinable> iterator = generators.iterator(); iterator.hasNext();) {
				WorldGenMinable generator = iterator.next();
				for (int i = 0; i < 10; i++) {
					int x = chunkX * 16 + rand.nextInt(16);
					int y = rand.nextInt(80);
					int z = chunkZ * 16 + rand.nextInt(16);

					generator.generate(world, rand, x, y, z);
				}
			}

		if (GanysSurface.enableBasalt)
			for (int i = 0; i < 10; i++) {
				int x = chunkX * 16 + rand.nextInt(16);
				int y = rand.nextInt(80);
				int z = chunkZ * 16 + rand.nextInt(16);

				generatorBasalt.generate(world, rand, x, y, z);
			}

		if (GanysSurface.enablePrismarineStuff)
			if (OceanMonument.canSpawnAt(world, chunkX, chunkZ)) {
				int x = chunkX * 16 + rand.nextInt(16);
				int y = 256;
				int z = chunkZ * 16 + rand.nextInt(16);
				for (; y > 0; y--)
					if (!world.getBlock(x, y, z).isAir(world, x, y, z))
						break;
				int monumentCeiling = y - (1 + rand.nextInt(3));
				OceanMonument.buildTemple(world, x, monumentCeiling - 22, z);
				return;
			}
	}
}