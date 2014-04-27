package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class DisguisedTrapDoor extends BlockTrapDoor {

	private final int type;

	public DisguisedTrapDoor(int id, int type) {
		super(id, Material.wood);
		disableStats();
		this.type = type;
		setHardness(3.0F);
		setStepSound(soundWoodFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		switch (type) {
			case 0:
				setUnlocalizedName(Utils.getUnlocalizedName(Strings.DISGUISED_TRAP_DOOR_OAK_NAME));
				break;
			case 1:
				setUnlocalizedName(Utils.getUnlocalizedName(Strings.DISGUISED_TRAP_DOOR_SPRUCE_NAME));
				break;
			case 2:
				setUnlocalizedName(Utils.getUnlocalizedName(Strings.DISGUISED_TRAP_DOOR_BIRCH_NAME));
				break;
			case 3:
				setUnlocalizedName(Utils.getUnlocalizedName(Strings.DISGUISED_TRAP_DOOR_JUNGLE_NAME));
				break;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon("planks_" + BlockWood.woodType[type]);
	}
}
