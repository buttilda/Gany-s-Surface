package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class StorageCase extends Item implements IConfigurable {

	public StorageCase() {
		setTextureName(Utils.getItemTexture(Strings.STORAGE_CASE_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.STORAGE_CASE_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean isComplex) {
		if (stack.hasTagCompound()) {
			ItemStack s = getStoredStack(stack);

			int count = 0;
			int max = getStorageception(stack);
			while (count < max - 1) {
				s = getStoredStack(s);
				count++;
			}

			tooltip.add((int) Math.pow(9, max) + "x " + s.getDisplayName());
		}
	}

	public static int getStorageception(ItemStack stack) {
		ItemStack s = getStoredStack(stack);

		int count = 0;
		while (s != null) {
			s = getStoredStack(s);
			count++;
		}
		return count;
	}

	public static ItemStack getStoredStack(ItemStack storageCase) {
		if (storageCase != null && storageCase.hasTagCompound() && storageCase.getItem() == ModItems.storageCase)
			return ItemStack.loadItemStackFromNBT(storageCase.getTagCompound().getCompoundTag("stack"));
		return null;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String name = super.getItemStackDisplayName(stack);
		ItemStack s = getStoredStack(stack);

		int count = 0;
		int max = getStorageception(stack);
		while (count < max - 1) {
			s = getStoredStack(s);
			count++;
		}

		if (s != null)
			return String.format(name, s.getDisplayName());
		return String.format(name, "Empty");
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableEncasers;
	}
}