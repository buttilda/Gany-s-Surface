package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
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

public class CupOfTea extends Item implements IConfigurable {

	public CupOfTea() {
		setMaxStackSize(1);
		setTextureName(Utils.getItemTexture(Strings.CUP_OF_TEA_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.CUP_OF_TEA_NAME));
		setCreativeTab(GanysSurface.enableTea ? GanysSurface.surfaceTab : null);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack item) {
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return 35;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote)
			if (stack != null && stack.stackSize > 0) {
				player.removePotionEffect(Potion.moveSlowdown.id);
				player.removePotionEffect(Potion.digSlowdown.id);
				player.removePotionEffect(Potion.weakness.id);

				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1200, 3));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 1200));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 1200, 3));
				player.addPotionEffect(new PotionEffect(Potion.hunger.id, 600));

				if (!player.capabilities.isCreativeMode) {
					stack.stackSize--;
					if (stack.stackSize <= 0)
						return new ItemStack(ModItems.emptyMug);

					InventoryUtils.addToPlayerInventory(player, new ItemStack(ModItems.emptyMug), player.posX, player.posY, player.posZ);
				}
			}

		return stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableTea;
	}
}
