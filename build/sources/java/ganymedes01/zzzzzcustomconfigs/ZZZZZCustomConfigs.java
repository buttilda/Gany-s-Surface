package ganymedes01.zzzzzcustomconfigs;

import ganymedes01.zzzzzcustomconfigs.handler.ConfigurationHandler;
import ganymedes01.zzzzzcustomconfigs.handler.HandlerEvents;
import ganymedes01.zzzzzcustomconfigs.lib.Files;
import ganymedes01.zzzzzcustomconfigs.lib.Reference;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES)
public class ZZZZZCustomConfigs {

	@Instance(Reference.MOD_ID)
	public static ZZZZZCustomConfigs instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Files.setPath(event.getModConfigurationDirectory().getAbsolutePath());

		MinecraftForge.EVENT_BUS.register(new HandlerEvents());
		ConfigurationHandler.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ConfigurationHandler.init();
	}
}