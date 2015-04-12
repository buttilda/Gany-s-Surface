package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.client.OpenGLHelper;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.inventory.ContainerGearalyser;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class GuiGearalyser extends GuiGanysSurface {

	private static final int WHITE = Utils.getColour(255, 255, 255);

	public GuiGearalyser(InventoryPlayer player) {
		super(new ContainerGearalyser(player));
		ySize = 193;
		xSize = 199;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String title = StatCollector.translateToLocal(Utils.getConainerName(Strings.GEARALYSER_NAME));
		fontRendererObj.drawString(title, xSize / 2 - fontRendererObj.getStringWidth(title) / 2, 6, BLACK);

		ItemStack stack = (ItemStack) inventorySlots.inventoryItemStacks.get(0);
		if (stack == null)
			return;
		Item item = stack.getItem();
		if (item instanceof ItemArmor) {
			ArmorMaterial material = ((ItemArmor) item).getArmorMaterial();
			int type = ((ItemArmor) item).armorType;
			fontRendererObj.drawString(StatCollector.translateToLocal("Material: " + material.toString().toLowerCase()), 34, 20, BLACK);
			fontRendererObj.drawString(StatCollector.translateToLocal("Enchantability: " + material.getEnchantability()), 10, 38, WHITE);
			fontRendererObj.drawString(StatCollector.translateToLocal("Durability: " + stack.getItemDamage() + "/" + material.getDurability(type)), 10, 47, WHITE);
			fontRendererObj.drawString(StatCollector.translateToLocal("Damage Reduction: " + material.getDamageReductionAmount(type)), 10, 56, WHITE);
		} else if (item instanceof ItemTool || item instanceof ItemHoe || item instanceof ItemSword) {
			ToolMaterial material;
			if (item instanceof ItemHoe)
				material = ToolMaterial.valueOf(((ItemHoe) item).getToolMaterialName());
			else if (item instanceof ItemSword)
				material = ToolMaterial.valueOf(((ItemSword) item).getToolMaterialName());
			else
				material = ((ItemTool) item).func_150913_i();
			fontRendererObj.drawString(StatCollector.translateToLocal("Material: " + material.toString().toLowerCase()), 34, 20, BLACK);
			fontRendererObj.drawString(StatCollector.translateToLocal("Harvest Level: " + material.getHarvestLevel()), 10, 38, WHITE);
			fontRendererObj.drawString(StatCollector.translateToLocal("Durability: " + stack.getItemDamage() + "/" + material.getMaxUses()), 10, 47, WHITE);
			fontRendererObj.drawString(StatCollector.translateToLocal("Efficiency: " + material.getEfficiencyOnProperMaterial()), 10, 56, WHITE);
			fontRendererObj.drawString(StatCollector.translateToLocal("Damage: " + material.getDamageVsEntity()), 10, 65, WHITE);
			fontRendererObj.drawString(StatCollector.translateToLocal("Enchantability: " + material.getEnchantability()), 10, 74, WHITE);
		} else
			fontRendererObj.drawString(StatCollector.translateToLocal("It's a gear!"), 10, 38, WHITE);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		OpenGLHelper.colour(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Utils.getGUITexture(Strings.GEARALYSER_NAME)));
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}