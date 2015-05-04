package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityOrganicMatterCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class OrganicMatterCompressor extends BlockContainer implements IConfigurable {

	public OrganicMatterCompressor() {
		super(Material.rock);
		setHardness(3.5F);
		setStepSound(soundTypeStone);
		setCreativeTab(GanysSurface.enableOMC ? GanysSurface.surfaceTab : null);
		setBlockName(Utils.getUnlocalisedName(Strings.ORGANIC_MATTER_COMPRESSOR_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.ORGANIC_MATTER_COMPRESSOR_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityOrganicMatterCompressor();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityOrganicMatterCompressor tile = Utils.getTileEntity(world, x, y, z, TileEntityOrganicMatterCompressor.class);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.ORGANIC_MATTER_COMPRESSOR, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return Container.calcRedstoneFromInventory(Utils.getTileEntity(world, x, y, z, TileEntityOrganicMatterCompressor.class));
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableOMC;
	}
}