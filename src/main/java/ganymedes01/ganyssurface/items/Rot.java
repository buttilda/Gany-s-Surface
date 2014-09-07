package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
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
	private IIcon[] icon;

	public Rot() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ROT_NAME));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + (stack.getItemDamage() == 0 ? Utils.getUnlocalizedName(Strings.ROT_NAME) : Utils.getUnlocalizedName(Strings.FERTILIZER_NAME));
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
		list.add(new ItemStack(item));
		if (GanysSurface.enablePoop)
			list.add(new ItemStack(item, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icon = new IIcon[2];
		icon[0] = reg.registerIcon(Utils.getItemTexture(Strings.ROT_NAME));
		icon[1] = reg.registerIcon(Utils.getItemTexture(Strings.FERTILIZER_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (ItemDye.applyBonemeal(stack, world, x, y, z, player)) {
			if (!world.isRemote)
				world.playAuxSFX(2005, x, y, z, 0);
			return true;
		}
		return false;
	}
}