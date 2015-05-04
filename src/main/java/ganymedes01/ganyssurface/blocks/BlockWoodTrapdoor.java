package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.RenderIDs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
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

public class BlockWoodTrapdoor extends BlockTrapDoor implements IConfigurable {

	public final int woodMeta;

	public BlockWoodTrapdoor(int meta) {
		super(Material.wood);
		woodMeta = meta;
		disableStats();
		setHardness(3.0F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName("trapdoor" + meta));
		setBlockTextureName(Utils.getBlockTexture("trapdoor_" + BlockWoodDoor.names[meta]));
		setCreativeTab(GanysSurface.enableWoodenTrapdoors ? GanysSurface.surfaceTab : null);
	}

	@Override
	public int getRenderType() {
		return RenderIDs.TRAPDOOR;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		boolean isOpen = func_150118_d(meta);
		if (isOpen) {
			switch (meta & 3) {
				case 0:
					if (side == 2)
						return super.getIcon(side, meta);
					break;
				case 1:
					if (side == 3)
						return super.getIcon(side, meta);
					break;
				case 2:
					if (side == 4)
						return super.getIcon(side, meta);
					break;
				case 3:
					if (side == 5)
						return super.getIcon(side, meta);
					break;
			}
			return Blocks.planks.getIcon(side, woodMeta);
		}

		return side == 0 || side == 1 ? super.getIcon(side, meta) : Blocks.planks.getIcon(side, woodMeta);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWoodenTrapdoors;
	}
}