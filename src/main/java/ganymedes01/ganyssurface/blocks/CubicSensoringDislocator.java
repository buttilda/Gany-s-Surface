package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityCubicSensoringDislocator;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
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

public class CubicSensoringDislocator extends SensoringDislocator {

	public CubicSensoringDislocator() {
		super();
		setBlockName(Utils.getUnlocalisedName(Strings.CUBIC_SENSORING_DISLOCATOR_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityCubicSensoringDislocator tile = Utils.getTileEntity(world, x, y, z, TileEntityCubicSensoringDislocator.class);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.BLOCK_DETECTOR, world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakSurroundingBlock(World world, int xCoord, int yCoord, int zCoord, ForgeDirection dir) {
		int x = xCoord + dir.offsetX;
		int y = yCoord + dir.offsetY;
		int z = zCoord + dir.offsetZ;
		Block target = world.getBlock(x, y, z);
		if (world.isAirBlock(x, y, z))
			return;

		IInventory inventory = getInventory(world, xCoord, yCoord, zCoord, null);

		if (target != null)
			if (target.getBlockHardness(world, x, y, z) >= 0 && target.getMaterial() != Material.water && target.getMaterial() != Material.lava) {
				if (inventory != null) {
					int meta = world.getBlockMetadata(x, y, z);
					ArrayList<ItemStack> drops = target.getDrops(world, x, y, z, meta, 0);
					ForgeEventFactory.fireBlockHarvesting(drops, world, target, x, y, z, meta, 0, 0, false, Utils.getPlayer(world));
					for (ItemStack stack : drops)
						if (!addStacktoInventory(inventory, stack))
							InventoryUtils.dropStack(world, x, y, z, stack);
				} else
					target.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.playAuxSFXAtEntity(null, 2001, x, y, z, Block.getIdFromBlock(target) + (world.getBlockMetadata(x, y, z) << 12));
				world.setBlockToAir(x, y, z);
			}
	}

	@Override
	protected IInventory getInventory(World world, int x, int y, int z, ForgeDirection dir) {
		for (int num : Utils.getRandomizedList(0, 6)) {
			ForgeDirection direction = getDirectionFromMetadata(num);
			IInventory invt = Utils.getTileEntity(world, x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, IInventory.class);
			if (invt != null)
				return invt;
		}
		return null;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return blockFront;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockFront = reg.registerIcon(Utils.getBlockTexture(Strings.CUBIC_SENSORING_DISLOCATOR_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCubicSensoringDislocator();
	}
}