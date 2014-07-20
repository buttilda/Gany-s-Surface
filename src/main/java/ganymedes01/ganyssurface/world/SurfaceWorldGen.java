package ganymedes01.ganyssurface.world;

import ganymedes01.ganyssurface.GanysSurface;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

		if (rand.nextInt(50) == 0) {
			int x = chunkX * 16 + rand.nextInt(16);
			int z = chunkZ * 16 + rand.nextInt(16);
			int y = world.getHeightValue(x, z);

			//TODO check if temple fits
			for (; y > 0; y--) {
				Block block = world.getBlock(x, y, z);
				if (block.getMaterial() != Material.water && !block.isAir(world, x, y, z))
					break;
			}

			for (BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(x, z)))
				if (type == BiomeDictionary.Type.OCEAN) {
					System.out.println("Temple at: " + x + ", " + y + ", " + z);
					int height = 4 + world.rand.nextInt(6);
					Temple.buildTemple(world, x, y, z, height);
					return;
				} else
					continue;
		}
	}
}