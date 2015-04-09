package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
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

public class MankyCupOfTea extends CupOfTea {

	public MankyCupOfTea() {
		setCreativeTab(null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.MANKY_CUP_OF_TEA_NAME));
	}

	@Override
	public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
		player.removePotionEffect(Potion.moveSpeed.id);
		player.removePotionEffect(Potion.digSpeed.id);
		player.removePotionEffect(Potion.jump.id);

		player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 1200, 3));
		player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 1200));
		player.addPotionEffect(new PotionEffect(Potion.weakness.id, 1200, 3));
		player.addPotionEffect(new PotionEffect(Potion.hunger.id, 900));
		return new ItemStack(ModItems.emptyMug);
	}
}