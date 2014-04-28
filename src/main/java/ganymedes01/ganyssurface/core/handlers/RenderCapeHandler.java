package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.client.renderer.DownloadImageData;
import ganymedes01.ganyssurface.lib.Reference;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraftforge.client.event.RenderPlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class RenderCapeHandler {

	private static final ArrayList<String> usersWithCapes = new ArrayList<String>();
	private final ThreadDownloadImageData CAPE_DATA = DownloadImageData.getDownloadImage(AbstractClientPlayer.getLocationCape("ganymedes01"), Reference.CAPE_IMAGE_FILE, null, null);
	private final ThreadDownloadImageData JEBJEB_CAPE_DATA = DownloadImageData.getDownloadImage(AbstractClientPlayer.getLocationCape("Jeb_Jeb"), Reference.JEBJEB_CAPE_IMAGE_FILE, null, null);
	private final ThreadDownloadImageData KPR_CAPE_DATA = DownloadImageData.getDownloadImage(AbstractClientPlayer.getLocationCape("KingPurpleRaptor"), Reference.KPR_CAPE_IMAGE_FILE, null, null);

	public static void getUsernames() {
		try {
			Scanner scanner = new Scanner(new URL(Reference.USERS_WITH_CAPES_FILE).openStream());
			while (scanner.hasNext())
				usersWithCapes.add(scanner.nextLine());
			scanner.close();
		} catch (Exception e) {
		}
	}

	@SubscribeEvent
	public void onPreRenderSpecials(RenderPlayerEvent.Specials.Pre event) {
		try {
			if (event.entityPlayer instanceof AbstractClientPlayer && CAPE_DATA instanceof DownloadImageData && JEBJEB_CAPE_DATA instanceof DownloadImageData && KPR_CAPE_DATA instanceof DownloadImageData)
				if (usersWithCapes.contains(event.entityPlayer.getCommandSenderName())) {
					AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
					if (!player.getTextureCape().isTextureUploaded()) {
						Field textureUploaded = player.getTextureCape().getClass().getField("textureUploaded");
						textureUploaded.setAccessible(true);
						textureUploaded.set(((AbstractClientPlayer) event.entityPlayer).getTextureCape(), true);
					}
					if (event.entityPlayer.getCommandSenderName().equals("Jeb_Jeb"))
						player.getTextureCape().setBufferedImage(((DownloadImageData) JEBJEB_CAPE_DATA).getBufferedImage());
					else if (event.entityPlayer.getCommandSenderName().equals("KingPurpleRaptor"))
						player.getTextureCape().setBufferedImage(((DownloadImageData) KPR_CAPE_DATA).getBufferedImage());
					else
						player.getTextureCape().setBufferedImage(((DownloadImageData) CAPE_DATA).getBufferedImage());

					event.renderCape = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}