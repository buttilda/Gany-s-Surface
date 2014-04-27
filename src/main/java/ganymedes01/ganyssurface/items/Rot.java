package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Rot extends Item {

	@SideOnly(Side.CLIENT)
	private Icon[] icon;

	public Rot() {
		super(ModIDs.ROT_ID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysSurface.surfaceTab);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + (stack.getItemDamage() == 0 ? Utils.getUnlocalizedName(Strings.ROT_NAME) : Utils.getUnlocalizedName(Strings.FERTILIZER_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return icon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tabs, List list) {
		list.add(new ItemStack(itemID, 1, 0));
		list.add(new ItemStack(itemID, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icon = new Icon[2];
		icon[0] = reg.registerIcon(Utils.getItemTexture(Strings.ROT_NAME));
		icon[1] = reg.registerIcon(Utils.getItemTexture(Strings.FERTILIZER_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (ItemDye.func_96604_a(stack, world, x, y, z)) {
			if (!world.isRemote)
				world.playAuxSFX(2005, x, y, z, 0);
			return true;
		}
		return false;
	}
}