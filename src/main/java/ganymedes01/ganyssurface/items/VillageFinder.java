package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.entities.EntityVillageFinder;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class VillageFinder extends Item implements IConfigurable {

	public VillageFinder() {
		setTextureName(Utils.getItemTexture(Strings.VILLAGE_FINDER));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.VILLAGE_FINDER));
		setCreativeTab(GanysSurface.enableVillageFinder ? GanysSurface.surfaceTab : null);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			ChunkCoordinates coords = Utils.findNearestVillage(world, (int) player.posX, (int) player.posY, (int) player.posZ);

			if (coords != null) {
				EntityVillageFinder villageEye = new EntityVillageFinder(world, player.posX, player.posY + 1.62D - player.yOffset, player.posZ);
				villageEye.moveTowards(coords.posX, coords.posY, coords.posZ);
				world.spawnEntityInWorld(villageEye);
				world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				world.playAuxSFXAtEntity((EntityPlayer) null, 1002, (int) player.posX, (int) player.posY, (int) player.posZ, 0);

				if (!player.capabilities.isCreativeMode)
					stack.stackSize--;
			}
		}

		return stack;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableVillageFinder;
	}
}