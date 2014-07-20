package ganymedes01.ganyssurface.world;

import ganymedes01.ganyssurface.GanysSurface;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SurfaceWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId != 0)
			return;
		if (!GanysSurface.enablePrismarineStuff)
			return;

		int x = chunkX + rand.nextInt(16);
		int y = 50 + rand.nextInt(100);
		int z = chunkZ + rand.nextInt(16);

		for (BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(x, z)))
			if (type == BiomeDictionary.Type.OCEAN) {
				Temple.buildTemple(world, x, y, z);
				return;
			} else
				continue;
	}
}