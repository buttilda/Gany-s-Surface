package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockWoodPressurePlate extends BlockPressurePlate {

	public BlockWoodPressurePlate(int meta) {
		super("planks_" + BlockWood.field_150096_a[meta], Material.wood, BlockPressurePlate.Sensitivity.everything);
		setHardness(0.5F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalizedName("pressurePlate" + meta));
		setCreativeTab(GanysSurface.enableWoodenPressurePlates ? GanysSurface.surfaceTab : null);
	}

}