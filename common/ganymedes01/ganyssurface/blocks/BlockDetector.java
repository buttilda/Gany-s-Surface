package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityBlockDetector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BlockDetector extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon blockOn, blockOff;

	protected BlockDetector(int id) {
		super(id, Material.rock);
		setHardness(0.2F);
		setStepSound(soundWoodFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getBlockTexture(Strings.BLOCK_DETECTOR_NAME, true));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.BLOCK_DETECTOR_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta == 0)
			return blockOff;
		else
			return blockOn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockOn = reg.registerIcon(Utils.getBlockTexture(Strings.BLOCK_DETECTOR_NAME, true) + "on");
		blockOff = reg.registerIcon(Utils.getBlockTexture(Strings.BLOCK_DETECTOR_NAME, true) + "off");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockDetector();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		TileEntityBlockDetector tile = (TileEntityBlockDetector) world.getBlockTileEntity(x, y, z);
		if (tile != null)
			tile.updateRedstoneSignalStatus(tile.checkNearbyBlocks());
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityBlockDetector tileentitydetector = (TileEntityBlockDetector) world.getBlockTileEntity(x, y, z);
			if (tileentitydetector != null)
				player.openGui(GanysSurface.instance, GUIsID.BLOCK_DETECTOR, world, x, y, z);
			return true;
		}
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int side) {
		return access.getBlockMetadata(x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityBlockDetector tile = (TileEntityBlockDetector) world.getBlockTileEntity(x, y, z);
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

	public void updateBlockStatus(World world, int x, int y, int z, boolean flag) {
		int metadata = world.getBlockMetadata(x, y, z);

		if (flag) {
			if (metadata != 15)
				world.setBlockMetadataWithNotify(x, y, z, 15, 3);
		} else if (metadata != 0)
			world.setBlockMetadataWithNotify(x, y, z, 0, 3);
	}
}
