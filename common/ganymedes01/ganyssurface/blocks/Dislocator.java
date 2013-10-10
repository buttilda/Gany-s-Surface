package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityDislocator;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Dislocator extends BlockContainer {

	@SideOnly(Side.CLIENT)
	protected Icon blockSide, blockFront, blockBack;

	public Dislocator() {
		this(ModIDs.DISLOCATOR_ID);
	}

	public Dislocator(int id) {
		super(id, Material.cloth);
		setHardness(0.2F);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.DISLOCATOR_NAME));
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
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		if (world.isRemote)
			return;
		TileEntityDislocator tile = (TileEntityDislocator) world.getBlockTileEntity(x, y, z);
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

	protected void breakSurroundingBlock(World world, int xCoord, int yCoord, int zCoord, ForgeDirection dir) {
		int x = xCoord + dir.offsetX;
		int y = yCoord + dir.offsetY;
		int z = zCoord + dir.offsetZ;
		Block target = Block.blocksList[world.getBlockId(x, y, z)];
		if (target != null)
			if (target.getBlockHardness(world, x, y, z) >= 0 && target.blockMaterial != Material.water && target.blockMaterial != Material.lava) {
				IInventory tile = getInventory(world, xCoord, yCoord, zCoord, dir);
				IPipeTile pipe = getPipe(world, xCoord, yCoord, zCoord, dir);
				if (tile != null) {
					for (ItemStack stack : target.getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
						if (!addStacktoInventory(tile, stack))
							Utils.dropStack(world, x, y, z, stack);
				} else if (pipe != null) {
					for (ItemStack stack : target.getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
						if (!addStackToPipe(pipe, stack, dir))
							Utils.dropStack(world, x, y, z, stack);
				} else
					target.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
				world.setBlockToAir(x, y, z);
			}
	}

	protected boolean addStackToPipe(IPipeTile pipe, ItemStack stack, ForgeDirection dir) {
		return pipe.injectItem(stack, true, dir) == stack.stackSize;
	}

	protected boolean addStacktoInventory(IInventory iinventory, ItemStack stack) {
		ArrayList<Integer> slots = getStackSlots(iinventory, stack);
		while (slots.size() > 0 && stack.stackSize > 0) {
			for (Integer slot : slots)
				while (iinventory.getStackInSlot(slot).stackSize < iinventory.getStackInSlot(slot).getMaxStackSize() && stack.stackSize > 0) {
					++iinventory.getStackInSlot(slot).stackSize;
					--stack.stackSize;
				}
			slots = getStackSlots(iinventory, stack);
		}
		if (stack.stackSize <= 0)
			return true;

		for (int i = 0; i < iinventory.getSizeInventory(); i++)
			if (iinventory.getStackInSlot(i) == null) {
				iinventory.setInventorySlotContents(i, stack);
				stack = null;
				return true;
			}
		if (stack != null && stack.stackSize > 0)
			return false;
		else
			return true;
	}

	private static ArrayList<Integer> getStackSlots(IInventory iinventory, ItemStack stack) {
		ArrayList<Integer> slots = new ArrayList<Integer>();
		for (int i = 0; i < iinventory.getSizeInventory(); i++)
			if (iinventory.getStackInSlot(i) != null && stack != null)
				if (iinventory.getStackInSlot(i).getItem() == stack.getItem())
					if (iinventory.getStackInSlot(i).isItemEqual(stack))
						if (iinventory.getStackInSlot(i).stackSize < iinventory.getStackInSlot(i).getMaxStackSize())
							slots.add(i);
		return slots;
	}

	protected IPipeTile getPipe(World world, int x, int y, int z, ForgeDirection dir) {
		ForgeDirection direction = dir.getOpposite();
		TileEntity tile = world.getBlockTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
		if (tile instanceof IPipeTile)
			if (((IPipeTile) tile).getPipeType() == PipeType.ITEM)
				return (IPipeTile) tile;
		return null;
	}

	protected IInventory getInventory(World world, int x, int y, int z, ForgeDirection dir) {
		ForgeDirection direction = dir.getOpposite();
		TileEntity tile = world.getBlockTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
		if (tile instanceof IInventory)
			return (IInventory) tile;
		else
			return null;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if (side == 0)
			return 1;
		if (side == 1)
			return 0;
		return side;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
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
	public void registerIcons(IconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.DISLOCATOR_NAME, true) + "side");
		blockFront = reg.registerIcon(Utils.getBlockTexture(Strings.DISLOCATOR_NAME, true) + "front");
		blockBack = reg.registerIcon(Utils.getBlockTexture(Strings.DISLOCATOR_NAME, true) + "back");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDislocator();
	}
}