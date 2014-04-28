package ganymedes01.ganyssurface.client.renderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class DownloadImageData extends ThreadDownloadImageData {

	private final String imageUrl;
	private final IImageBuffer imageBuffer;
	private BufferedImage bufferedImage;
	private Thread imageThread;
	private final SimpleTexture imageLocation;
	private boolean textureUploaded;

	public DownloadImageData(String imageUrl, ResourceLocation imageLoc, IImageBuffer imageBuffer) {
		super(imageUrl, imageLoc, imageBuffer);
		this.imageUrl = imageUrl;
		this.imageBuffer = imageBuffer;
		imageLocation = imageLoc != null ? new SimpleTexture(imageLoc) : null;
	}

	@Override
	public int getGlTextureId() {
		int texID = super.getGlTextureId();

		if (!textureUploaded && bufferedImage != null) {
			TextureUtil.uploadTextureImage(texID, bufferedImage);
			textureUploaded = true;
		}

		return texID;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	@Override
	public void setBufferedImage(BufferedImage buffer) {
		bufferedImage = buffer;
	}

	@Override
	public void loadTexture(IResourceManager manager) throws IOException {
		if (bufferedImage == null) {
			if (imageLocation != null) {
				imageLocation.loadTexture(manager);
				glTextureId = imageLocation.getGlTextureId();
			}
		} else
			TextureUtil.uploadTextureImage(getGlTextureId(), bufferedImage);

		if (imageThread == null) {
			imageThread = new DownloadImageDataINNER1(this);
			imageThread.setDaemon(true);
			imageThread.setName("Skin downloader: " + imageUrl);
			imageThread.start();
		}
	}

	public static ThreadDownloadImageData getDownloadImage(ResourceLocation resourceLoc, String textureURL, ResourceLocation imageLoc, IImageBuffer imageBuffer) {
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		ITextureObject tex = texturemanager.getTexture(resourceLoc);
		if (tex == null) {
			tex = new DownloadImageData(textureURL, imageLoc, imageBuffer);
			texturemanager.loadTexture(resourceLoc, tex);
		}

		return (ThreadDownloadImageData) tex;
	}

	private final class DownloadImageDataINNER1 extends Thread {
		private final DownloadImageData data;

		private DownloadImageDataINNER1(DownloadImageData data) {
			this.data = data;
		}

		@Override
		public void run() {
			HttpURLConnection httpConnection = null;

			try {
				httpConnection = (HttpURLConnection) new URL(data.imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
				httpConnection.setDoInput(true);
				httpConnection.setDoOutput(false);
				httpConnection.connect();

				if (httpConnection.getResponseCode() / 100 != 2)
					return;

				BufferedImage bufferedimage = ImageIO.read(httpConnection.getInputStream());

				if (data.imageBuffer != null)
					bufferedimage = data.imageBuffer.parseUserSkin(bufferedimage);

				data.setBufferedImage(bufferedimage);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (httpConnection != null)
					httpConnection.disconnect();
			}
		}
	}
}