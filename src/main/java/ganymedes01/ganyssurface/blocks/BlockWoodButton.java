package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockWoodButton extends BlockButtonWood implements IConfigurable {

	private final int plankMeta;

	public BlockWoodButton(int meta) {
		plankMeta = meta;
		setHardness(0.5F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName("button" + meta));
		setCreativeTab(GanysSurface.enableWoodenButtons ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(side, plankMeta);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWoodenButtons;
	}
}