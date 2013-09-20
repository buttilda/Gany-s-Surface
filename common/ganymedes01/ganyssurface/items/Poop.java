package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Poop extends Item {

	@SideOnly(Side.CLIENT)
	private Icon[] icon = new Icon[2];

	public Poop(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.POOP_NAME));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 0)
			return super.getUnlocalizedName();
		else
			return "item." + Reference.MOD_ID + ".batPoop";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		return icon[i];
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
		icon[0] = reg.registerIcon(Utils.getItemTexture(Strings.POOP_NAME) + "_0");
		icon[1] = reg.registerIcon(Utils.getItemTexture(Strings.POOP_NAME) + "_1");
	}
}
