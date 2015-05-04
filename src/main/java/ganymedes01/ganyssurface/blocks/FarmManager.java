package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityFarmManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FarmManager extends BlockContainer implements IConfigurable {

	public FarmManager() {
		super(Material.cloth);
		setHardness(1.0F);
		setBlockName(Utils.getUnlocalisedName(Strings.FARM_MANAGER_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.PLANTER_NAME + "Top"));
		setCreativeTab(GanysSurface.enablePlanter ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return Container.calcRedstoneFromInventory(Utils.getTileEntity(world, x, y, z, TileEntityFarmManager.class));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityFarmManager tile = Utils.getTileEntity(world, x, y, z, TileEntityFarmManager.class);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.FARM_MANAGER, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFarmManager();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		if (world.isRemote)
			return;
		TileEntityFarmManager tile = Utils.getTileEntity(world, x, y, z, TileEntityFarmManager.class);
		tile.redstoneActive = world.isBlockIndirectlyGettingPowered(x, y, z);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePlanter;
	}
}