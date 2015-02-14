package ganymedes01.ganyssurface.world;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SurfaceWorldGen implements IWorldGenerator {

	private final List<WorldGenMinable> generators = new LinkedList<WorldGenMinable>();

	public SurfaceWorldGen() {
		generators.add(new WorldGenMinable(ModBlocks.newStones, 1, 33, Blocks.stone));
		generators.add(new WorldGenMinable(ModBlocks.newStones, 3, 33, Blocks.stone));
		generators.add(new WorldGenMinable(ModBlocks.newStones, 5, 33, Blocks.stone));
		generators.add(new WorldGenMinable(ModBlocks.newStones, 7, 33, Blocks.stone));
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId != 0)
			return;

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

		if (GanysSurface.enablePrismarineStuff)
			if (rand.nextInt(GanysSurface.prismarineTempleChance) == 0) {
				int x = chunkX * 16 + rand.nextInt(16);
				int z = chunkZ * 16 + rand.nextInt(16);
				if (biomeIsOcean(BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(x, z)))) {
					int height = world.getHeightValue(x, z);
					int y = height;

					for (; y > 0; y--) {
						Block block = world.getBlock(x, y, z);
						if (block.getMaterial() != Material.water && !block.isAir(world, x, y, z))
							break;
					}

					int pillarHeight = height - y - 22; // 22 is the height of the temple without the pillars
					pillarHeight -= 2 + rand.nextInt(2); // So that the temple is X blocks under water
					if (pillarHeight >= 0) {

						// Check if temple fits
						for (int i = 0; i < 58; i++)
							for (int j = 0; j < 22; j++)
								for (int k = 0; k < 58; k++)
									if (!world.getBlock(x + i, y + j + pillarHeight, z + k).isReplaceable(world, x + i, y + j + pillarHeight, z + k))
										return;
						Temple.buildTemple(world, x, y, z, pillarHeight);
						return;
					}
					return;
				}
			}
	}

	private boolean biomeIsOcean(BiomeDictionary.Type[] types) {
		for (int i = 0; i < types.length; i++)
			if (types[i] == BiomeDictionary.Type.OCEAN)
				return true;
		return false;
	}
}