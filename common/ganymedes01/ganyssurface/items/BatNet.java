package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
	public boolean hitEntity(ItemStack item, EntityLivingBase attacked, EntityLivingBase player) {
		if (attacked instanceof EntityBat) {
			if (!player.worldObj.isRemote) {
				attacked.setDead();
				attacked.entityDropItem(new ItemStack(ModItems.pocketBat), 1.0F);
				item.damageItem(1, player);
			}
			return true;
		} else
			return false;
	}
}
