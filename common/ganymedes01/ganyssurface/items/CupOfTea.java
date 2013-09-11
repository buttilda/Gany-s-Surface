package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class CupOfTea extends Item {

	public CupOfTea(int id) {
		super(id);
		setMaxStackSize(1);
		setTextureName(Utils.getItemTexture(Strings.CUP_OF_TEA_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CUP_OF_TEA_NAME));
	}

	@Override
	public EnumAction getItemUseAction(ItemStack item) {
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return 50;
	}

	@Override
	public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
		player.removePotionEffect(Potion.moveSlowdown.id);
		player.removePotionEffect(Potion.digSlowdown.id);
		player.removePotionEffect(Potion.weakness.id);

		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1200, 3));
		player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 1200));
		player.addPotionEffect(new PotionEffect(Potion.jump.id, 1200, 3));
		player.addPotionEffect(new PotionEffect(Potion.hunger.id, 600));
		return new ItemStack(ModItems.emptyMug);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		player.setItemInUse(item, 35);
		return item;
	}
}
