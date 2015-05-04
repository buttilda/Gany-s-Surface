package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ItemDyeableArmour extends ItemArmor implements IConfigurable {

	public ItemDyeableArmour(ArmorMaterial material, int armourType) {
		super(material, 2, armourType);
		setMaxStackSize(1);
		setCreativeTab(null);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			int meta = world.getBlockMetadata(x, y, z);
			if (world.getBlock(x, y, z) == Blocks.cauldron && meta > 0) {
				removeColor(stack);
				world.setBlockMetadataWithNotify(x, y, z, meta - 1, 3);
				return true;
			}
		}
		return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return material.getItem() == getArmorMaterial().customCraftingMaterial;
	}

	@Override
	public boolean hasColor(ItemStack stack) {
		return !isValidMaterial() ? false : !stack.hasTagCompound() ? false : !stack.getTagCompound().hasKey("display") ? false : stack.getTagCompound().getCompoundTag("display").hasKey("color");
	}

	@Override
	public int getColor(ItemStack stack) {
		if (!isValidMaterial())
			return -1;
		else {
			NBTTagCompound data = stack.getTagCompound();

			if (data == null)
				return -1;
			else {
				NBTTagCompound nbtColour = data.getCompoundTag("display");
				return nbtColour == null ? -1 : nbtColour.hasKey("color") ? nbtColour.getInteger("color") : -1;
			}
		}
	}

	@Override
	public void removeColor(ItemStack stack) {
		if (isValidMaterial()) {
			NBTTagCompound nbt = stack.getTagCompound();

			if (nbt != null) {
				NBTTagCompound nbtDisplay = nbt.getCompoundTag("display");

				if (nbtDisplay.hasKey("color"))
					nbtDisplay.removeTag("color");
			}
		}
	}

	@Override
	public void func_82813_b(ItemStack stack, int colour) {
		if (!isValidMaterial())
			throw new UnsupportedOperationException("Can\'t dye non-iron or non-chain!");
		else {
			NBTTagCompound nbt = stack.getTagCompound();

			if (nbt == null) {
				nbt = new NBTTagCompound();
				stack.setTagCompound(nbt);
			}

			NBTTagCompound nbtDisplay = nbt.getCompoundTag("display");

			if (!nbt.hasKey("display"))
				nbt.setTag("display", nbtDisplay);

			nbtDisplay.setInteger("color", colour);
		}
	}

	private boolean isValidMaterial() {
		return getArmorMaterial() == ArmorMaterial.IRON || getArmorMaterial() == ArmorMaterial.CHAIN;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableDyedArmour;
	}
}