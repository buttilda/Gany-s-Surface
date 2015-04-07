package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockWoodTrapdoor extends BlockTrapDoor {

	public BlockWoodTrapdoor(int meta) {
		super(Material.wood);
		disableStats();
		setHardness(3.0F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalizedName("trapdoor_" + BlockWoodDoor.names[meta]));
		setBlockTextureName(Utils.getBlockTexture("trapdoor_" + BlockWoodDoor.names[meta]));
		setCreativeTab(GanysSurface.enableWoodenTrapdoors ? GanysSurface.surfaceTab : null);
	}

}