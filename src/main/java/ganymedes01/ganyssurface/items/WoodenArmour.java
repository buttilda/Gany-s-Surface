package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModMaterials;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class WoodenArmour extends ItemArmor implements IConfigurable {

	private String texturePath, iconPath;

	public WoodenArmour(int type) {
		super(ModMaterials.WOOD, 0, type);
		setMaxStackSize(1);
		setArmourType(type);
		setCreativeTab(GanysSurface.enableWoodenArmour ? GanysSurface.surfaceTab : null);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (getDamage(stack) >= getMaxDamage()) {
			player.inventory.armorInventory[3 - armorType] = null;
			renderBrokenItemStack(player, stack, new Random());
			return;
		}
		if (player.isBurning())
			stack.damageItem(1, player);
	}

	private void renderBrokenItemStack(EntityPlayer player, ItemStack stack, Random rand) {
		player.playSound("random.bowhit", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F);
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		for (ItemStack stack : OreDictionary.getOres("logWood"))
			if (stack.getItem() == material.getItem())
				return true;
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(iconPath);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		return texturePath;
	}

	private void setArmourType(int piece) {
		switch (piece) {
			case 0:
				setUnlocalizedName(Utils.getUnlocalisedName(Strings.WOODEN_HELMET_NAME));
				texturePath = Utils.getArmourTexture(ModMaterials.WOOD.name(), 1);
				iconPath = Utils.getItemTexture(Strings.WOODEN_HELMET_NAME);
				break;
			case 1:
				setUnlocalizedName(Utils.getUnlocalisedName(Strings.WOODEN_CHESTPLATE_NAME));
				texturePath = Utils.getArmourTexture(ModMaterials.WOOD.name(), 1);
				iconPath = Utils.getItemTexture(Strings.WOODEN_CHESTPLATE_NAME);
				break;
			case 2:
				setUnlocalizedName(Utils.getUnlocalisedName(Strings.WOODEN_LEGGINGS_NAME));
				texturePath = Utils.getArmourTexture(ModMaterials.WOOD.name(), 2);
				iconPath = Utils.getItemTexture(Strings.WOODEN_LEGGINGS_NAME);
				break;
			case 3:
				setUnlocalizedName(Utils.getUnlocalisedName(Strings.WOODEN_BOOTS_NAME));
				texturePath = Utils.getArmourTexture(ModMaterials.WOOD.name(), 1);
				iconPath = Utils.getItemTexture(Strings.WOODEN_BOOTS_NAME);
				break;
		}
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWoodenArmour;
	}
}