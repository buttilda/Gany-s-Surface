package ganymedes01.ganyssurface.client.renderer.tileentity;

import ganymedes01.ganyssurface.blocks.BlockWoodChest;
import ganymedes01.ganyssurface.blocks.BlockWoodChest.ChestType;
import ganymedes01.ganyssurface.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
	private static final Map<ChestType, ResourceLocation> large_textures = new HashMap<ChestType, ResourceLocation>();

	public TileEntityWoodChestRenderer() {
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

			if (chest.adjacentChestXPos == null && chest.adjacentChestZPos == null) {
				model = model_normal;
				bindTexture(normal_textures.get(type));
			} else {
				model = model_large;
				bindTexture(large_textures.get(type));
			}

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);

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
				GL11.glTranslatef(1.0F, 0.0F, 0.0F);
			if (meta == 5 && chest.adjacentChestZPos != null)
				GL11.glTranslatef(0.0F, 0.0F, -1.0F);

			GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

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
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}