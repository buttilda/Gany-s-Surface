package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemBlockGeneric;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
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

public class LeafWall extends Block implements ISubBlocksBlock, IConfigurable {

	public LeafWall() {
		super(Material.leaves);
		setHardness(1.0F);
		setHarvestLevel("axe", 0);
		setStepSound(soundTypeGrass);
		setBlockName(Utils.getUnlocalisedName(Strings.LEAF_WALL));
		setCreativeTab(GanysSurface.enableLeafWalls ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item id, CreativeTabs tab, List list) {
		for (int i = 0; i < 4; i++)
			list.add(new ItemStack(id, 1, i));
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		maxY = 1.5D;
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		boolean north = canConnectWallTo(world, x, y, z - 1);
		boolean south = canConnectWallTo(world, x, y, z + 1);
		boolean west = canConnectWallTo(world, x - 1, y, z);
		boolean east = canConnectWallTo(world, x + 1, y, z);

		float minX = 0.3125F;
		float maxX = 0.6875F;
		float minZ = 0.3125F;
		float maxZ = 0.6875F;

		if (north)
			minZ = 0.0F;
		if (south)
			maxZ = 1.0F;
		if (west)
			minX = 0.0F;
		if (east)
			maxX = 1.0F;

		setBlockBounds(minX, 0.0F, minZ, maxX, 1.0F, maxZ);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		float f = 0.25F;
		float f1 = 0.75F;
		float f2 = 0.25F;
		float f3 = 0.75F;
		setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	public boolean canConnectWallTo(IBlockAccess world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if (block != this && block != Blocks.fence_gate) {
			if (block.getMaterial().isOpaque() && block.renderAsNormalBlock())
				return block.getMaterial() != Material.gourd;
			else
				return block.isLeaves(world, x, y, z);
		} else
			return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.leaves.getIcon(side, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return Blocks.leaves.getRenderColor(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return Blocks.leaves.colorMultiplier(world, x, y, z);
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableLeafWalls;
	}
}