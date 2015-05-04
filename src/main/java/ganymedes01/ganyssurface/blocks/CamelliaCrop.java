package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class CamelliaCrop extends BlockCrops implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;

	public CamelliaCrop() {
		super();
		setBlockName(Utils.getUnlocalisedName(Strings.CAMELLIA_CROP_NAME));
	}

	@Override
	protected Item func_149866_i() {
		return ModItems.camelliaSeeds;
	}

	@Override
	protected Item func_149865_P() {
		return ModItems.teaLeaves;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta < 7) {
			if (meta == 6)
				meta = 5;
			return iconArray[meta >> 1];
		}
		return iconArray[3];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		iconArray = new IIcon[4];
		for (int i = 0; i < iconArray.length; ++i)
			iconArray[i] = reg.registerIcon(Utils.getBlockTexture(Strings.CAMELLIA_CROP_NAME + "_stage_") + i);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableTea;
	}
}