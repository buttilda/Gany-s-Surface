package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.handlers.KeyBindingHandler;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PortableDualWorkTable extends Item implements IConfigurable {

	public PortableDualWorkTable() {
		setMaxStackSize(1);
		setTextureName(Utils.getItemTexture(Strings.PORTABLE_DUAL_WORK_TABLE_NAME));
		setCreativeTab(GanysSurface.enableWorkTables ? GanysSurface.surfaceTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.PORTABLE_DUAL_WORK_TABLE_NAME));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote)
			player.openGui(GanysSurface.instance, GUIsID.PORTABLE_DUAL_WORK_TABLE, world, player.inventory.currentItem, 0, 0);
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(String.format(StatCollector.translateToLocal("portableWorktable"), EnumChatFormatting.YELLOW + Keyboard.getKeyName(KeyBindingHandler.worktable.getKeyCode()) + EnumChatFormatting.GRAY));
	}

	public static TileEntityDualWorkTable getTile(ItemStack stack) {
		TileEntityDualWorkTable tile = new TileEntityDualWorkTable();
		if (!stack.hasTagCompound())
			return tile;

		tile.readFromNBT(stack.getTagCompound().getCompoundTag("Inventory"));
		return tile;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableWorkTables;
	}
}