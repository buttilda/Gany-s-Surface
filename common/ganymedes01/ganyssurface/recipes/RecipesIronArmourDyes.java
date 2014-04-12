package ganymedes01.ganyssurface.recipes;

import ganymedes01.ganyssurface.items.ItemDyeableArmour;
import ganymedes01.ganyssurface.items.ModItems;

import java.util.ArrayList;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipesArmorDyes;
import net.minecraft.world.World;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class RecipesIronArmourDyes extends RecipesArmorDyes {

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		ItemStack stack = null;
		ArrayList list = new ArrayList();

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack2 = inventory.getStackInSlot(i);

			if (stack2 != null)
				if (stack2.getItem() instanceof ItemArmor) {
					ItemArmor armour = (ItemArmor) stack2.getItem();
					if (!isDyeableMaterial(armour.getArmorMaterial()) || stack != null)
						return false;
					stack = stack2;
				} else {
					if (stack2.itemID != Item.dyePowder.itemID)
						return false;
					list.add(stack2);
				}
		}

		return stack != null && !list.isEmpty();
	}

	private boolean isDyeableMaterial(EnumArmorMaterial material) {
		return material == EnumArmorMaterial.IRON || material == EnumArmorMaterial.CHAIN;
	}

	// Messy and stolen from vanilla
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		ItemStack result = null;
		int[] rgb = new int[3];
		int i = 0;
		int j = 0;
		ItemDyeableArmour armour = null;
		int k;
		int colour;
		float f;
		float f1;
		int i1;

		for (k = 0; k < inventory.getSizeInventory(); k++) {
			ItemStack craftStack = inventory.getStackInSlot(k);

			if (craftStack != null)
				if (craftStack.getItem() instanceof ItemDyeableArmour) {
					armour = (ItemDyeableArmour) craftStack.getItem();

					result = craftStack.copy();
					result.stackSize = 1;

					if (armour.hasColor(craftStack)) {
						colour = armour.getColor(result);
						f = (colour >> 16 & 255) / 255.0F;
						f1 = (colour >> 8 & 255) / 255.0F;
						float f2 = (colour & 255) / 255.0F;
						i = (int) (i + Math.max(f, Math.max(f1, f2)) * 255.0F);
						rgb[0] = (int) (rgb[0] + f * 255.0F);
						rgb[1] = (int) (rgb[1] + f1 * 255.0F);
						rgb[2] = (int) (rgb[2] + f2 * 255.0F);
						j++;
					}
				} else if (craftStack.getItem() instanceof ItemArmor) {
					if (!isDyeableMaterial(((ItemArmor) craftStack.getItem()).getArmorMaterial()))
						return null;

					result = craftStack.copy();
					if (craftStack.getItem() == Item.helmetIron)
						armour = (ItemDyeableArmour) ModItems.dyedIronHelmet;
					else if (craftStack.getItem() == Item.plateIron)
						armour = (ItemDyeableArmour) ModItems.dyedIronChestplate;
					else if (craftStack.getItem() == Item.legsIron)
						armour = (ItemDyeableArmour) ModItems.dyedIronLeggings;
					else if (craftStack.getItem() == Item.bootsIron)
						armour = (ItemDyeableArmour) ModItems.dyedIronBoots;
					else if (craftStack.getItem() == Item.helmetChain)
						armour = (ItemDyeableArmour) ModItems.dyedChainHelmet;
					else if (craftStack.getItem() == Item.plateChain)
						armour = (ItemDyeableArmour) ModItems.dyedChainChestplate;
					else if (craftStack.getItem() == Item.legsChain)
						armour = (ItemDyeableArmour) ModItems.dyedChainLeggings;
					else if (craftStack.getItem() == Item.bootsChain)
						armour = (ItemDyeableArmour) ModItems.dyedChainBoots;

					result.itemID = armour.itemID;
					result.stackSize = 1;

					if (armour.hasColor(craftStack)) {
						colour = armour.getColor(result);
						f = (colour >> 16 & 255) / 255.0F;
						f1 = (colour >> 8 & 255) / 255.0F;
						float f2 = (colour & 255) / 255.0F;
						i = (int) (i + Math.max(f, Math.max(f1, f2)) * 255.0F);
						rgb[0] = (int) (rgb[0] + f * 255.0F);
						rgb[1] = (int) (rgb[1] + f1 * 255.0F);
						rgb[2] = (int) (rgb[2] + f2 * 255.0F);
						j++;
					}
				} else {
					if (craftStack.itemID != Item.dyePowder.itemID)
						return null;

					float[] afloat = EntitySheep.fleeceColorTable[BlockColored.getBlockFromDye(craftStack.getItemDamage())];
					int j1 = (int) (afloat[0] * 255.0F);
					int k1 = (int) (afloat[1] * 255.0F);
					i1 = (int) (afloat[2] * 255.0F);
					i += Math.max(j1, Math.max(k1, i1));
					rgb[0] += j1;
					rgb[1] += k1;
					rgb[2] += i1;
					j++;
				}
		}

		if (armour == null)
			return null;
		else {
			k = rgb[0] / j;
			int l1 = rgb[1] / j;
			colour = rgb[2] / j;
			f = (float) i / (float) j;
			f1 = Math.max(k, Math.max(l1, colour));
			k = (int) (k * f / f1);
			l1 = (int) (l1 * f / f1);
			colour = (int) (colour * f / f1);
			i1 = (k << 8) + l1;
			i1 = (i1 << 8) + colour;
			armour.func_82813_b(result, i1);
			return result;
		}
	}
}