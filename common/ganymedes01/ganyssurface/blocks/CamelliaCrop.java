package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class CamelliaCrop extends BlockCrops {

	@SideOnly(Side.CLIENT)
	private Icon[] iconArray;

	protected CamelliaCrop() {
		super(ModIDs.CAMELLIA_CROP_ID);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CAMELLIA_CROP_NAME));
	}

	@Override
	protected int getSeedItem() {
		return ModItems.camelliaSeeds.itemID;
	}

	@Override
	protected int getCropItem() {
		return ModItems.teaLeaves.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta < 7) {
			if (meta == 6)
				meta = 5;
			return iconArray[meta >> 1];
		}
		return iconArray[3];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		iconArray = new Icon[4];
		for (int i = 0; i < iconArray.length; ++i)
			iconArray[i] = reg.registerIcon(Utils.getBlockTexture(Strings.CAMELLIA_CROP_NAME + "_stage_") + i);
	}
}
