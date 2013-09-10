package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityDislocator;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
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

public class Dislocator extends BlockContainer {

	@SideOnly(Side.CLIENT)
	protected Icon blockSide, blockFront, blockBack;

	public Dislocator(int id) {
		super(id, Material.cloth);
		setHardness(0.2F);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.DISLOCATOR_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.getCurrentEquippedItem() != null)
			if (player.getCurrentEquippedItem().getItem() == ModItems.woodenWrench) {
				player.getCurrentEquippedItem().damageItem(1, player);
				return rotate(world, x, y, z, player, side);
			}
		return false;
	}

	protected boolean rotate(World world, int x, int y, int z, EntityPlayer player, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		int newMeta = meta;
		if (meta == 5)
			newMeta = 0;
		else
			newMeta = meta + 1;

		world.notifyBlocksOfNeighborChange(x, y, z, blockID);
		world.setBlockMetadataWithNotify(x, y, z, newMeta, 2);
		return true;
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
				int meta = world.getBlockMetadata(x, y, z);
				switch (meta) {
					case 0:
						breakSurroundingBlock(world, x, y + 1, z, meta);
						break;
					case 1:
						breakSurroundingBlock(world, x, y - 1, z, meta);
						break;
					case 2:
						breakSurroundingBlock(world, x, y, z - 1, meta);
						break;
					case 3:
						breakSurroundingBlock(world, x, y, z + 1, meta);
						break;
					case 4:
						breakSurroundingBlock(world, x - 1, y, z, meta);
						break;
					case 5:
						breakSurroundingBlock(world, x + 1, y, z, meta);
						break;
				}
			}
	}

	protected void breakSurroundingBlock(World world, int x, int y, int z, int meta) {
		Block target = Block.blocksList[world.getBlockId(x, y, z)];
		if (target != null)
			if (target.getBlockHardness(world, x, y, z) >= 0 && target.blockMaterial != Material.water && target.blockMaterial != Material.lava) {
				IInventory tile = getInventory(world, x, y, z, meta);
				IPipeTile pipe = getPipe(world, x, y, z, meta);
				if (tile != null) {
					for (ItemStack stack : target.getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
						if (!addStacktoInventory(tile, stack))
							Utils.dropStack(world, x, y, z, stack);
				} else if (pipe != null) {
					for (ItemStack stack : target.getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
						if (!addStackToPipe(pipe, stack, meta))
							Utils.dropStack(world, x, y, z, stack);
				} else
					target.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
				world.setBlockToAir(x, y, z);
			}
	}

	protected boolean addStackToPipe(IPipeTile pipe, ItemStack stack, int meta) {

		switch (meta) {
			case 0:
				return pipe.injectItem(stack, true, ForgeDirection.UP) == stack.stackSize;
			case 1:
				return pipe.injectItem(stack, true, ForgeDirection.DOWN) == stack.stackSize;
			case 2:
				return pipe.injectItem(stack, true, ForgeDirection.NORTH) == stack.stackSize;
			case 3:
				return pipe.injectItem(stack, true, ForgeDirection.SOUTH) == stack.stackSize;
			case 4:
				return pipe.injectItem(stack, true, ForgeDirection.WEST) == stack.stackSize;
			case 5:
				return pipe.injectItem(stack, true, ForgeDirection.EAST) == stack.stackSize;
			default:
				return false;
		}
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

	protected IPipeTile getPipe(World world, int x, int y, int z, int meta) {
		int i = 0, j = 0, k = 0;

		switch (meta) {
			case 0:
				i = x;
				j = y - 2;
				k = z;
				break;
			case 1:
				i = x;
				j = y + 2;
				k = z;
				break;
			case 2:
				i = x;
				j = y;
				k = z + 2;
				break;
			case 3:
				i = x;
				j = y;
				k = z - 2;
				break;
			case 4:
				i = x + 2;
				j = y;
				k = z;
				break;
			case 5:
				i = x - 2;
				j = y;
				k = z;
				break;
		}
		TileEntity tile = world.getBlockTileEntity(i, j, k);
		if (tile instanceof IPipeTile)
			if (((IPipeTile) tile).getPipeType() == PipeType.ITEM)
				return (IPipeTile) world.getBlockTileEntity(i, j, k);
		return null;
	}

	protected IInventory getInventory(World world, int x, int y, int z, int meta) {
		int i = 0, j = 0, k = 0;

		switch (meta) {
			case 0:
				i = x;
				j = y - 2;
				k = z;
				break;
			case 1:
				i = x;
				j = y + 2;
				k = z;
				break;
			case 2:
				i = x;
				j = y;
				k = z + 2;
				break;
			case 3:
				i = x;
				j = y;
				k = z - 2;
				break;
			case 4:
				i = x + 2;
				j = y;
				k = z;
				break;
			case 5:
				i = x - 2;
				j = y;
				k = z;
				break;
		}
		if (world.getBlockTileEntity(i, j, k) instanceof IInventory)
			return (IInventory) world.getBlockTileEntity(i, j, k);
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
