package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityOrganicMatterCompressor;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class OrganicMatterCompressor extends BlockContainer {

	public OrganicMatterCompressor() {
		super(ModIDs.ORGANIC_MATTER_COMPRESSOR_ID, Material.rock);
		setHardness(3.5F);
		setStepSound(soundStoneFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getBlockTexture(Strings.ORGANIC_MATTER_COMPRESSOR_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ORGANIC_MATTER_COMPRESSOR_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityOrganicMatterCompressor();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityOrganicMatterCompressor tile = (TileEntityOrganicMatterCompressor) world.getBlockTileEntity(x, y, z);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.ORGANIC_MATTER_COMPRESSOR, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityOrganicMatterCompressor tile = (TileEntityOrganicMatterCompressor) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int j1 = 0; j1 < tile.getSizeInventory(); ++j1) {
				ItemStack stack = tile.getStackInSlot(j1);
				if (stack != null)
					Utils.dropStack(world, x, y, z, stack);
			}
			world.func_96440_m(x, y, z, par5);
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return Container.calcRedstoneFromInventory((IInventory) world.getBlockTileEntity(x, y, z));
	}
}
