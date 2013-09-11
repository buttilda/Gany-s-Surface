package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class WoodenWrench extends Item {

	public WoodenWrench(int id) {
		super(id);
		setFull3D();
		setMaxDamage(128);
		setMaxStackSize(1);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.WOODEN_WRENCH_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.WOODEN_WRENCH_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (Block.blocksList[world.getBlockId(x, y, z)] instanceof BlockRotatedPillar) {
			int meta = world.getBlockMetadata(x, y, z);
			switch (meta) {
				case 0:
					meta = 4;
					break;
				case 1:
					meta = 5;
					break;
				case 2:
					meta = 6;
					break;
				case 3:
					meta = 7;
					break;
				case 4:
					meta = 8;
					break;
				case 5:
					meta = 9;
					break;
				case 6:
					meta = 10;
					break;
				case 7:
					meta = 11;
					break;
				case 8:
					meta = 0;
					break;
				case 9:
					meta = 1;
					break;
				case 10:
					meta = 2;
					break;
				case 11:
					meta = 3;
					break;
			}
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			stack.damageItem(1, player);
			return true;
		} else if (Block.blocksList[world.getBlockId(x, y, z)] instanceof BlockQuartz) {
			int meta = world.getBlockMetadata(x, y, z);
			if (!(meta == 0 || meta == 1))
				if (meta == 4)
					meta = 2;
				else
					meta++;
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			stack.damageItem(1, player);
			return true;
		}
		return false;
	}
}
