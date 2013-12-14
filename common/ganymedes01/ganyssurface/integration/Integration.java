package ganymedes01.ganyssurface.integration;

import cpw.mods.fml.common.Loader;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public abstract class Integration {

	private boolean integrate;

	public abstract void init();

	public abstract void postInit();

	public abstract String getModID();

	public boolean shouldIntegrate() {
		return integrate && Loader.isModLoaded(getModID());
	}

	public void setShouldIntegrate(boolean integrate) {
		this.integrate = integrate;
	}

	@Override
	public String toString() {
		return getModID();
	}
}