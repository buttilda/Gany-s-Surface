package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
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

	public DisguisedTrapDoor(int type) {
		super(Material.wood);
		disableStats();
		this.type = type;
		setHardness(3.0F);
		setStepSound(soundTypeWood);
		setCreativeTab(GanysSurface.surfaceTab);
		setBlockName(Utils.getUnlocalizedName(Strings.DISGUISED_TRAP_DOOR_NAME + BlockWood.field_150096_a[type]));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon("planks_" + BlockWood.field_150096_a[type]);
	}
}