package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.api.ISlimeBlockSpreable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemBlockGeneric;
import ganymedes01.ganyssurface.lib.ModSounds;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
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

public class BlockOfPoop extends Block implements ISlimeBlockSpreable, ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockOfPoop() {
		super(Material.cloth);
		setHardness(2.0F);
		setHarvestLevel("shovel", 0);
		setStepSound(ModSounds.soundSlime);
		setBlockName(Utils.getUnlocalisedName(Strings.BLOCK_OF_POOP_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.BLOCK_OF_POOP_NAME));
		setCreativeTab(GanysSurface.enablePoop ? GanysSurface.surfaceTab : null);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 2; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[Math.max(Math.min(meta, icons.length - 1), 0)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[2];
		icons[0] = reg.registerIcon(getTextureName() + 0);
		icons[1] = reg.registerIcon(getTextureName() + 1);
	}

	@Override
	public float getSpreadChance(World world, int x, int y, int z) {
		float chance = 0.075F;
		if (world.getBlockMetadata(x, y, z) == 1)
			chance *= 2;

		return chance;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}
}