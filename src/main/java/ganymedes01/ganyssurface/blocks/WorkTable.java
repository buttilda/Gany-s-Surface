package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityWorkTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
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

public class WorkTable extends BlockContainer implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon blockTop, blockSide, blockFront;

	public WorkTable() {
		super(Material.wood);
		setHardness(2.5F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName(Strings.WORK_TABLE_NAME));
		setCreativeTab(GanysSurface.enableWorkTables ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int rotation = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (rotation == 0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if (rotation == 1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if (rotation == 2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if (rotation == 3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? blockTop : side == 0 ? Blocks.planks.getBlockTextureFromSide(side) : side != meta ? blockSide : blockFront;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.WORK_TABLE_NAME) + "_top");
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.WORK_TABLE_NAME) + "_side");
		blockFront = reg.registerIcon(Utils.getBlockTexture(Strings.WORK_TABLE_NAME) + "_front");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityWorkTable tile = Utils.getTileEntity(world, x, y, z, TileEntityWorkTable.class);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.WORK_TABLE, world, x, y, z);
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
		return new TileEntityWorkTable();
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWorkTables;
	}
}