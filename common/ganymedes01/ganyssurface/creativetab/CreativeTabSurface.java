package ganymedes01.ganyssurface.creativetab;

import ganymedes01.ganyssurface.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class CreativeTabSurface extends CreativeTabs {

	public CreativeTabSurface() {
		super(Reference.MOD_ID);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex() {
		return Block.grass.blockID;
	}
}
