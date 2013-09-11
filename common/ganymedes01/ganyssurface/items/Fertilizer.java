package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Fertilizer extends Rot {

	public Fertilizer(int id) {
		super(id);
		setTextureName(Utils.getItemTexture(Strings.FERTILIZER_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.FERTILIZER_NAME));
	}
}
