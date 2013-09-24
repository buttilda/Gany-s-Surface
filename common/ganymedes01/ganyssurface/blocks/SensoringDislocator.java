package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntitySensoringDislocator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class SensoringDislocator extends Dislocator {

	public SensoringDislocator(int id) {
		super(id);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.SENSORING_DISLOCATOR_NAME));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntitySensoringDislocator tile = (TileEntitySensoringDislocator) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int j1 = 0; j1 < tile.getSizeInventory(); ++j1) {
				ItemStack stack = tile.getStackInSlot(j1);
				if (stack != null)
					Utils.dropStack(world, x, y, z, stack);
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
			if (player.getCurrentEquippedItem() != null)
				if (player.getCurrentEquippedItem().getItem() == ModItems.woodenWrench) {
					player.getCurrentEquippedItem().damageItem(1, player);
					return rotate(world, x, y, z, player, side);
				}

			TileEntitySensoringDislocator tile = (TileEntitySensoringDislocator) world.getBlockTileEntity(x, y, z);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.BLOCK_DETECTOR, world, x, y, z);
			return true;
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		if (world.isRemote)
			return;
		doBreak(world, x, y, z);
	}

	public void doBreak(World world, int x, int y, int z) {
		TileEntitySensoringDislocator tile = (TileEntitySensoringDislocator) world.getBlockTileEntity(x, y, z);
		if (tile == null)
			return;
		if (tile.checkNearbyBlocks())
			breakSurroundingBlock(world, x, y, z, getDirectionFromMetadata(world.getBlockMetadata(x, y, z)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.SENSORING_DISLOCATOR_NAME, true) + "side");
		blockFront = reg.registerIcon(Utils.getBlockTexture(Strings.SENSORING_DISLOCATOR_NAME, true) + "front");
		blockBack = reg.registerIcon(Utils.getBlockTexture(Strings.SENSORING_DISLOCATOR_NAME, true) + "back");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySensoringDislocator();
	}
}