package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class DyedChainArmour extends ItemDyeableArmour {

	public DyedChainArmour(int type) {
		super(ArmorMaterial.CHAIN, type);

		switch (type) {
			case 0:
				setUnlocalizedName(Utils.getUnlocalisedName(Strings.DYED_CHAIN_HELMET_NAME));
				setTextureName("chainmail_helmet");
				break;
			case 1:
				setUnlocalizedName(Utils.getUnlocalisedName(Strings.DYED_CHAIN_CHESTPLATE_NAME));
				setTextureName("chainmail_chestplate");
				break;
			case 2:
				setUnlocalizedName(Utils.getUnlocalisedName(Strings.DYED_CHAIN_LEGGINGS_NAME));
				setTextureName("chainmail_leggings");
				break;
			case 3:
				setUnlocalizedName(Utils.getUnlocalisedName(Strings.DYED_CHAIN_BOOTS_NAME));
				setTextureName("chainmail_boots");
				break;
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (type != null)
			return Reference.ARMOUR_TEXTURE_PATH + "dyedArmour_Overlay.png";

		if (slot == 2)
			return "textures/models/armor/chainmail_layer_2.png";
		else
			return "textures/models/armor/chainmail_layer_1.png";
	}
}