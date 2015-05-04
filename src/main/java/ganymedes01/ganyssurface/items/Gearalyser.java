package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Gearalyser extends Item implements IConfigurable {

	public Gearalyser() {
		setMaxStackSize(1);
		setTextureName(Utils.getItemTexture(Strings.GEARALYSER_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.GEARALYSER_NAME));
		setCreativeTab(GanysSurface.enableAnalisers ? GanysSurface.surfaceTab : null);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote)
			player.openGui(GanysSurface.instance, GUIsID.GEARALYSER, world, 0, 0, 0);
		return stack;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableAnalisers;
	}
}