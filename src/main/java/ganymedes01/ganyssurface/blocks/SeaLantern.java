package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SeaLantern extends Block {

	public SeaLantern() {
		super(Material.glass);
		setHardness(0.3F);
		setLightLevel(1.0F);
		if (GanysSurface.enablePrismarineStuff)
			setCreativeTab(GanysSurface.surfaceTab);
		else
			setCreativeTab(null);
		setBlockName(Utils.getUnlocalizedName(Strings.SEA_LANTERN));
		setBlockTextureName(Utils.getBlockTexture(Strings.SEA_LANTERN));
	}
}