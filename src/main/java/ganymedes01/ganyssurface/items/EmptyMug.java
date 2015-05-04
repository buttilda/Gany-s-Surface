package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class EmptyMug extends Item implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] icon;

	public EmptyMug() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.EMPTY_MUG_NAME));
		setCreativeTab(GanysSurface.enableTea ? GanysSurface.surfaceTab : null);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();
		String s = super.getUnlocalizedName(stack);
		return meta == 0 ? s : s + "_raw";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return icon[Math.max(0, Math.min(meta, icon.length - 1))];
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (int i = 0; i < 2; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icon = new IIcon[2];
		icon[0] = reg.registerIcon(Utils.getItemTexture(Strings.EMPTY_MUG_NAME));
		icon[1] = reg.registerIcon(Utils.getItemTexture(Strings.EMPTY_MUG_NAME + "_raw"));
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableTea;
	}
}