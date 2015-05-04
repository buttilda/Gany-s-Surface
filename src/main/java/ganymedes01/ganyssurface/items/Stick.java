package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.blocks.BlockWoodDoor;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.BlockWood;
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

public class Stick extends Item implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] icon;

	public Stick() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setTextureName(Utils.getItemTexture(Strings.STICK_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.STICK_NAME));
		setCreativeTab(GanysSurface.enableWoodenLadders ? GanysSurface.surfaceTab : null);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "_" + BlockWoodDoor.names[Math.max(0, Math.min(stack.getItemDamage() + 1, BlockWoodDoor.names.length - 1))];
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
		for (int i = 0; i < BlockWood.field_150096_a.length - 1; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icon = new IIcon[BlockWood.field_150096_a.length - 1];
		for (int i = 0; i < icon.length; i++)
			icon[i] = reg.registerIcon(getIconString() + "_" + BlockWoodDoor.names[i + 1]);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWoodenLadders;
	}
}