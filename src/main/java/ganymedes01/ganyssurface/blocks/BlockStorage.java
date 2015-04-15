package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.block.ItemBlockGeneric;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
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

public class BlockStorage extends Block implements ISubBlocksBlock {

	private static enum TYPES {
		carrot(new ItemStack(Items.carrot), new ItemStack(Items.carrot, 9), "blockCarrot");
		/*gunpowder(new ItemStack(Items.gunpowder), new ItemStack(Items.gunpowder, 9), "blockGunpowder"),
		potato(new ItemStack(Items.potato), new ItemStack(Items.potato, 9), "blockPotato"),
		bone(new ItemStack(Items.bone), new ItemStack(Items.bone, 9), "blockBone"),
		sugar(new ItemStack(Items.sugar), new ItemStack(Items.sugar, 9), "blockSugar"),
		apple(new ItemStack(Items.apple), new ItemStack(Items.apple, 9), "blockApple"),
		leather(new ItemStack(Items.leather), new ItemStack(Items.leather, 9), "blockLeather");*/

		private final Object ingredient;
		private final ItemStack output;
		private final String orename;

		private TYPES(Object ingredient, ItemStack output, String orename) {
			this.ingredient = ingredient;
			this.output = output;
			this.orename = orename;
		}

		public static int length() {
			return values().length;
		}

		public static TYPES get(int ordinal) {
			return values()[ordinal];
		}
	}

	public static String getTypesString() {
		String result = "";
		for (int i = 0; i < TYPES.length() - 1; i++)
			result += TYPES.get(i) + ", ";
		result += TYPES.get(TYPES.length() - 1);

		return result;
	}

	public static int getBlockNumber() {
		return TYPES.length();
	}

	public static Object getStoredObjectIngredient(int meta) {
		return TYPES.get(meta).ingredient;
	}

	public static ItemStack getStoredObjectResult(int meta) {
		return TYPES.get(meta).output.copy();
	}

	public static String getOreName(int meta) {
		return TYPES.get(meta).orename;
	}

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockStorage() {
		super(Material.rock);
		setHardness(2.0F);
		setStepSound(soundTypeStone);
		setBlockName(Utils.getUnlocalisedName(Strings.STORAGE));
		setBlockTextureName(Utils.getBlockTexture(Strings.STORAGE));
		setCreativeTab(GanysSurface.enableStorageBlocks ? GanysSurface.surfaceTab : null);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < TYPES.length(); i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[Math.max(Math.min(meta, TYPES.length() - 1), 0)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[TYPES.length()];

		for (int i = 0; i < TYPES.length(); i++)
			icons[i] = reg.registerIcon(getTextureName() + "_" + TYPES.get(i));
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}
}