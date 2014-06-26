package ganymedes01.ganyssurface.configuration;

import ganymedes01.ganyssurface.lib.Reference;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ConfigGUI extends GuiConfig {

	public ConfigGUI(GuiScreen parent) {
		super(parent, getElements(), Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.INSTANCE.configFile.toString()));
	}

	@SuppressWarnings({ "rawtypes" })
	private static List<IConfigElement> getElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		for (String category : ConfigurationHandler.INSTANCE.usedCategories)
			list.add(new ConfigElement(ConfigurationHandler.INSTANCE.configFile.getCategory(category)));
		return list;
	}
}