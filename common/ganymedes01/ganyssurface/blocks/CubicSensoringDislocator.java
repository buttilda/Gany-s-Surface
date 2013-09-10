package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityCubicSensoringDislocator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import buildcraft.api.transport.IPipeTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CubicSensoringDislocator extends SensoringDislocator {

	public CubicSensoringDislocator(int id) {
		super(id);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CUBIC_SENSORING_DISLOCATOR_NAME));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityCubicSensoringDislocator tile = (TileEntityCubicSensoringDislocator) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int j1 = 0; j1 < tile.getSizeInventory(); ++j1) {
				ItemStack itemstack = tile.getStackInSlot(j1);

				if (itemstack != null)
					Utils.dropStack(world, x, y, z, itemstack);
			}
			world.func_96440_m(x, y, z, par5);
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityCubicSensoringDislocator tile = (TileEntityCubicSensoringDislocator) world.getBlockTileEntity(x, y, z);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.BLOCK_DETECTOR, world, x, y, z);
			return true;
		}
	}

	@Override
	public void doBreak(World world, int x, int y, int z) {
		TileEntityCubicSensoringDislocator tile = (TileEntityCubicSensoringDislocator) world.getBlockTileEntity(x, y, z);
		if (tile == null)
			return;

		if (!tile.checkNearbyBlocks())
			tile.activated = false;
		if (!tile.activated)
			if (tile.checkNearbyBlocks()) {
				tile.activated = true;
				IInventory inventory = getInventory(world, x, y, z, 0);
				IPipeTile pipe = getPipe(world, x, y, z, 0);
				if (tile.checkBlock(x + 1, y, z))
					breakSurroundingBlock(pipe, inventory, world, x + 1, y, z, 0);
				if (tile.checkBlock(x - 1, y, z))
					breakSurroundingBlock(pipe, inventory, world, x - 1, y, z, 0);
				if (tile.checkBlock(x, y + 1, z))
					breakSurroundingBlock(pipe, inventory, world, x, y + 1, z, 0);
				if (tile.checkBlock(x, y - 1, z))
					breakSurroundingBlock(pipe, inventory, world, x, y - 1, z, 0);
				if (tile.checkBlock(x, y, z + 1))
					breakSurroundingBlock(pipe, inventory, world, x, y, z + 1, 0);
				if (tile.checkBlock(x, y, z - 1))
					breakSurroundingBlock(pipe, inventory, world, x, y, z - 1, 0);
			}
	}

	private void breakSurroundingBlock(IPipeTile pipe, IInventory tile, World world, int x, int y, int z, int meta) {
		Block target = Block.blocksList[world.getBlockId(x, y, z)];
		if (target != null)
			if (target.getBlockHardness(world, x, y, z) >= 0 && target.blockMaterial != Material.water && target.blockMaterial != Material.lava) {
				if (tile != null) {
					for (ItemStack stack : target.getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
						if (!addStacktoInventory(tile, stack))
							Utils.dropStack(world, x, y, z, stack);
				} else if (pipe != null) {
					for (ItemStack stack : target.getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
						if (!addStackToPipe(pipe, stack, 0))
							if (!addStackToPipe(pipe, stack, 1))
								Utils.dropStack(world, x, y, z, stack);
				} else
					target.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
				world.setBlockToAir(x, y, z);
			}
	}

	@Override
	protected IPipeTile getPipe(World world, int x, int y, int z, int meta) {
		if (world.getBlockTileEntity(x, y + 1, z) instanceof IPipeTile)
			return (IPipeTile) world.getBlockTileEntity(x, y + 1, z);
		else if (world.getBlockTileEntity(x, y - 1, z) instanceof IPipeTile)
			return (IPipeTile) world.getBlockTileEntity(x, y - 1, z);
		else
			return null;
	}

	@Override
	protected IInventory getInventory(World world, int x, int y, int z, int meta) {
		if (world.getBlockTileEntity(x, y + 1, z) instanceof IInventory)
			return (IInventory) world.getBlockTileEntity(x, y + 1, z);
		else if (world.getBlockTileEntity(x, y - 1, z) instanceof IInventory)
			return (IInventory) world.getBlockTileEntity(x, y - 1, z);
		else
			return null;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return blockFront;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockFront = reg.registerIcon(Utils.getBlockTexture(Strings.CUBIC_SENSORING_DISLOCATOR_NAME, false));
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCubicSensoringDislocator();
	}
}
