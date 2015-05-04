package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.blocks.BlockWoodSign;
import ganymedes01.ganyssurface.blocks.CubicSensoringDislocator;
import ganymedes01.ganyssurface.blocks.Dislocator;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class WoodenWrench extends Item implements IConfigurable {

	public WoodenWrench() {
		setFull3D();
		setMaxStackSize(1);
		setTextureName(Utils.getItemTexture(Strings.WOODEN_WRENCH_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.WOODEN_WRENCH_NAME));
		setCreativeTab(GanysSurface.enableWoodenWrench ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(StatCollector.translateToLocal("interacttorotate"));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);
		if (block instanceof BlockRotatedPillar) {
			int meta = world.getBlockMetadata(x, y, z);
			switch (meta) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
					meta += 4;
					break;
				case 8:
				case 9:
				case 10:
				case 11:
					meta -= 8;
					break;
			}
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			return true;
		} else if (block instanceof BlockQuartz) {
			int meta = world.getBlockMetadata(x, y, z);
			if (!(meta == 0 || meta == 1))
				if (meta == 4)
					meta = 2;
				else
					meta++;
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			return true;
		} else if (block instanceof Dislocator && !(block instanceof CubicSensoringDislocator)) {
			int meta = world.getBlockMetadata(x, y, z);
			if (meta == 5)
				meta = 0;
			else
				meta = meta + 1;
			world.notifyBlocksOfNeighborChange(x, y, z, block);
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			return true;
		} else if (block == Blocks.standing_sign || block == Blocks.wall_sign) {
			TileEntitySign tile = Utils.getTileEntity(world, x, y, z, TileEntitySign.class);
			if (tile != null && !world.isRemote)
				player.func_146100_a(tile);
			return true;
		} else if (block instanceof BlockWoodSign)
			if (!world.isRemote)
				player.openGui(GanysSurface.instance, GUIsID.WOOD_SIGN, world, x, y, z);
		return false;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWoodenWrench;
	}
}