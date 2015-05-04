package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.entities.EntityBatPoop;
import ganymedes01.ganyssurface.entities.EntityPoop;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

public class Poop extends Item implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] icon;

	public Poop() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.POOP_NAME));
		setCreativeTab(GanysSurface.enablePoop ? GanysSurface.surfaceTab : null);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 0)
			return super.getUnlocalizedName();
		else
			return "item." + Reference.MOD_ID + ".batPoop";
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!GanysSurface.enableFlingablePoop)
			return super.onItemRightClick(stack, world, player);

		if (!player.capabilities.isCreativeMode)
			stack.stackSize--;

		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isRemote)
			if (stack.getItemDamage() == 0)
				world.spawnEntityInWorld(new EntityPoop(world, player));
			else
				world.spawnEntityInWorld(new EntityBatPoop(world, player));

		return stack;
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
		for (int i = 0; i < icon.length; i++)
			icon[i] = reg.registerIcon(Utils.getItemTexture(Strings.POOP_NAME) + "_" + i);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePoop;
	}
}