package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.blocks.BlockWoodChest;
import ganymedes01.ganyssurface.blocks.BlockWoodChest.ChestType;
import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class TileEntityWoodChestRenderer extends TileEntitySpecialRenderer {

	private final ModelChest model_normal = new ModelChest();
	private final ModelChest model_large = new ModelLargeChest();
	public static final Map<ChestType, ResourceLocation> normal_textures = new HashMap<ChestType, ResourceLocation>();
	public static final Map<ChestType, ResourceLocation> large_textures = new HashMap<ChestType, ResourceLocation>();

	public TileEntityWoodChestRenderer() {
		if (!GanysSurface.enableDynamicTextureChests)
			for (ChestType type : ChestType.values()) {
				normal_textures.put(type, Utils.getResource(Utils.getEntityTexture("chest_normal_" + type.toString().toLowerCase())));
				large_textures.put(type, Utils.getResource(Utils.getEntityTexture("chest_large_" + type.toString().toLowerCase())));
			}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
		TileEntityChest chest = (TileEntityChest) tile;
		int meta;
		ChestType type = BlockWoodChest.getType(chest.func_145980_j());

		if (!chest.hasWorldObj())
			meta = 0;
		else {
			Block block = chest.getBlockType();
			meta = chest.getBlockMetadata();

			if (block instanceof BlockChest && meta == 0) {
				try {
					((BlockChest) block).func_149954_e(chest.getWorldObj(), chest.xCoord, chest.yCoord, chest.zCoord);
				} catch (ClassCastException e) {
					FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", chest.xCoord, chest.yCoord, chest.zCoord);
				}
				meta = chest.getBlockMetadata();
			}

			chest.checkForAdjacentChests();
		}

		if (chest.adjacentChestZNeg == null && chest.adjacentChestXNeg == null) {
			ModelChest model;

			boolean isNormal = chest.adjacentChestXPos == null && chest.adjacentChestZPos == null;
			model = isNormal ? model_normal : model_large;
			bindTexture(makeChestTexture(type, isNormal));

			OpenGLHelper.pushMatrix();
			OpenGLHelper.enableRescaleNormal();
			OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
			OpenGLHelper.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
			OpenGLHelper.scale(1.0F, -1.0F, -1.0F);
			OpenGLHelper.translate(0.5F, 0.5F, 0.5F);

			short rotation = 0;
			if (meta == 2)
				rotation = 180;
			if (meta == 3)
				rotation = 0;
			if (meta == 4)
				rotation = 90;
			if (meta == 5)
				rotation = -90;
			if (meta == 2 && chest.adjacentChestXPos != null)
				OpenGLHelper.translate(1.0F, 0.0F, 0.0F);
			if (meta == 5 && chest.adjacentChestZPos != null)
				OpenGLHelper.translate(0.0F, 0.0F, -1.0F);

			OpenGLHelper.rotate(rotation, 0.0F, 1.0F, 0.0F);
			OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

			float angle = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * partialTicks;
			float angle2;
			if (chest.adjacentChestZNeg != null) {
				angle2 = chest.adjacentChestZNeg.prevLidAngle + (chest.adjacentChestZNeg.lidAngle - chest.adjacentChestZNeg.prevLidAngle) * partialTicks;
				if (angle2 > angle)
					angle = angle2;
			}
			if (chest.adjacentChestXNeg != null) {
				angle2 = chest.adjacentChestXNeg.prevLidAngle + (chest.adjacentChestXNeg.lidAngle - chest.adjacentChestXNeg.prevLidAngle) * partialTicks;
				if (angle2 > angle)
					angle = angle2;
			}

			angle = 1.0F - angle;
			angle = 1.0F - angle * angle * angle;
			model.chestLid.rotateAngleX = -(angle * (float) Math.PI / 2.0F);
			model.renderAll();
			OpenGLHelper.disableRescaleNormal();
			OpenGLHelper.popMatrix();
			OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	public static ResourceLocation makeChestTexture(ChestType type, boolean normal) {
		Map<ChestType, ResourceLocation> textures = normal ? normal_textures : large_textures;

		if (textures.containsKey(type))
			return textures.get(type);

		String size = normal ? "normal" : "large";
		ResourceLocation res = Utils.getResource("textures/blocks/planks_" + type.toString().toLowerCase() + ".png");
		ResourceLocation out = Utils.getResource(Utils.getEntityTexture("chest_" + size + "_outline"));
		try {
			BufferedImage image = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream());
			BufferedImage outline = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(out).getInputStream());
			BufferedImage result = new BufferedImage(image.getWidth() * (normal ? 4 : 8), image.getHeight() * 4, BufferedImage.TYPE_INT_ARGB);
			int width = image.getWidth();
			int height = image.getHeight();

			for (int i = 0; i < (normal ? 4 : 8); i++)
				for (int j = 0; j < 4; j++)
					for (int x = 0; x < image.getWidth(); x++)
						for (int y = 0; y < image.getHeight(); y++)
							result.setRGB(x + i * width, y + j * height, image.getRGB(x, y));

			result = merge(result, outline);

			ResourceLocation loc = Utils.getResource(Utils.getEntityTexture("chest_" + size + "_" + type.toString().toLowerCase()));
			Minecraft.getMinecraft().getTextureManager().loadTexture(loc, new DynamicTexture(result));
			textures.put(type, loc);
			return loc;
		} catch (IOException e) {
			e.printStackTrace();
			textures.put(type, res);
			return res;
		}
	}

	public static BufferedImage merge(BufferedImage img1, BufferedImage... imgs) {
		BufferedImage merged = copy(img1);

		for (BufferedImage img2 : imgs) {
			if (merged.getWidth() != img2.getWidth() || merged.getHeight() != img2.getHeight()) {
				float wScale = (float) merged.getWidth() / (float) img2.getWidth();
				float hScale = (float) merged.getHeight() / (float) img2.getHeight();

				img2 = scale(img2, wScale, hScale);
			}

			merged = blend(merged, img2);
		}

		return merged;
	}

	private static BufferedImage copy(BufferedImage src) {
		BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

		Object srcbuf, dstbuf;
		int length;

		if (src.getRaster().getDataBuffer().getDataType() == DataBuffer.TYPE_BYTE) {
			srcbuf = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();
			dstbuf = ((DataBufferByte) dst.getRaster().getDataBuffer()).getData();
			length = ((byte[]) dstbuf).length;
		} else {
			srcbuf = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
			dstbuf = ((DataBufferInt) dst.getRaster().getDataBuffer()).getData();
			length = ((int[]) dstbuf).length;
		}

		System.arraycopy(srcbuf, 0, dstbuf, 0, length);

		return dst;
	}

	private static BufferedImage blend(BufferedImage image, BufferedImage overlay) {
		int w = Math.max(image.getWidth(), overlay.getWidth());
		int h = Math.max(image.getHeight(), overlay.getHeight());
		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics g = combined.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.drawImage(overlay, 0, 0, null);

		return combined;
	}

	private static BufferedImage scale(BufferedImage image, float widthScale, float heightScale) {
		BufferedImage img = new BufferedImage((int) (image.getWidth() * widthScale), (int) (image.getHeight() * heightScale), image.getType());

		Graphics2D grph = (Graphics2D) img.getGraphics();
		grph.scale(widthScale, heightScale);
		grph.drawImage(image, 0, 0, null);
		grph.dispose();

		return img;
	}
}