package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockStorage extends BlockGeneric implements ISubBlocksBlock, IConfigurable {

	private static enum TYPES {
		flint(new ItemStack(Items.flint), new ItemStack(Items.flint, 9), "blockFlint"),
		carrot(new ItemStack(Items.carrot), new ItemStack(Items.carrot, 9), "blockCarrot"),
		gunpowder(new ItemStack(Items.gunpowder), new ItemStack(Items.gunpowder, 9), "blockGunpowder"),
		sugar(new ItemStack(Items.sugar), new ItemStack(Items.sugar, 9), "blockSugar");
		/*
		 * potato(new ItemStack(Items.potato), new ItemStack(Items.potato, 9), "blockPotato"), bone(new ItemStack(Items.bone), new ItemStack(Items.bone, 9), "blockBone"), apple(new ItemStack(Items.apple), new ItemStack(Items.apple, 9), "blockApple"), leather(new ItemStack(Items.leather), new ItemStack(Items.leather, 9), "blockLeather");
		 */

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

	private static String[] getNames() {
		String[] names = new String[TYPES.length()];
		for (int i = 0; i < names.length; i++)
			names[i] = TYPES.values()[i].name();
		return names;
	}

	public int getBlockNumber() {
		return TYPES.length();
	}

	public Object getStoredObjectIngredient(int meta) {
		return TYPES.get(meta).ingredient;
	}

	public ItemStack getStoredObjectResult(int meta) {
		return TYPES.get(meta).output.copy();
	}

	public String getOreName(int meta) {
		return TYPES.get(meta).orename;
	}

	public BlockStorage() {
		this(getNames());

	}

	protected BlockStorage(String... strings) {
		super(Material.rock, strings);
		setHardness(2.0F);
		setStepSound(soundTypeStone);
		setBlockName(Utils.getUnlocalisedName(Strings.STORAGE));
		setBlockTextureName(Utils.getBlockTexture(Strings.STORAGE));
		setCreativeTab(GanysSurface.enableStorageBlocks ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableStorageBlocks;
	}
}