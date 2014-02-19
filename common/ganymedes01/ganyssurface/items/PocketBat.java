package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
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

public class PocketBat extends Item {

	@SideOnly(Side.CLIENT)
	private Icon[] icon;

	public PocketBat() {
		super(ModIDs.POCKET_BAT_ID);
		setMaxDamage(0);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.POCKET_BAT_NAME));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 0)
			return super.getUnlocalizedName();
		else
			return "item." + Reference.MOD_ID + ".pocketSquid";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(StatCollector.translateToLocal("pleaseletmefree" + stack.getItemDamage()));
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return ("" + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name")).trim();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		else {
			int blockID = world.getBlockId(x, y, z);
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];

			int entityID;
			if (stack.getItemDamage() == 0)
				entityID = 65;
			else if (stack.getItemDamage() == 1)
				entityID = 94;
			else
				entityID = -1;

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
	public Icon getIconFromDamage(int meta) {
		return icon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tabs, List list) {
		list.add(new ItemStack(itemID, 1, 0));
		list.add(new ItemStack(itemID, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icon = new Icon[2];
		icon[0] = reg.registerIcon(Utils.getItemTexture(Strings.POCKET_BAT_NAME));
		icon[1] = reg.registerIcon(Utils.getItemTexture("pocketSquid"));
	}
}