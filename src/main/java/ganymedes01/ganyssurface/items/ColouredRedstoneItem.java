package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.blocks.ColouredRedstone;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.BlockColored;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ColouredRedstoneItem extends Item implements IConfigurable {

	public ColouredRedstoneItem() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setTextureName(Utils.getItemTexture(Strings.COLOURED_REDSTONE_ITEM_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.COLOURED_REDSTONE_ITEM_NAME));
		setCreativeTab(GanysSurface.enableColouredRedstone ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		float[] colour = EntitySheep.fleeceColorTable[BlockColored.func_150031_c(stack.getItemDamage())];
		return Utils.getColour((int) (colour[0] * 255), (int) (colour[1] * 255), (int) (colour[2] * 255));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();
		if (meta >= ColouredRedstone.COLOURS.length)
			meta = ColouredRedstone.COLOURS.length - 1;
		if (meta < 0)
			meta = 0;
		return super.getUnlocalizedName() + ColouredRedstone.COLOURS[meta].toUpperCase();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.getBlock(x, y, z) != Blocks.snow) {
			if (side == 0)
				y--;
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

			if (!world.isAirBlock(x, y, z))
				return false;
		}

		if (!player.canPlayerEdit(x, y, z, side, stack))
			return false;
		else {
			if (ModBlocks.colouredRedstone[stack.getItemDamage()].canPlaceBlockAt(world, x, y, z)) {
				stack.stackSize--;
				world.setBlock(x, y, z, ModBlocks.colouredRedstone[stack.getItemDamage()]);
			}

			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (int i = 0; i < ColouredRedstone.COLOURS.length; i++)
			if (i != 1) // Skip Red
				list.add(new ItemStack(item, 1, i));
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableColouredRedstone;
	}
}