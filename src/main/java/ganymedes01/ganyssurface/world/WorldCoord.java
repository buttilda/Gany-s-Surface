package ganymedes01.ganyssurface.world;

import net.minecraft.tileentity.TileEntity;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class WorldCoord implements Comparable<WorldCoord> {

	public int x;
	public int y;
	public int z;

	public WorldCoord(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public WorldCoord(TileEntity tile) {
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(x).append(y).append(z).hashCode();
	}

	@Override
	public int compareTo(WorldCoord wc) {
		if (x == wc.x) {
			if (y == wc.y)
				return z - wc.z;
			return y - wc.y;
		}

		return x - wc.x;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof WorldCoord))
			return false;
		WorldCoord wc = (WorldCoord) obj;

		return x == wc.x && y == wc.y && z == wc.z;
	}

	@Override
	public String toString() {
		return "Coord: " + x + ", " + y + ", " + z;
	}
}