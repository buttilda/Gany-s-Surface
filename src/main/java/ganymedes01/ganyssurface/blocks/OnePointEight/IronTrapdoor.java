package ganymedes01.ganyssurface.blocks.OnePointEight;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
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

public class IronTrapdoor extends BlockTrapDoor implements IConfigurable {

	public IronTrapdoor() {
		super(Material.iron);
		setHardness(5.0F);
		setStepSound(soundTypeMetal);
		setBlockTextureName(Strings.IRON_TRAPDOOR);
		setBlockName(Utils.getUnlocalisedName(Strings.IRON_TRAPDOOR));
		setCreativeTab(GanysSurface.enableIronTrapdoor ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableIronTrapdoor;
	}
}