package ganymedes01.ganyssurface.core.handlers;

import java.util.Iterator;
import java.util.Set;

import ganymedes01.ganyssurface.GanysSurface;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SnowTickHandler {

	@SubscribeEvent
	public void tick(WorldTickEvent event) {
		if (!GanysSurface.enableDynamicSnow)
			return;

		World world = event.world;
		for (Iterator<ChunkCoordIntPair> iterator = getActiveChunks(world); iterator.hasNext();) {
			ChunkCoordIntPair chunkcoordintpair = iterator.next();
			int chunkX = chunkcoordintpair.chunkXPos * 16;
			int chunkZ = chunkcoordintpair.chunkZPos * 16;
			Chunk chunk = world.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);

			if (world.provider.canDoRainSnowIce(chunk) && world.rand.nextInt(24) == 0) {
				int rand = world.rand.nextInt() - world.rand.nextInt();
				int x = rand & 15;
				int z = rand >> 8 & 15;
				int y = world.getPrecipitationHeight(x + chunkX, z + chunkZ);
				if (world.getBlock(x + chunkX, y, z + chunkZ) == Blocks.snow_layer) {
					int meta = world.getBlockMetadata(x + chunkX, y, z + chunkZ);
					if (world.isRaining()) {
						if (meta < 6)
							world.setBlockMetadataWithNotify(x + chunkX, y, z + chunkZ, meta + 1, 3);
					} else if (world.rand.nextInt(4) == 0)
						if (meta > 0)
							world.setBlockMetadataWithNotify(x + chunkX, y, z + chunkZ, meta - 1, 3);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Iterator<ChunkCoordIntPair> getActiveChunks(World world) {
		return ((Set<ChunkCoordIntPair>) ReflectionHelper.getPrivateValue(World.class, world, "activeChunkSet", "field_72993_I")).iterator();
	}
}