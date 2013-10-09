package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class WorkTable extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon blockTop, blockSide, blockFront;

	public WorkTable() {
		super(ModIDs.WORK_TABLE_ID, Material.wood);
		setHardness(2.5F);
		setStepSound(soundWoodFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.WORK_TABLE_NAME));
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		if (!world.isRemote) {
			int l = world.getBlockId(x, y, z - 1);
			int i1 = world.getBlockId(x, y, z + 1);
			int j1 = world.getBlockId(x - 1, y, z);
			int k1 = world.getBlockId(x + 1, y, z);
			byte b0 = 3;

			if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
				b0 = 3;

			if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
				b0 = 2;

			if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
				b0 = 5;

			if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
				b0 = 4;

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l == 0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		if (l == 1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);

		if (l == 2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		if (l == 3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? blockTop : side == 0 ? Block.planks.getBlockTextureFromSide(side) : side != meta ? blockSide : blockFront;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.WORK_TABLE_NAME, true) + "top");
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.WORK_TABLE_NAME, true) + "side");
		blockFront = reg.registerIcon(Utils.getBlockTexture(Strings.WORK_TABLE_NAME, true) + "front");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityWorkTable tile = (TileEntityWorkTable) world.getBlockTileEntity(x, y, z);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.WORK_TABLE, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityWorkTable tile = (TileEntityWorkTable) world.getBlockTileEntity(x, y, z);
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
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityWorkTable();
	}
}
