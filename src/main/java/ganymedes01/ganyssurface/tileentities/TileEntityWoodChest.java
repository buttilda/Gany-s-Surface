package ganymedes01.ganyssurface.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityWoodChest extends TileEntityChest {

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 2, yCoord + 1, zCoord + 2);
	}

	@Override
	public int func_145980_j() {
		return Math.max(200, super.func_145980_j());
	}
}