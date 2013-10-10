package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.CubicSensoringDislocator;
import ganymedes01.ganyssurface.blocks.Dislocator;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
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

	public WoodenWrench() {
		super(ModIDs.WOODEN_WRENCH_ID);
		setFull3D();
		setMaxStackSize(1);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.WOODEN_WRENCH_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.WOODEN_WRENCH_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		int blockID = world.getBlockId(x, y, z);
		if (blockID >= Block.blocksList.length)
			return false;

		Block block = Block.blocksList[blockID];
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
			world.notifyBlocksOfNeighborChange(x, y, z, blockID);
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			return true;
		}
		return false;
	}
}