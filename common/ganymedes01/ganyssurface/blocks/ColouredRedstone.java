package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.ParticleEffectsID;
import ganymedes01.ganyssurface.lib.RenderIDs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneLogic;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ColouredRedstone extends Block {

	public static final String[] COLOURS = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "lightGrey", "grey", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };

	private boolean wiresProvidePower = true;
	private Set blocksNeedingUpdate = new HashSet();
	@SideOnly(Side.CLIENT)
	public Icon cross, line, cross_overlay, line_overlay;
	private final int colourIndex;

	public ColouredRedstone(int id, int index) {
		super(id, Material.circuits);
		this.colourIndex = index;
		disableStats();
		setTextureName("redstone_dust");
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}

	private void notifyWireNeighborsOfNeighborChange(World world, int x, int y, int z) {
		if (world.getBlockId(x, y, z) == blockID) {
			world.notifyBlocksOfNeighborChange(x, y, z, blockID);
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				world.notifyBlocksOfNeighborChange(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, blockID);
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);

		if (!world.isRemote) {
			updateAndPropagateCurrentStrength(world, x, y, z);
			world.notifyBlocksOfNeighborChange(x, y + 1, z, blockID);
			world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
			notifyWireNeighborsOfNeighborChange(world, x - 1, y, z);
			notifyWireNeighborsOfNeighborChange(world, x + 1, y, z);
			notifyWireNeighborsOfNeighborChange(world, x, y, z - 1);
			notifyWireNeighborsOfNeighborChange(world, x, y, z + 1);

			if (world.isBlockNormalCube(x - 1, y, z))
				notifyWireNeighborsOfNeighborChange(world, x - 1, y + 1, z);
			else
				notifyWireNeighborsOfNeighborChange(world, x - 1, y - 1, z);

			if (world.isBlockNormalCube(x + 1, y, z))
				notifyWireNeighborsOfNeighborChange(world, x + 1, y + 1, z);
			else
				notifyWireNeighborsOfNeighborChange(world, x + 1, y - 1, z);

			if (world.isBlockNormalCube(x, y, z - 1))
				notifyWireNeighborsOfNeighborChange(world, x, y + 1, z - 1);
			else
				notifyWireNeighborsOfNeighborChange(world, x, y - 1, z - 1);

			if (world.isBlockNormalCube(x, y, z + 1))
				notifyWireNeighborsOfNeighborChange(world, x, y + 1, z + 1);
			else
				notifyWireNeighborsOfNeighborChange(world, x, y - 1, z + 1);
		}
	}

	private int getMaxCurrentStrength(World world, int x, int y, int z, int strenght) {
		if (world.getBlockId(x, y, z) != blockID)
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
			int id = world.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (id == Block.redstoneWire.blockID || Block.blocksList[id] instanceof ColouredRedstone && id != blockID)
				return 0;
			else if (Block.blocksList[id] instanceof BlockRedstoneLogic)
				return meta;

			boolean flag = isPoweredOrRepeater(world, x - 1, y, z, 1, blockID) || !world.isBlockNormalCube(x - 1, y, z) && isPoweredOrRepeater(world, x - 1, y - 1, z, -1, blockID);
			boolean flag1 = isPoweredOrRepeater(world, x + 1, y, z, 3, blockID) || !world.isBlockNormalCube(x + 1, y, z) && isPoweredOrRepeater(world, x + 1, y - 1, z, -1, blockID);
			boolean flag2 = isPoweredOrRepeater(world, x, y, z - 1, 2, blockID) || !world.isBlockNormalCube(x, y, z - 1) && isPoweredOrRepeater(world, x, y - 1, z - 1, -1, blockID);
			boolean flag3 = isPoweredOrRepeater(world, x, y, z + 1, 0, blockID) || !world.isBlockNormalCube(x, y, z + 1) && isPoweredOrRepeater(world, x, y - 1, z + 1, -1, blockID);

			if (!world.isBlockNormalCube(x, y + 1, z)) {
				if (world.isBlockNormalCube(x - 1, y, z) && isPoweredOrRepeater(world, x - 1, y + 1, z, -1, blockID))
					flag = true;
				if (world.isBlockNormalCube(x + 1, y, z) && isPoweredOrRepeater(world, x + 1, y + 1, z, -1, blockID))
					flag1 = true;
				if (world.isBlockNormalCube(x, y, z - 1) && isPoweredOrRepeater(world, x, y + 1, z - 1, -1, blockID))
					flag2 = true;
				if (world.isBlockNormalCube(x, y, z + 1) && isPoweredOrRepeater(world, x, y + 1, z + 1, -1, blockID))
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
		int id = world.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		if (id == Block.redstoneWire.blockID || Block.blocksList[id] instanceof ColouredRedstone && id != blockID)
			return false;
		return wiresProvidePower && side != -1;
	}

	public static boolean isPowerProviderOrWire(IBlockAccess world, int x, int y, int z, int side, int blockID) {
		int id = world.getBlockId(x, y, z);

		if (id == blockID)
			return true;
		else if (id == 0 || id == Block.redstoneWire.blockID || Block.blocksList[id] instanceof ColouredRedstone && id != blockID)
			return false;
		else if (!Block.redstoneRepeaterIdle.func_94487_f(id))
			return Block.blocksList[id] != null && Block.blocksList[id].canConnectRedstone(world, x, y, z, side);
		else {
			int meta = world.getBlockMetadata(x, y, z);
			return side == (meta & 3) || side == Direction.rotateOpposite[meta & 3];
		}
	}

	public static boolean isPoweredOrRepeater(IBlockAccess world, int x, int y, int z, int side, int blockID) {
		if (isPowerProviderOrWire(world, x, y, z, side, blockID))
			return true;
		else {
			int id = world.getBlockId(x, y, z);

			if (id == Block.redstoneRepeaterActive.blockID) {
				int meta = world.getBlockMetadata(x, y, z);
				return side == (meta & 3);
			} else
				return false;
		}
	}

	private void updateAndPropagateCurrentStrength(World world, int x, int y, int z) {
		calculateCurrentChanges(world, x, y, z, x, y, z);
		ArrayList arraylist = new ArrayList(blocksNeedingUpdate);
		blocksNeedingUpdate.clear();

		for (int i = 0; i < arraylist.size(); i++) {
			ChunkPosition chunkposition = (ChunkPosition) arraylist.get(i);
			world.notifyBlocksOfNeighborChange(chunkposition.x, chunkposition.y, chunkposition.z, blockID);
		}
	}

	private void calculateCurrentChanges(World world, int x, int y, int z, int otherX, int otherY, int otherZ) {
		int meta = world.getBlockMetadata(x, y, z);
		int strength = getMaxCurrentStrength(world, otherX, otherY, otherZ, 0);
		wiresProvidePower = false;
		int strongestPower = world.getStrongestIndirectPower(x, y, z);
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

			if (world.isBlockNormalCube(newX, y, newZ) && !world.isBlockNormalCube(x, y + 1, z)) {
				if ((newX != otherX || newZ != otherZ) && y >= otherY)
					maxStrength = getMaxCurrentStrength(world, newX, y + 1, newZ, maxStrength);
			} else if (!world.isBlockNormalCube(newX, y, newZ) && (newX != otherX || newZ != otherZ) && y <= otherY)
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
	public void onNeighborBlockChange(World world, int x, int y, int z, int side) {
		if (!world.isRemote) {
			if (canPlaceBlockAt(world, x, y, z))
				updateAndPropagateCurrentStrength(world, x, y, z);
			else {
				dropBlockAsItem(world, x, y, z, 0, 0);
				world.setBlockToAir(x, y, z);
			}
			super.onNeighborBlockChange(world, x, y, z, side);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		super.breakBlock(world, x, y, z, par5, par6);

		if (!world.isRemote) {
			world.notifyBlocksOfNeighborChange(x, y + 1, z, blockID);
			world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
			world.notifyBlocksOfNeighborChange(x + 1, y, z, blockID);
			world.notifyBlocksOfNeighborChange(x - 1, y, z, blockID);
			world.notifyBlocksOfNeighborChange(x, y, z + 1, blockID);
			world.notifyBlocksOfNeighborChange(x, y, z - 1, blockID);
			updateAndPropagateCurrentStrength(world, x, y, z);
			notifyWireNeighborsOfNeighborChange(world, x - 1, y, z);
			notifyWireNeighborsOfNeighborChange(world, x + 1, y, z);
			notifyWireNeighborsOfNeighborChange(world, x, y, z - 1);
			notifyWireNeighborsOfNeighborChange(world, x, y, z + 1);

			if (world.isBlockNormalCube(x - 1, y, z))
				notifyWireNeighborsOfNeighborChange(world, x - 1, y + 1, z);
			else
				notifyWireNeighborsOfNeighborChange(world, x - 1, y - 1, z);

			if (world.isBlockNormalCube(x + 1, y, z))
				notifyWireNeighborsOfNeighborChange(world, x + 1, y + 1, z);
			else
				notifyWireNeighborsOfNeighborChange(world, x + 1, y - 1, z);

			if (world.isBlockNormalCube(x, y, z - 1))
				notifyWireNeighborsOfNeighborChange(world, x, y + 1, z - 1);
			else
				notifyWireNeighborsOfNeighborChange(world, x, y - 1, z - 1);

			if (world.isBlockNormalCube(x, y, z + 1))
				notifyWireNeighborsOfNeighborChange(world, x, y + 1, z + 1);
			else
				notifyWireNeighborsOfNeighborChange(world, x, y - 1, z + 1);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return world.doesBlockHaveSolidTopSurface(x, y - 1, z) || world.getBlockId(x, y - 1, z) == Block.glowStone.blockID;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.colouredRedstone, 1, colourIndex));
		return list;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int x, int y, int z) {
		return ModItems.colouredRedstone.itemID;
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
		return RenderIDs.COLOURED_WIRE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return ModItems.colouredRedstone.getColorFromItemStack(new ItemStack(ModItems.colouredRedstone, 1, colourIndex), 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		cross = reg.registerIcon(getTextureName() + "_" + "cross");
		line = reg.registerIcon(getTextureName() + "_" + "line");
		cross_overlay = reg.registerIcon(getTextureName() + "_" + "cross_overlay");
		line_overlay = reg.registerIcon(getTextureName() + "_" + "line_overlay");
		blockIcon = cross;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		GanysSurface.proxy.handleParticleEffects(world, x, y, z, ParticleEffectsID.COLOURED_REDSTONE, colourIndex);
	}
}