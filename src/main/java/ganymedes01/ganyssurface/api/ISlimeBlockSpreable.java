package ganymedes01.ganyssurface.api;

import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public interface ISlimeBlockSpreable {

	float getSpreadChance(World world, int x, int y, int z);
}