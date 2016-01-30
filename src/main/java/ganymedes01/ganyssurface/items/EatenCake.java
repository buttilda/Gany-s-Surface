package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class EatenCake extends Item implements IConfigurable {

	private final Block cake = Blocks.cake;

	public EatenCake() {
		setCreativeTab(null);
		setUnlocalizedName(Utils.getUnlocalisedName("eatenCake"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);

		if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
			side = 1;
		else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;
		}

		if (!player.canPlayerEdit(x, y, z, side, stack))
			return false;
		else if (stack.stackSize == 0)
			return false;
		else {
			if (world.canPlaceEntityOnSide(cake, x, y, z, false, side, null, stack)) {
				int meta = cake.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, stack.getItemDamage());

				if (world.setBlock(x, y, z, cake, meta, 3)) {
					if (world.getBlock(x, y, z) == cake) {
						cake.onBlockPlacedBy(world, x, y, z, player, stack);
						cake.onPostBlockPlaced(world, x, y, z, meta);
					}
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, cake.stepSound.func_150496_b(), (cake.stepSound.getVolume() + 1.0F) / 2.0F, cake.stepSound.getPitch() * 0.8F);
					stack.stackSize--;
				}
			}

			return true;
		}
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableEatenCake;
	}
}