package ganymedes01.ganyssurface;

import ganymedes01.ganyssurface.configuration.ConfigurationHandler;
import ganymedes01.ganyssurface.core.handlers.FuelHandler;
import ganymedes01.ganyssurface.core.handlers.InterModComms;
import ganymedes01.ganyssurface.core.proxy.CommonProxy;
import ganymedes01.ganyssurface.creativetab.CreativeTabSurface;
import ganymedes01.ganyssurface.integration.ModIntegrator;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.recipes.ModRecipes;
import ganymedes01.ganyssurface.world.SurfaceWorldGen;
import ganymedes01.ganyssurface.world.Temple;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class GanysSurface {

	@Instance(Reference.MOD_ID)
	public static GanysSurface instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs surfaceTab = new CreativeTabSurface();

	public static boolean enableDynamicSnow = true;
	public static boolean mobsShouldPoop = true;
	public static boolean enableChocolate = true;
	public static boolean shouldDoVersionCheck = true;
	public static boolean forceAllContainersOpen = false;
	public static boolean enableWoodenArmour = true;
	public static boolean enableCamilaSeedsToDropFromGrass = true;
	public static boolean poopRandomBonemeals = true;
	public static int maxLevelOMCWorks = 15;
	public static int inkHarvesterMaxStrike = 5;
	public static int poopingChance = 15000;
	public static boolean enable18Stones = true;
	public static boolean enableIronTrapdoor = true;
	public static boolean enableMutton = true;
	public static boolean enableSpongeTexture = true;
	public static boolean enablePrismarineStuff = true;
	public static boolean enableDispenserShears = true;
	public static int prismarineTempleChance = 1000;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModIntegrator.preInit();

		ConfigurationHandler.INSTANCE.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MASTER + File.separator + Reference.MOD_ID + ".cfg"));

		GameRegistry.registerWorldGenerator(new SurfaceWorldGen(), 0);

		ModBlocks.init();
		ModItems.init();
		ModRecipes.init();

		Temple.makeMap();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PacketHandler.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

		GameRegistry.registerFuelHandler(new FuelHandler());

		proxy.registerEvents();
		proxy.registerTileEntities();
		proxy.registerRenderers();
		proxy.registerEntities();

		ModIntegrator.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModIntegrator.postInit();
	}

	@EventHandler
	public void processIMCRequests(IMCEvent event) {
		InterModComms.processIMC(event);
	}
}