package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.tileentities.TileEntityWoodSign;

import java.lang.reflect.Field;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class GuiWoodSign extends GuiEditSign {

	private final TileEntityWoodSign sign;
	private final Field editLineField, updateCounterField;

	public GuiWoodSign(TileEntityWoodSign sign) {
		super(sign);
		this.sign = sign;

		editLineField = ReflectionHelper.findField(GuiEditSign.class, "field_146851_h", "editLine");
		editLineField.setAccessible(true);
		updateCounterField = ReflectionHelper.findField(GuiEditSign.class, "field_146849_g", "updateCounter");
		updateCounterField.setAccessible(true);
	}

	@Override
	public void drawScreen(int x, int y, float p_73863_3_) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, I18n.format("sign.edit", new Object[0]), width / 2, 40, 16777215);
		GL11.glPushMatrix();
		GL11.glTranslatef(width / 2, 0.0F, 50.0F);
		float f1 = 93.75F;
		GL11.glScalef(-f1, -f1, -f1);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

		if (sign.isStanding) {
			float f2 = sign.getBlockMetadata() * 360 / 16.0F;
			GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
		} else {
			int meta = sign.getBlockMetadata();
			float rotation = 0.0F;
			if (meta == 2)
				rotation = 180.0F;
			if (meta == 4)
				rotation = 90.0F;
			if (meta == 5)
				rotation = -90.0F;
			GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
		}

		try {
			if (updateCounterField.getInt(this) / 6 % 2 == 0)
				sign.lineBeingEdited = editLineField.getInt(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TileEntityRendererDispatcher.instance.renderTileEntityAt(sign, -0.5D, -0.75D, -0.5D, 0.0F);
		sign.lineBeingEdited = -1;
		GL11.glPopMatrix();

		// super super
		for (int k = 0; k < buttonList.size(); k++)
			((GuiButton) buttonList.get(k)).drawButton(mc, x, y);
		for (int k = 0; k < labelList.size(); k++)
			((GuiLabel) labelList.get(k)).func_146159_a(mc, x, y);
	}
}