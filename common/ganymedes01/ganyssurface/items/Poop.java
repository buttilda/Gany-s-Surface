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

public class Poop extends Item {

	@SideOnly(Side.CLIENT)
	private Icon[] icon;

	public Poop(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.POOP_NAME));
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
		for (int i = 0; i <= 1; ++i)
			list.add(new ItemStack(itemID, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icon = new Icon[2];

		for (int i = 0; i <= 1; ++i)
			icon[i] = reg.registerIcon(Utils.getItemTexture(Strings.POOP_NAME) + "_" + i);
	}
}
