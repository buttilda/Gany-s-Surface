package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BatNet extends Item {

	public BatNet(int id) {
		super(id);
		setFull3D();
		setMaxDamage(32);
		setMaxStackSize(1);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.BAT_NET_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.BAT_NET_NAME));
	}

	@Override
	public boolean onLeftClickEntity(ItemStack item, EntityPlayer player, Entity target) {
		if (target instanceof EntityBat) {
			if (!player.worldObj.isRemote) {
				target.setDead();
				target.entityDropItem(new ItemStack(ModItems.pocketBat), 1.0F);
				item.damageItem(1, player);
			}
			return true;
		} else
			return false;
	}
}
