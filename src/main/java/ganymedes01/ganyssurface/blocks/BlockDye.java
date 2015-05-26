package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockDye extends BlockStorage {

	private static enum TYPES {
		black("dyeBlack", new ItemStack(Items.dye, 9, 0), "blockDyeBlack"),
		red("dyeRed", new ItemStack(Items.dye, 9, 1), "blockDyeRed"),
		green("dyeGreen", new ItemStack(Items.dye, 9, 2), "blockDyeGreen"),
		brown("dyeBrown", new ItemStack(Items.dye, 9, 3), "blockDyeBrown"),
		purple("dyePurple", new ItemStack(Items.dye, 9, 5), "blockDyePurple"),
		cyan("dyeCyan", new ItemStack(Items.dye, 9, 6), "blockDyeCyan"),
		light_gray("dyeLightGray", new ItemStack(Items.dye, 9, 7), "blockDyeLightGray"),
		gray("dyeGray", new ItemStack(Items.dye, 9, 8), "blockDyeGray"),
		pink("dyePink", new ItemStack(Items.dye, 9, 9), "blockDyePink"),
		lime("dyeLime", new ItemStack(Items.dye, 9, 10), "blockDyeLime"),
		yellow("dyeYellow", new ItemStack(Items.dye, 9, 11), "blockDyeYellow"),
		light_blue("dyeLightBlue", new ItemStack(Items.dye, 9, 12), "blockDyeLightBlue"),
		magenta("dyeMagenta", new ItemStack(Items.dye, 9, 13), "blockDyeMagenta"),
		orange("dyeOrange", new ItemStack(Items.dye, 9, 14), "blockDyeOrange"),
		white("dyeWhite", new ItemStack(Items.dye, 9, 15), "blockDyeWhite");

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

	@Override
	public int getBlockNumber() {
		return TYPES.length();
	}

	@Override
	public Object getStoredObjectIngredient(int meta) {
		return TYPES.get(meta).ingredient;
	}

	@Override
	public ItemStack getStoredObjectResult(int meta) {
		return TYPES.get(meta).output.copy();
	}

	@Override
	public String getOreName(int meta) {
		return TYPES.get(meta).orename;
	}

	private static String[] getNames() {
		String[] names = new String[TYPES.length()];
		for (int i = 0; i < names.length; i++)
			names[i] = TYPES.values()[i].name();
		return names;
	}

	public BlockDye() {
		super(getNames());
		setBlockName(Utils.getUnlocalisedName(Strings.DYE));
		setBlockTextureName(Utils.getBlockTexture("storage_dye"));
		setCreativeTab(GanysSurface.enableDyeBlocks ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableDyeBlocks;
	}
}