package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
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
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class CubicSensoringDislocator extends SensoringDislocator {

	public CubicSensoringDislocator() {
		super(ModIDs.CUBIC_SENSORING_DISLOCATOR_ID);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CUBIC_SENSORING_DISLOCATOR_NAME));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityCubicSensoringDislocator tile = (TileEntityCubicSensoringDislocator) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int i = 0; i < tile.getSizeInventory(); i++) {
				ItemStack itemstack = tile.getStackInSlot(i);

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

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if (tile.checkNearbyBlocks(dir))
				breakSurroundingBlock(world, x, y, z, dir);
	}

	@Override
	protected void breakSurroundingBlock(World world, int xCoord, int yCoord, int zCoord, ForgeDirection dir) {
		int x = xCoord + dir.offsetX;
		int y = yCoord + dir.offsetY;
		int z = zCoord + dir.offsetZ;
		int blockID = world.getBlockId(x, y, z);
		if (world.isAirBlock(x, y, z))
			return;

		IInventory inventory = getInventory(world, xCoord, yCoord, zCoord, null);
		IPipeTile pipe = null;
		ForgeDirection pipeDir = null;
		for (int num : Utils.getRandomizedList(0, 6)) {
			pipeDir = getDirectionFromMetadata(num);
			pipe = getPipe(world, xCoord, yCoord, zCoord, pipeDir);
			if (pipe != null)
				break;
		}

		Block target = Block.blocksList[blockID];
		if (target != null)
			if (target.getBlockHardness(world, x, y, z) >= 0 && target.blockMaterial != Material.water && target.blockMaterial != Material.lava) {
				if (inventory != null) {
					for (ItemStack stack : target.getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
						if (!addStacktoInventory(inventory, stack))
							Utils.dropStack(world, x, y, z, stack);
				} else if (pipe != null) {
					for (ItemStack stack : target.getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0))
						if (!addStackToPipe(pipe, stack, pipeDir))
							Utils.dropStack(world, x, y, z, stack);
				} else
					target.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
				world.setBlockToAir(x, y, z);
			}
	}

	@Override
	protected IInventory getInventory(World world, int x, int y, int z, ForgeDirection dir) {
		for (int num : Utils.getRandomizedList(0, 6)) {
			ForgeDirection direction = getDirectionFromMetadata(num);
			TileEntity tile = world.getBlockTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
			if (tile instanceof IInventory)
				return (IInventory) tile;
		}
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