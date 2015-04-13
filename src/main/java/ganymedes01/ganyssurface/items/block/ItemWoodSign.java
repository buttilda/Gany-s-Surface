package ganymedes01.ganyssurface.items.block;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.tileentities.TileEntityWoodSign;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ItemWoodSign extends ItemBlock {

	public ItemWoodSign(Block block) {
		super(block);
		setMaxStackSize(16);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side == 0)
			return false;
		else if (!world.getBlock(x, y, z).getMaterial().isSolid())
			return false;
		else {
			if (side == 1)
				y++;
			if (side == 2)
				z--;
			if (side == 3)
				z++;
			if (side == 4)
				x--;
			if (side == 5)
				x++;

			if (!player.canPlayerEdit(x, y, z, side, stack))
				return false;
			else if (!field_150939_a.canPlaceBlockAt(world, x, y, z))
				return false;
			else {
				if (side == 1) {
					int meta = MathHelper.floor_double((player.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 15;
					world.setBlock(x, y, z, field_150939_a, meta, 3);
				} else
					world.setBlock(x, y, z, field_150939_a, side, 3);

				stack.stackSize--;
				TileEntityWoodSign sign = (TileEntityWoodSign) world.getTileEntity(x, y, z);
				if (sign != null) {
					sign.isStanding = side == 1;
					if (world.isRemote)
						player.openGui(GanysSurface.instance, GUIsID.WOOD_SIGN, world, x, y, z);
					sign.func_145912_a(player);
				}
				return true;
			}
		}
	}
}