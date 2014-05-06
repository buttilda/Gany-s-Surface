package ganymedes01.ganyssurface;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.configuration.ConfigurationHandler;
import ganymedes01.ganyssurface.core.handlers.FuelHandler;
import ganymedes01.ganyssurface.core.handlers.InterModComms;
import ganymedes01.ganyssurface.core.handlers.OpenContainerHandler;
import ganymedes01.ganyssurface.core.handlers.PoopHandler;
import ganymedes01.ganyssurface.core.handlers.RenderCapeHandler;
import ganymedes01.ganyssurface.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganyssurface.core.proxy.CommonProxy;
import ganymedes01.ganyssurface.core.utils.VersionHelper;
import ganymedes01.ganyssurface.creativetab.CreativeTabSurface;
import ganymedes01.ganyssurface.integration.ModIntegrator;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.recipes.ModRecipes;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
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
import cpw.mods.fml.relauncher.Side;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES)
public class GanysSurface {

	@Instance(Reference.MOD_ID)
	public static GanysSurface instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs surfaceTab = new CreativeTabSurface();
	public static boolean mobsShouldPoop = true;
	public static boolean enableChocolate = true;
	public static boolean shouldDoVersionCheck = true;
	public static boolean forceAllContainersOpen = false;
	public static boolean enableMarket = true;
	public static boolean enableWoodenArmour = true;
	public static boolean enableCamilaSeedsToDropFromGrass = true;
	public static int maxLevelOMCWorks = 15;
	public static int inkHarvesterMaxStrike = 5;
	public static int poopingChance = 15000;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModIntegrator.preInit();

		ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MASTER + File.separator + Reference.MOD_ID + ".cfg"));

		if (shouldDoVersionCheck) {
			VersionHelper.execute();
			FMLCommonHandler.instance().bus().register(new VersionCheckTickHandler());
		}

		proxy.registerEntities();

		ModBlocks.init();
		ModItems.init();
		ModRecipes.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PacketHandler.INSTANCE.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		GameRegistry.registerFuelHandler(new FuelHandler());

		if (mobsShouldPoop)
			MinecraftForge.EVENT_BUS.register(new PoopHandler());
		MinecraftForge.EVENT_BUS.register(new OpenContainerHandler());

		proxy.registerTileEntities();
		proxy.registerRenderers();

		if (!Loader.isModLoaded("mobsplice") && !Loader.isModLoaded("ganysend") && !Loader.isModLoaded("ganysnether"))
			if (event.getSide() == Side.CLIENT) {
				RenderCapeHandler.getUsernames();
				MinecraftForge.EVENT_BUS.register(new RenderCapeHandler());
			}

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