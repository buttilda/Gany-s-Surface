package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class EncasingBench extends Block {

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public EncasingBench() {
		super(ModIDs.ENCASING_BENCH_ID, Material.wood);
		setHardness(1.0F);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENCASING_BENCH_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			player.openGui(GanysSurface.instance, GUIsID.ENCASING_BENCH, world, x, y, z);
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? icons[0] : icons[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icons = new Icon[2];
		for (int i = 0; i < icons.length; i++)
			icons[i] = reg.registerIcon(Utils.getBlockTexture(Strings.ENCASING_BENCH_NAME) + i);
	}
}