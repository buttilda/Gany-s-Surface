package ganymedes01.ganyssurface.core.handlers;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

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
	private static ResourceLocation CAPE_DATA = Utils.getResource(Reference.MOD_ID + ":textures/capes/gany.png");
	private static ResourceLocation JEBJEB_CAPE_DATA = Utils.getResource(Reference.MOD_ID + ":textures/capes/jade.png");
	private static ResourceLocation KPR_CAPE_DATA = Utils.getResource(Reference.MOD_ID + ":textures/capes/KingPurpleRaptor.png");

	private static BufferedImage CAPE_IMAGE;
	private static BufferedImage JEBJEB_CAPE_IMAGE;
	private static BufferedImage KPR_CAPE_IMAGE;

	private static boolean started = false;
	private static boolean finished = false;
	private static boolean loaded = false;

	@SubscribeEvent
	public void onPreRenderSpecials(RenderPlayerEvent.Specials.Pre event) {
		if (!started) {
			downloadCapes();
			started = true;
		}

		if (finished && !loaded) {
			TextureManager manager = Minecraft.getMinecraft().renderEngine;
			manager.loadTexture(CAPE_DATA, new CapeTexture(CAPE_IMAGE));
			manager.loadTexture(JEBJEB_CAPE_DATA, new CapeTexture(JEBJEB_CAPE_IMAGE));
			manager.loadTexture(KPR_CAPE_DATA, new CapeTexture(KPR_CAPE_IMAGE));
			loaded = true;
		}

		if (!loaded)
			return;

		if (event.entityPlayer instanceof AbstractClientPlayer)
			if (usersWithCapes.contains(event.entityPlayer.getCommandSenderName())) {
				AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;

				if (player.getLocationCape() == null)
					if (event.entityPlayer.getCommandSenderName().equals("Jeb_Jeb"))
						player.func_152121_a(MinecraftProfileTexture.Type.CAPE, JEBJEB_CAPE_DATA);
					else if (event.entityPlayer.getCommandSenderName().equals("KingPurpleRaptor"))
						player.func_152121_a(MinecraftProfileTexture.Type.CAPE, KPR_CAPE_DATA);
					else
						player.func_152121_a(MinecraftProfileTexture.Type.CAPE, CAPE_DATA);

				event.renderCape = true;
			}
	}

	private void downloadCapes() {
		new Thread(new CapeDownlaoder(), "CapeDownloader").start();
	}

	private static class CapeDownlaoder implements Runnable {

		@Override
		public void run() {
			try {
				Scanner scanner = new Scanner(new URL(Reference.USERS_WITH_CAPES_FILE).openStream());
				while (scanner.hasNext())
					usersWithCapes.add(scanner.nextLine());
				scanner.close();
			} catch (IOException e) {
			}

			finished = false;
			try {
				CAPE_IMAGE = ImageIO.read(new URL(Reference.CAPE_IMAGE_FILE));
			} catch (IOException e) {
			}

			try {
				JEBJEB_CAPE_IMAGE = ImageIO.read(new URL(Reference.JEBJEB_CAPE_IMAGE_FILE));
			} catch (IOException e) {
			}

			try {
				KPR_CAPE_IMAGE = ImageIO.read(new URL(Reference.KPR_CAPE_IMAGE_FILE));
			} catch (IOException e) {
			}
			finished = true;
		}
	}

	private static class CapeTexture extends AbstractTexture {

		final BufferedImage image;

		CapeTexture(BufferedImage image) {
			this.image = image;
		}

		@Override
		public void loadTexture(IResourceManager p_110551_1_) throws IOException {
			deleteGlTexture();
			TextureUtil.uploadTextureImageAllocate(getGlTextureId(), image, false, false);
		}
	}
}