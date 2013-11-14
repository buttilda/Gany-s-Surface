package ganymedes01.ganyssurface.items;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ItemDyeableArmour extends ItemArmor {

	public ItemDyeableArmour(int id, int armourType) {
		super(id, EnumArmorMaterial.IRON, 2, armourType);
		setMaxStackSize(1);
		setCreativeTab(null);
	}

	@Override
	public boolean hasColor(ItemStack par1ItemStack) {
		return getArmorMaterial() != EnumArmorMaterial.IRON ? false : !par1ItemStack.hasTagCompound() ? false : !par1ItemStack.getTagCompound().hasKey("display") ? false : par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color");
	}

	@Override
	public int getColor(ItemStack stack) {
		if (getArmorMaterial() != EnumArmorMaterial.IRON)
			return -1;
		else {
			NBTTagCompound data = stack.getTagCompound();

			if (data == null)
				return -1;
			else {
				NBTTagCompound colourData = data.getCompoundTag("display");
				return colourData == null ? -1 : colourData.hasKey("color") ? colourData.getInteger("color") : -1;
			}
		}
	}

	@Override
	public void removeColor(ItemStack stack) {
		if (getArmorMaterial() == EnumArmorMaterial.IRON) {
			NBTTagCompound nbttagcompound = stack.getTagCompound();

			if (nbttagcompound != null) {
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

				if (nbttagcompound1.hasKey("color"))
					nbttagcompound1.removeTag("color");
			}
		}
	}

	@Override
	public void func_82813_b(ItemStack stack, int colour) {
		if (getArmorMaterial() != EnumArmorMaterial.IRON)
			throw new UnsupportedOperationException("Can\'t dye non-iron!");
		else {
			NBTTagCompound nbttagcompound = stack.getTagCompound();

			if (nbttagcompound == null) {
				nbttagcompound = new NBTTagCompound();
				stack.setTagCompound(nbttagcompound);
			}

			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (!nbttagcompound.hasKey("display"))
				nbttagcompound.setCompoundTag("display", nbttagcompound1);

			nbttagcompound1.setInteger("color", colour);
		}
	}
}