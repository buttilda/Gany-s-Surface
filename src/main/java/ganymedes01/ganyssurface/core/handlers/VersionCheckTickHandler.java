package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.core.utils.VersionHelper;
import ganymedes01.ganyssurface.lib.Reference;

import java.util.EnumSet;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class VersionCheckTickHandler implements ITickHandler {

	private static boolean initialized = false;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (!initialized)
			for (TickType tickType : type)
				if (tickType == TickType.CLIENT)
					if (FMLClientHandler.instance().getClient().currentScreen == null)
						if (VersionHelper.getResult() != VersionHelper.UNINITIALIZED || VersionHelper.getResult() != VersionHelper.FINAL_ERROR) {
							initialized = true;
							if (VersionHelper.getResult() == VersionHelper.OUTDATED)
								FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(VersionHelper.getResultMessageForClient());
						}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return Reference.MOD_NAME + ": " + this.getClass().getSimpleName();
	}
}
