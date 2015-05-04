package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.BlockCarrot;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockBeetroot extends BlockCarrot implements IConfigurable {

	public BlockBeetroot() {
		setCreativeTab(null);
		setBlockName(Utils.getUnlocalisedName(Strings.BLOCK_BEETROOT_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.BLOCK_BEETROOT_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return meta < 7 ? Blocks.carrots.getIcon(side, meta) : blockIcon;
	}

	@Override
	protected Item func_149866_i() {
		return ModItems.beetrootSeeds;
	}

	@Override
	protected Item func_149865_P() {
		return ModItems.beetroot;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(getTextureName());
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableBeetroots;
	}
}