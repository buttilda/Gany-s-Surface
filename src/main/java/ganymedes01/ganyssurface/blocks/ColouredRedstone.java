package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.RenderIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ColouredRedstone extends Block implements IConfigurable {

	public static final String[] COLOURS = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "lightGrey", "grey", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };

	private boolean wiresProvidePower = true;
	private final Set<ChunkPosition> blocksNeedingUpdate = new HashSet<ChunkPosition>();
	@SideOnly(Side.CLIENT)
	public IIcon cross, line, cross_overlay, line_overlay;
	private final int colourIndex;

	public ColouredRedstone(int index) {
		super(Material.circuits);
		colourIndex = index;
		disableStats();
		setBlockTextureName("redstone_dust");
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		setBlockName(Utils.getUnlocalisedName(Strings.COLOURED_REDSTONE_NAME[index]));
	}

	private void notifyWireNeighborsOfNeighborChange(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z) == this) {
			world.notifyBlocksOfNeighborChange(x, y, z, this);
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				world.notifyBlocksOfNeighborChange(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this);
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);

		if (!world.isRemote) {
			updateAndPropagateCurrentStrength(world, x, y, z);
			world.notifyBlocksOfNeighborChange(x, y + 1, z, this);
			world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
			notifyWireNeighborsOfNeighborChange(world, x - 1, y, z);
			notifyWireNeighborsOfNeighborChange(world, x + 1, y, z);
			notifyWireNeighborsOfNeighborChange(world, x, y, z - 1);
			notifyWireNeighborsOfNeighborChange(world, x, y, z + 1);

			if (world.getBlock(x - 1, y, z).isNormalCube())
				notifyWireNeighborsOfNeighborChange(world, x - 1, y + 1, z);
			else
				notifyWireNeighborsOfNeighborChange(world, x - 1, y - 1, z);

			if (world.getBlock(x + 1, y, z).isNormalCube())
				notifyWireNeighborsOfNeighborChange(world, x + 1, y + 1, z);
			else
				notifyWireNeighborsOfNeighborChange(world, x + 1, y - 1, z);

			if (world.getBlock(x, y, z - 1).isNormalCube())
				notifyWireNeighborsOfNeighborChange(world, x, y + 1, z - 1);
			else
				notifyWireNeighborsOfNeighborChange(world, x, y - 1, z - 1);

			if (world.getBlock(x, y, z + 1).isNormalCube())
				notifyWireNeighborsOfNeighborChange(world, x, y + 1, z + 1);
			else
				notifyWireNeighborsOfNeighborChange(world, x, y - 1, z + 1);
		}
	}

	private int getMaxCurrentStrength(World world, int x, int y, int z, int strenght) {
		if (world.getBlock(x, y, z) != this)
			return strenght;
		else {
			int meta = world.getBlockMetadata(x, y, z);
			return meta > strenght ? meta : strenght;
		}
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
		return !wiresProvidePower ? 0 : isProvidingWeakPower(world, x, y, z, side);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		if (!wiresProvidePower)
			return 0;
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0)
			return 0;
		else if (side == 1)
			return meta;
		else {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side].getOpposite();
			Block id = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (id == Blocks.redstone_wire || id instanceof ColouredRedstone && id != this)
				return 0;
			else if (id instanceof BlockRedstoneWire)
				return meta;

			boolean flag = isPoweredOrRepeater(world, x - 1, y, z, 1, this) || !world.getBlock(x - 1, y, z).isNormalCube() && isPoweredOrRepeater(world, x - 1, y - 1, z, -1, this);
			boolean flag1 = isPoweredOrRepeater(world, x + 1, y, z, 3, this) || !world.getBlock(x + 1, y, z).isNormalCube() && isPoweredOrRepeater(world, x + 1, y - 1, z, -1, this);
			boolean flag2 = isPoweredOrRepeater(world, x, y, z - 1, 2, this) || !world.getBlock(x, y, z - 1).isNormalCube() && isPoweredOrRepeater(world, x, y - 1, z - 1, -1, this);
			boolean flag3 = isPoweredOrRepeater(world, x, y, z + 1, 0, this) || !world.getBlock(x, y, z + 1).isNormalCube() && isPoweredOrRepeater(world, x, y - 1, z + 1, -1, this);

			if (!world.getBlock(x, y + 1, z).isNormalCube()) {
				if (world.getBlock(x - 1, y, z).isNormalCube() && isPoweredOrRepeater(world, x - 1, y + 1, z, -1, this))
					flag = true;
				if (world.getBlock(x + 1, y, z).isNormalCube() && isPoweredOrRepeater(world, x + 1, y + 1, z, -1, this))
					flag1 = true;
				if (world.getBlock(x, y, z - 1).isNormalCube() && isPoweredOrRepeater(world, x, y + 1, z - 1, -1, this))
					flag2 = true;
				if (world.getBlock(x, y, z + 1).isNormalCube() && isPoweredOrRepeater(world, x, y + 1, z + 1, -1, this))
					flag3 = true;
			}
			return !flag2 && !flag1 && !flag && !flag3 && side >= 2 && side <= 5 ? meta : side == 2 && flag2 && !flag && !flag1 ? meta : side == 3 && flag3 && !flag && !flag1 ? meta : side == 4 && flag && !flag2 && !flag3 ? meta : side == 5 && flag1 && !flag2 && !flag3 ? meta : 0;
		}
	}

	@Override
	public boolean canProvidePower() {
		return wiresProvidePower;
	}

	private ForgeDirection getDirFromSide(int side) {
		switch (side) {
			case 0:
				return ForgeDirection.NORTH;
			case 1:
				return ForgeDirection.EAST;
			case 2:
				return ForgeDirection.SOUTH;
			case 3:
				return ForgeDirection.WEST;
			default:
				return ForgeDirection.UP;
		}
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		ForgeDirection dir = getDirFromSide(side);
		Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		if (block == Blocks.redstone_wire || block == Blocks.redstone_block || block instanceof ColouredRedstone && block != this)
			return false;
		return wiresProvidePower && side != -1;
	}

	public static boolean isPowerProviderOrWire(IBlockAccess world, int x, int y, int z, int side, Block block) {
		Block b = world.getBlock(x, y, z);

		if (b == block)
			return true;
		else if (b == Blocks.air || b == Blocks.redstone_wire || b == Blocks.redstone_block || b instanceof ColouredRedstone && b != block)
			return false;
		else if (!BlockRedstoneDiode.isRedstoneRepeaterBlockID(b))
			return b != null && b.canConnectRedstone(world, x, y, z, side);
		else {
			int meta = world.getBlockMetadata(x, y, z);
			return side == (meta & 3) || side == Direction.rotateOpposite[meta & 3];
		}
	}

	public static boolean isPoweredOrRepeater(IBlockAccess world, int x, int y, int z, int side, Block block) {
		if (isPowerProviderOrWire(world, x, y, z, side, block))
			return true;
		else if (world.getBlock(x, y, z) == Blocks.powered_repeater) {
			int meta = world.getBlockMetadata(x, y, z);
			return side == (meta & 3);
		} else
			return false;
	}

	private void updateAndPropagateCurrentStrength(World world, int x, int y, int z) {
		calculateCurrentChanges(world, x, y, z, x, y, z);
		ArrayList<ChunkPosition> arraylist = new ArrayList<ChunkPosition>(blocksNeedingUpdate);
		blocksNeedingUpdate.clear();

		for (int i = 0; i < arraylist.size(); i++) {
			ChunkPosition pos = arraylist.get(i);
			world.notifyBlocksOfNeighborChange(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, this);
		}
	}

	private int getStrongestIndirectPower(World world, int x, int y, int z) {
		int power = 0;

		for (int i = 0; i < 6; i++) {
			Block block = world.getBlock(x + Facing.offsetsXForSide[i], y + Facing.offsetsYForSide[i], z + Facing.offsetsZForSide[i]);
			if (block != Blocks.redstone_block && block != Blocks.redstone_wire) {
				int indirectPower = world.getIndirectPowerLevelTo(x + Facing.offsetsXForSide[i], y + Facing.offsetsYForSide[i], z + Facing.offsetsZForSide[i], i);

				if (indirectPower >= 15)
					return 15;
				if (indirectPower > power)
					power = indirectPower;
			}
		}
		return power;
	}

	private void calculateCurrentChanges(World world, int x, int y, int z, int otherX, int otherY, int otherZ) {
		int meta = world.getBlockMetadata(x, y, z);
		int strength = getMaxCurrentStrength(world, otherX, otherY, otherZ, 0);
		wiresProvidePower = false;
		int strongestPower = getStrongestIndirectPower(world, x, y, z);
		wiresProvidePower = true;

		if (strongestPower > 0 && strongestPower > strength - 1)
			strength = strongestPower;

		int maxStrength = 0;

		for (int i = 0; i < 4; i++) {
			int newX = x;
			int newZ = z;

			if (i == 0)
				newX--;
			if (i == 1)
				newX++;
			if (i == 2)
				newZ--;
			if (i == 3)
				newZ++;

			if (newX != otherX || newZ != otherZ)
				maxStrength = getMaxCurrentStrength(world, newX, y, newZ, maxStrength);

			if (world.getBlock(newX, y, newZ).isNormalCube() && !world.getBlock(x, y + 1, z).isNormalCube()) {
				if ((newX != otherX || newZ != otherZ) && y >= otherY)
					maxStrength = getMaxCurrentStrength(world, newX, y + 1, newZ, maxStrength);
			} else if (!world.getBlock(newX, y, newZ).isNormalCube() && (newX != otherX || newZ != otherZ) && y <= otherY)
				maxStrength = getMaxCurrentStrength(world, newX, y - 1, newZ, maxStrength);
		}

		if (maxStrength > strength)
			strength = maxStrength - 1;
		else if (strength > 0)
			strength--;
		else
			strength = 0;

		if (strongestPower > strength - 1)
			strength = strongestPower;

		if (meta != strength) {
			world.setBlockMetadataWithNotify(x, y, z, strength, 2);
			blocksNeedingUpdate.add(new ChunkPosition(x, y, z));
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				blocksNeedingUpdate.add(new ChunkPosition(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ));
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		if (!world.isRemote) {
			if (canPlaceBlockAt(world, x, y, z))
				updateAndPropagateCurrentStrength(world, x, y, z);
			else {
				dropBlockAsItem(world, x, y, z, 0, 0);
				world.setBlockToAir(x, y, z);
			}
			super.onNeighborBlockChange(world, x, y, z, neighbour);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		super.breakBlock(world, x, y, z, block, meta);

		if (!world.isRemote) {
			world.notifyBlocksOfNeighborChange(x, y + 1, z, this);
			world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
			world.notifyBlocksOfNeighborChange(x + 1, y, z, this);
			world.notifyBlocksOfNeighborChange(x - 1, y, z, this);
			world.notifyBlocksOfNeighborChange(x, y, z + 1, this);
			world.notifyBlocksOfNeighborChange(x, y, z - 1, this);
			updateAndPropagateCurrentStrength(world, x, y, z);
			notifyWireNeighborsOfNeighborChange(world, x - 1, y, z);
			notifyWireNeighborsOfNeighborChange(world, x + 1, y, z);
			notifyWireNeighborsOfNeighborChange(world, x, y, z - 1);
			notifyWireNeighborsOfNeighborChange(world, x, y, z + 1);

			if (world.getBlock(x - 1, y, z).isNormalCube())
				notifyWireNeighborsOfNeighborChange(world, x - 1, y + 1, z);
			else
				notifyWireNeighborsOfNeighborChange(world, x - 1, y - 1, z);

			if (world.getBlock(x + 1, y, z).isNormalCube())
				notifyWireNeighborsOfNeighborChange(world, x + 1, y + 1, z);
			else
				notifyWireNeighborsOfNeighborChange(world, x + 1, y - 1, z);

			if (world.getBlock(x, y, z - 1).isNormalCube())
				notifyWireNeighborsOfNeighborChange(world, x, y + 1, z - 1);
			else
				notifyWireNeighborsOfNeighborChange(world, x, y - 1, z - 1);

			if (world.getBlock(x, y, z + 1).isNormalCube())
				notifyWireNeighborsOfNeighborChange(world, x, y + 1, z + 1);
			else
				notifyWireNeighborsOfNeighborChange(world, x, y - 1, z + 1);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) || world.getBlock(x, y - 1, z) == Blocks.glowstone;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.colouredRedstone, 1, colourIndex));
		return list;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return ModItems.colouredRedstone;
	}

	@Override
	public int damageDropped(int meta) {
		return colourIndex;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.COLOURED_REDSTONE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return ModItems.colouredRedstone.getColorFromItemStack(new ItemStack(ModItems.colouredRedstone, 1, colourIndex), 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		cross = reg.registerIcon(getTextureName() + "_" + "cross");
		line = reg.registerIcon(getTextureName() + "_" + "line");
		cross_overlay = reg.registerIcon(getTextureName() + "_" + "cross_overlay");
		line_overlay = reg.registerIcon(getTextureName() + "_" + "line_overlay");
		blockIcon = cross;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);

		if (meta > 0) {
			double d0 = x + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
			double d1 = y + 0.0625F;
			double d2 = z + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;

			float[] colour = EntitySheep.fleeceColorTable[BlockColored.func_150031_c(colourIndex)];
			world.spawnParticle("reddust", d0, d1, d2, colour[0], colour[1], colour[2]);
		}
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableColouredRedstone;
	}
}