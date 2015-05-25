package ganymedes01.ganyssurface.blocks.OnePointEight;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemBlockGeneric;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;
import java.util.Random;

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

public class Stones18 extends Block implements ISubBlocksBlock, IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public static final int GRANITE = 1;
	public static final int POLISHED_GRANITE = 2;
	public static final int DIORITE = 3;
	public static final int POLISHED_DIORITE = 4;
	public static final int ANDESITE = 5;
	public static final int POLISHED_ANDESITE = 6;

	public Stones18() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName(Strings.NEW_STONES_NAME));
		setCreativeTab(GanysSurface.enable18Stones ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return this == target || target == Blocks.stone;
	}

	@Override
	public int damageDropped(int meta) {
		return isBasalt(meta) ? meta - 7 : meta;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return isBasalt(meta) ? ModBlocks.basalt.getItemDropped(meta - 7, rand, fortune) : super.getItemDropped(meta, rand, fortune);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 1; i < 7; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (isBasalt(meta))
			return ModBlocks.basalt.getIcon(side, meta - 7);

		return icons[Math.max(Math.min(meta, icons.length - 1), 1)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[7];
		icons[1] = reg.registerIcon("stone_granite");
		icons[2] = reg.registerIcon("stone_granite_smooth");
		icons[3] = reg.registerIcon("stone_diorite");
		icons[4] = reg.registerIcon("stone_diorite_smooth");
		icons[5] = reg.registerIcon("stone_andesite");
		icons[6] = reg.registerIcon("stone_andesite_smooth");
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

	private boolean isBasalt(int meta) {
		return meta == 7 || meta == 8;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enable18Stones;
	}
}