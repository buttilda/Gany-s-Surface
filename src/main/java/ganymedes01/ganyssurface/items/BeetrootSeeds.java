package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BeetrootSeeds extends ItemSeeds implements IConfigurable {

	public BeetrootSeeds() {
		super(ModBlocks.beetroot, Blocks.farmland);
		setTextureName(Utils.getItemTexture(Strings.BEETROOT_SEEDS_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.BEETROOT_SEEDS_NAME));
		setCreativeTab(GanysSurface.enableBeetroots ? GanysSurface.surfaceTab : null);

		if (GanysSurface.enableBeetroots)
			MinecraftForge.addGrassSeed(new ItemStack(this), 7);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableBeetroots;
	}
}