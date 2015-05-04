package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemWoodSign;
import ganymedes01.ganyssurface.tileentities.TileEntityWoodSign;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

public class BlockWoodSign extends BlockSign implements ISubBlocksBlock, IConfigurable {

	public final int woodMeta;

	public BlockWoodSign(int meta) {
		super(TileEntityWoodSign.class, true);
		woodMeta = meta;
		disableStats();
		setHardness(1.0F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName("sign" + meta));
		setCreativeTab(GanysSurface.enableWoodenSigns ? GanysSurface.surfaceTab : null);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(side, woodMeta);
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemWoodSign.class;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntityWoodSign tile = Utils.getTileEntity(world, x, y, z, TileEntityWoodSign.class);
		if (tile == null)
			return;

		if (!tile.isStanding) {
			int meta = world.getBlockMetadata(x, y, z);
			float f = 0.28125F;
			float f1 = 0.78125F;
			float f2 = 0.0F;
			float f3 = 1.0F;
			float f4 = 0.125F;
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

			if (meta == 2)
				setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
			if (meta == 3)
				setBlockBounds(f2, f, 0.0F, f3, f1, f4);
			if (meta == 4)
				setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
			if (meta == 5)
				setBlockBounds(0.0F, f, f2, f4, f1, f3);
		} else {
			float f = 0.25F;
			float f1 = 1.0F;
			setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		TileEntityWoodSign tile = Utils.getTileEntity(world, x, y, z, TileEntityWoodSign.class);
		if (tile == null)
			return;

		boolean flag = false;
		if (tile.isStanding) {
			if (!world.getBlock(x, y - 1, z).getMaterial().isSolid())
				flag = true;
		} else {
			int meta = world.getBlockMetadata(x, y, z);
			flag = true;
			if (meta == 2 && world.getBlock(x, y, z + 1).getMaterial().isSolid())
				flag = false;
			if (meta == 3 && world.getBlock(x, y, z - 1).getMaterial().isSolid())
				flag = false;
			if (meta == 4 && world.getBlock(x + 1, y, z).getMaterial().isSolid())
				flag = false;
			if (meta == 5 && world.getBlock(x - 1, y, z).getMaterial().isSolid())
				flag = false;
		}

		if (flag) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}

		super.onNeighborBlockChange(world, x, y, z, neighbour);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return !GanysSurface.enable3DRendering ? Utils.getBlockTexture("sign_" + BlockWoodDoor.names[woodMeta]) : null;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWoodenSigns;
	}
}