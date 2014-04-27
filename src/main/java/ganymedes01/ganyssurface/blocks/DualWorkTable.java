package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class DualWorkTable extends WorkTable {

	public DualWorkTable() {
		super(ModIDs.DUAL_WORK_TABLE_ID);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.DUAL_WORK_TABLE_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityDualWorkTable tile = (TileEntityDualWorkTable) world.getBlockTileEntity(x, y, z);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.DUAL_WORK_TABLE, world, x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDualWorkTable();
	}

	@Override
	public int getRenderType() {
		return -1;
	}
}