package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityDislocator;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Dislocator extends BlockContainer implements IConfigurable {

	@SideOnly(Side.CLIENT)
	protected IIcon blockSide, blockFront, blockBack;

	public Dislocator() {
		super(Material.cloth);
		setHardness(0.2F);
		setBlockName(Utils.getUnlocalisedName(Strings.DISLOCATOR_NAME));
		setCreativeTab(GanysSurface.enableDislocators ? GanysSurface.surfaceTab : null);
	}

	public static ForgeDirection getDirectionFromMetadata(int meta) {
		switch (meta) {
			case 0:
				return ForgeDirection.UP;
			case 1:
				return ForgeDirection.DOWN;
			case 2:
				return ForgeDirection.NORTH;
			case 3:
				return ForgeDirection.SOUTH;
			case 4:
				return ForgeDirection.WEST;
			case 5:
				return ForgeDirection.EAST;
		}
		return null;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		if (world.isRemote)
			return;
		TileEntityDislocator tile = Utils.getTileEntity(world, x, y, z, TileEntityDislocator.class);
		if (tile == null)
			return;

		if (!world.isBlockIndirectlyGettingPowered(x, y, z))
			tile.activated = false;
		if (!tile.activated)
			if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
				tile.activated = true;
				breakSurroundingBlock(world, x, y, z, getDirectionFromMetadata(world.getBlockMetadata(x, y, z)));
			}
	}

	public void breakSurroundingBlock(World world, int xCoord, int yCoord, int zCoord, ForgeDirection dir) {
		int x = xCoord + dir.offsetX;
		int y = yCoord + dir.offsetY;
		int z = zCoord + dir.offsetZ;
		Block target = world.getBlock(x, y, z);
		if (target.isAir(world, x, y, z))
			return;

		if (target.getBlockHardness(world, x, y, z) >= 0 && target.getMaterial() != Material.water && target.getMaterial() != Material.lava) {
			IInventory inventory = getInventory(world, xCoord, yCoord, zCoord, dir);

			if (inventory != null) {
				int meta = world.getBlockMetadata(x, y, z);
				ArrayList<ItemStack> drops = target.getDrops(world, x, y, z, meta, 0);
				ForgeEventFactory.fireBlockHarvesting(drops, world, target, x, y, z, meta, 0, 0, false, Utils.getPlayer(world));
				for (ItemStack stack : drops)
					if (!InventoryUtils.addStackToInventory(inventory, stack, dir.ordinal()))
						InventoryUtils.dropStack(world, x, y, z, stack);
			} else
				target.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.playAuxSFXAtEntity(null, 2001, x, y, z, Block.getIdFromBlock(target) + (world.getBlockMetadata(x, y, z) << 12));
			world.setBlockToAir(x, y, z);
		}
	}

	protected IInventory getInventory(World world, int x, int y, int z, ForgeDirection dir) {
		ForgeDirection direction = dir.getOpposite();
		return Utils.getTileEntity(world, x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, IInventory.class);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int meta = BlockPistonBase.determineOrientation(world, x, y, z, player);
		if (meta == 0)
			meta = 1;
		else if (meta == 1)
			meta = 0;
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta == 0)
			return side == 1 ? blockFront : side == 0 ? blockBack : blockSide;
		if (meta == 1)
			return side == 0 ? blockFront : side == 1 ? blockBack : blockSide;
		if (meta == 2)
			return side == meta ? blockFront : side == 3 ? blockBack : blockSide;
		if (meta == 4)
			return side == meta ? blockFront : side == 5 ? blockBack : blockSide;
		return side == meta ? blockFront : side == meta - 1 ? blockBack : blockSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.DISLOCATOR_NAME) + "_side");
		blockFront = reg.registerIcon(Utils.getBlockTexture(Strings.DISLOCATOR_NAME) + "_front");
		blockBack = reg.registerIcon(Utils.getBlockTexture(Strings.DISLOCATOR_NAME) + "_back");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDislocator();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableDislocators;
	}
}