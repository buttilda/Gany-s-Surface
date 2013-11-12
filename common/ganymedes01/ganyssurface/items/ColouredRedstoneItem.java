package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.ColouredRedstone;
import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
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

public class ColouredRedstoneItem extends Item {

	public ColouredRedstoneItem() {
		super(ModIDs.COLOURED_REDSTONE_ITEM_ID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.COLOURED_REDSTONE_ITEM_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.COLOURED_REDSTONE_ITEM_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		float[] colour = EntitySheep.fleeceColorTable[BlockColored.getBlockFromDye(stack.getItemDamage())];
		return Utils.getColour((int) (colour[0] * 255), (int) (colour[1] * 255), (int) (colour[2] * 255));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + ColouredRedstone.COLOURS[stack.getItemDamage()].toUpperCase();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.getBlockId(x, y, z) != Block.snow.blockID) {
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
				world.setBlock(x, y, z, ModBlocks.colouredRedstone[stack.getItemDamage()].blockID);
			}

			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tabs, List list) {
		for (int i = 0; i < ColouredRedstone.COLOURS.length; i++)
			if (i != 1) // Skip Red
				list.add(new ItemStack(itemID, 1, i));
	}
}