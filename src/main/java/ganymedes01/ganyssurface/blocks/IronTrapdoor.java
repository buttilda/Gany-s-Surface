package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class IronTrapdoor extends BlockTrapDoor {

	public IronTrapdoor() {
		super(Material.iron);
		setHardness(5.0F);
		setStepSound(soundTypeMetal);
		if (GanysSurface.enableIronTrapdoor)
			setCreativeTab(GanysSurface.surfaceTab);
		else
			setCreativeTab(null);
		setBlockName(Utils.getUnlocalizedName(Strings.IRON_TRAPDOOR));
		setBlockTextureName(Utils.getBlockTexture(Strings.IRON_TRAPDOOR));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return false;
	}
}