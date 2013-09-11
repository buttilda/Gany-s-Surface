package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModMaterials;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
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

public class WoodenArmour extends ItemArmor {

	private String texturePath, iconPath;

	public WoodenArmour(int id, int type) {
		super(id, ModMaterials.WOOD, 0, type);
		setMaxStackSize(1);
		setArmourType(type);
		setCreativeTab(GanysSurface.surfaceTab);
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack) {
		if (getDamage(itemStack) >= getMaxDamage()) {
			player.inventory.armorInventory[3 - armorType] = null;
			renderBrokenItemStack(player, itemStack, new Random());
			return;
		}
		if (player.isBurning())
			itemStack.damageItem(1, player);
	}

	private void renderBrokenItemStack(EntityPlayer player, ItemStack par1ItemStack, Random rand) {
		player.playSound("random.bowhit", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F);

		for (int i = 0; i < 5; ++i) {
			Vec3 vec3 = player.worldObj.getWorldVec3Pool().getVecFromPool((rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
			vec3.rotateAroundX(-player.rotationPitch * (float) Math.PI / 180.0F);
			vec3.rotateAroundY(-player.rotationYaw * (float) Math.PI / 180.0F);
			Vec3 vec31 = player.worldObj.getWorldVec3Pool().getVecFromPool((rand.nextFloat() - 0.5D) * 0.3D, -rand.nextFloat() * 0.6D - 0.3D, 0.6D);
			vec31.rotateAroundX(-player.rotationPitch * (float) Math.PI / 180.0F);
			vec31.rotateAroundY(-player.rotationYaw * (float) Math.PI / 180.0F);
			vec31 = vec31.addVector(player.posX, player.posY + player.getEyeHeight(), player.posZ);
			player.worldObj.spawnParticle("iconcrack_" + par1ItemStack.getItem().itemID, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord);
		}
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
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(iconPath);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return texturePath;
	}

	private void setArmourType(int piece) {
		switch (piece) {
			case 0:
				setUnlocalizedName(Utils.getUnlocalizedName(Strings.WOODEN_HELMET_NAME));
				texturePath = Utils.getArmourTexture(ModMaterials.WOOD.name(), 1);
				iconPath = Utils.getItemTexture(Strings.WOODEN_HELMET_NAME);
				break;
			case 1:
				setUnlocalizedName(Utils.getUnlocalizedName(Strings.WOODEN_CHESTPLATE_NAME));
				texturePath = Utils.getArmourTexture(ModMaterials.WOOD.name(), 1);
				iconPath = Utils.getItemTexture(Strings.WOODEN_CHESTPLATE_NAME);
				break;
			case 2:
				setUnlocalizedName(Utils.getUnlocalizedName(Strings.WOODEN_LEGGINGS_NAME));
				texturePath = Utils.getArmourTexture(ModMaterials.WOOD.name(), 2);
				iconPath = Utils.getItemTexture(Strings.WOODEN_LEGGINGS_NAME);
				break;
			case 3:
				setUnlocalizedName(Utils.getUnlocalizedName(Strings.WOODEN_BOOTS_NAME));
				texturePath = Utils.getArmourTexture(ModMaterials.WOOD.name(), 1);
				iconPath = Utils.getItemTexture(Strings.WOODEN_BOOTS_NAME);
				break;
		}
	}
}
