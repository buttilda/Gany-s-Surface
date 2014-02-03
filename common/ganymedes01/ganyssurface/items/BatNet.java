package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IRepairable;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class BatNet extends Item implements IRepairable {

	public BatNet() {
		super(ModIDs.BAT_NET_ID);
		setFull3D();
		setMaxDamage(32);
		setMaxStackSize(1);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.BAT_NET_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.BAT_NET_NAME));
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(StatCollector.translateToLocal("attacktocapture"));
	}

	@Override
	public boolean onLeftClickEntity(ItemStack item, EntityPlayer player, Entity target) {
		int meta = getMetaFromCreature(target);
		if (meta > 0) {
			if (!player.worldObj.isRemote) {
				ItemStack pocketBat = new ItemStack(ModItems.pocketBat, 1, meta);
				if (((EntityLiving) target).hasCustomNameTag())
					pocketBat.setItemName(((EntityBat) target).getCustomNameTag());
				target.setDead();
				if (!player.inventory.addItemStackToInventory(pocketBat))
					target.entityDropItem(pocketBat, 1.0F);
				item.damageItem(1, player);
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
}