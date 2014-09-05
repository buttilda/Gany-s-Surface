package ganymedes01.ganyssurface.core.handlers;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

	public static IIcon itemFrameBackground;

	@SubscribeEvent
	public void loadTextures(TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 0)
			itemFrameBackground = event.map.registerIcon("itemframe_background");
	}
}