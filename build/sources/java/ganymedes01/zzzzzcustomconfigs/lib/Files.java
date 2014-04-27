package ganymedes01.zzzzzcustomconfigs.lib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Files {

	private static String path;

	private static final String ORE_DICTIONARY = "OreDictionary";
	private static final String SMELTING = "Smelting";
	private static final String ORE_DICTIONARY_SMELTING = "OreDictSmelting";
	private static final String SHAPED_RECIPE = "ShapedRecipe";
	private static final String SHAPELESS_RECIPE = "ShapelessRecipe";
	private static final String ENTITY_BLACKLIST = "EntityBlacklist";
	private static final String REMOVE_RECIPE = "RemoveRecipe";
	private static final String IC2_RECIPE = "IC2Recipe";
	private static final String TC4_ASPECTS = "TC4Aspects";

	private static final String ORE_DICTIONARY_DEFAULT = "# oreName, itemName, itemMetadata #";
	private static final String SMELTING_DEFAULT = "# inputName, inputMetadata, outputName, outputAmount, outputMetadata, xp #";
	private static final String ORE_DICTIONARY_SMELTING_DEFAULT = "# oreName, outputName, outputAmount, outputMetadata, xp #\n" + "# Will override recipes from vanilla and mods loaded previously #";
	private static final String SHAPED_RECIPE_DEFAULT = "# resultName, resultAmount, resultMetadata, topRow, middleRow, bottomRow, (ingredIdentifier, ingredName, ingredMetada)... #\n" + "# Diamond sword recipe example: 276, 0, 0, x, x, y, x, 264, 0, y, 280, 0 #\n" + "# If is empty leave it blank. Example: xx, , xx\n" + "# L shaped recipe example (leave an actual blank space where it says (space)): (space)(space)x, (space)(space)x, xxx #";
	private static final String SHAPELESS_RECIPE_DEFAULT = "# resultName, resultAmount, resultMetadata, (inputId, inputMetadata)... #\n" + "# Example of recipe for bedrock using stone, dirt and grass: bedrock,1,0, stone,0, dirt,0, grass,0 #";
	private static final String ENTITY_BLACKLIST_DEFAULT = "# entityName #\n" + "# One name per line. Example: #\n" + "# Creeper";
	private static final String REMOVE_RECIPE_DEFAULT = "# outputName, outputMetadata #\n" + "# All the shaped and shapeless recipes for the items in this file will be removed #";
	private static final String IC2_RECIPE_DEFAULT = "# machineName, inputOreDict, inputSize, extra(some don't need it) ,outputName, outputAmount, outputMetadata... #\n" + "# Types: macerator, extractor, compressor, centrifuge, metalformerExtruding, metalformerCutting, metalformerRolling, oreWashing #\n" + "# Examples: #\n" + "# Macerating 1 dust copper yields a diamond sword #\n" + "# macerator, dustCopper, 1, 276, 1, 0 #\n"
	+ "# Thermal Centrifuge, with min heat of 1000, 1 diamond sword yields a 2 diamonds and a stick #\n" + "# centrifuge, 276,1,0, 264,2,0, 280,1,0 #";
	private static final String TC4_ASPECTS_DEFAULT;

	static {
		StringBuffer buffer = new StringBuffer();
		// buffer.append("# ");
		// for (Entry<String, Aspect> entry : Aspect.aspects.entrySet())
		// buffer.append(entry.getKey() + ", ");
		// buffer.deleteCharAt(buffer.length() - 1);
		// buffer.deleteCharAt(buffer.length() - 1);
		//
		// buffer.append(" #\n");

		buffer.append("# id:meta = aspect:amount, aspect:amount, aspect:amount... #\n");
		buffer.append("# Example(diamond sword with 1 lux and 5 ordo):  256:0 = lux:1, ordo:5 #");

		TC4_ASPECTS_DEFAULT = buffer.toString();
	}

	public static void setPath(String path) {
		Files.path = path + File.separator + Reference.MOD_ID + File.separator;

		File parentPath = new File(Files.path);
		parentPath.mkdirs();
	}

	public static File getOreDictionaryFile() throws IOException {
		return getFile(ORE_DICTIONARY, ORE_DICTIONARY_DEFAULT);
	}

	public static File getSmeltingFile() throws IOException {
		return getFile(SMELTING, SMELTING_DEFAULT);
	}

	public static File getOreDictSmeltingFile() throws IOException {
		return getFile(ORE_DICTIONARY_SMELTING, ORE_DICTIONARY_SMELTING_DEFAULT);
	}

	public static File getShapedRecipesFile() throws IOException {
		return getFile(SHAPED_RECIPE, SHAPED_RECIPE_DEFAULT);
	}

	public static File getShapedOreDictRecipesFile() throws IOException {
		return getFile(SHAPELESS_RECIPE, SHAPELESS_RECIPE_DEFAULT);
	}

	public static File getBlacklistEntityFile() throws IOException {
		return getFile(ENTITY_BLACKLIST, ENTITY_BLACKLIST_DEFAULT);
	}

	public static File getRemoveRecipeFile() throws IOException {
		return getFile(REMOVE_RECIPE, REMOVE_RECIPE_DEFAULT);
	}

	public static File getIC2RecipeFile() throws IOException {
		return getFile(IC2_RECIPE, IC2_RECIPE_DEFAULT);
	}

	public static File getTC4AspectsFile() throws IOException {
		return getFile(TC4_ASPECTS, TC4_ASPECTS_DEFAULT);
	}

	public static File getFile(String name, String heading) throws IOException {
		File file = new File(path + name + ".csv");
		if (!file.exists()) {
			file.createNewFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			writer.write(heading);
			writer.close();
		}
		return file;
	}
}