package ganymedes01.ganyssurface.items;

import java.util.List;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BatNet extends Item implements IConfigurable {

	public BatNet() {
		setFull3D();
		setMaxDamage(32);
		setMaxStackSize(1);
		setTextureName(Utils.getItemTexture(Strings.BAT_NET_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.BAT_NET_NAME));
		setCreativeTab(GanysSurface.enablePocketCritters ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(StatCollector.translateToLocal(Utils.getString("attacktocapture")));
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		int meta = getMetaFromCreature(target);
		if (meta >= 0) {
			if (!player.worldObj.isRemote) {
				ItemStack pocketBat = new ItemStack(ModItems.pocketCritter, 1, meta);
				if (((EntityLiving) target).hasCustomNameTag())
					pocketBat.setStackDisplayName(((EntityLiving) target).getCustomNameTag());
				target.setDead();
				InventoryUtils.addToPlayerInventory(player, pocketBat, target.posX, target.posY + 1, target.posZ);
				stack.damageItem(1, player);
				if (stack.getItemDamage() > stack.getMaxDamage())
					player.setCurrentItemOrArmor(0, null);
			}
			return true;
		} else
			return false;
	}

	public int getMetaFromCreature(Entity creature) {
		if (creature instanceof EntityBat)
			return 0;
		else if (creature instanceof EntitySquid)
			return 1;
		else
			return -1;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePocketCritters;
	}
}
