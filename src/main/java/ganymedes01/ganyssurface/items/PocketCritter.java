package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class PocketCritter extends Item implements IConfigurable {

	public PocketCritter() {
		setMaxDamage(0);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.POCKET_CRITTER_NAME));
		setCreativeTab(GanysSurface.enablePocketCritters ? GanysSurface.surfaceTab : null);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 0)
			return "item." + Reference.MOD_ID + ".pocketBat";
		else
			return "item." + Reference.MOD_ID + ".pocketSquid";
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(StatCollector.translateToLocal("pleaseletmefree" + stack.getItemDamage()));
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ("" + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name")).trim();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		else {
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];

			int entityID = -1;
			switch (stack.getItemDamage()) {
				case 0:
					entityID = 65;
					break;
				case 1:
					entityID = 94;
					break;
			}

			if (entityID > 0) {
				Entity entity = ItemMonsterPlacer.spawnCreature(world, entityID, x + 0.5D, y + 0.5D, z + 0.5D);

				if (entity != null) {
					((EntityLiving) entity).func_110163_bv();
					if (stack.hasDisplayName())
						((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

					if (!player.capabilities.isCreativeMode)
						stack.stackSize--;
				}
			}

			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (int i = 0; i < 2; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePocketCritters;
	}
}