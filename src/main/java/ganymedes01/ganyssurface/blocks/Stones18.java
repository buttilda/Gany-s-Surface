package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.Item18Stones;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Stones18 extends Block implements ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public Stones18() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalizedName(Strings.NEW_STONES_NAME));
		setCreativeTab(GanysSurface.enable18Stones ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return this == target || target == Blocks.stone;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 1; i < 9; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[Math.max(Math.min(meta, icons.length - 1), 1)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[9];
		icons[1] = reg.registerIcon("stone_granite");
		icons[2] = reg.registerIcon("stone_granite_smooth");
		icons[3] = reg.registerIcon("stone_diorite");
		icons[4] = reg.registerIcon("stone_diorite_smooth");
		icons[5] = reg.registerIcon("stone_andesite");
		icons[6] = reg.registerIcon("stone_andesite_smooth");

		icons[7] = reg.registerIcon(Utils.getBlockTexture("stone_basalt"));
		icons[8] = reg.registerIcon(Utils.getBlockTexture("stone_basalt_smooth"));
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return Item18Stones.class;
	}
}