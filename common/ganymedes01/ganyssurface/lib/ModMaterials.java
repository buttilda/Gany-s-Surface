package ganymedes01.ganyssurface.lib;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ModMaterials {

	// Armour
	public static final EnumArmorMaterial WOOD = EnumHelper.addArmorMaterial("WOOD", 8, new int[] { 2, 4, 3, 2 }, 9);

	// Tool
	public static final EnumToolMaterial ICE = EnumHelper.addToolMaterial("ICE", 2, 512, 6.0F, 2.0F, 14);

}
