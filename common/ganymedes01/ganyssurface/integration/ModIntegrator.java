package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.lib.Reference;

import java.util.ArrayList;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ModIntegrator {

	public static ArrayList<Integration> modIntegrations;

	public static void preInit() {
		modIntegrations = new ArrayList<Integration>();

		try {
			for (ClassInfo clazzInfo : ClassPath.from(ModIntegrator.class.getClassLoader()).getTopLevelClasses("ganymedes01." + Reference.MOD_ID + ".integration")) {
				Class clazz = clazzInfo.load();
				if (clazz != Integration.class && Integration.class.isAssignableFrom(clazz))
					modIntegrations.add((Integration) clazz.newInstance());
			}
		} catch (Exception e) {
		}
	}

	public static void init() {
		for (Integration integration : modIntegrations)
			if (integration.shouldIntegrate())
				integration.init();
	}

	public static void postInit() {
		for (Integration integration : modIntegrations)
			if (integration.shouldIntegrate())
				integration.postInit();
	}
}