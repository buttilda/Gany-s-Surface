package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemBlockGeneric;

import java.util.List;

import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.BlockWood;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockWoodBookshelf extends BlockBookshelf implements ISubBlocksBlock, IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockWoodBookshelf() {
		setHardness(1.5F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName("bookshelf"));
		setBlockTextureName(Utils.getBlockTexture("bookshelf_"));
		setCreativeTab(GanysSurface.enableWoodenBookshelves ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < BlockWood.field_150096_a.length - 1; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side != 1 && side != 0 ? icons[Math.max(Math.min(meta, icons.length - 1), 0)] : Blocks.planks.getIcon(side, meta + 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[BlockWood.field_150096_a.length - 1];

		for (int i = 0; i < icons.length; i++)
			icons[i] = reg.registerIcon(getTextureName() + BlockWoodDoor.names[i + 1]);
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWoodenBookshelves;
	}
}