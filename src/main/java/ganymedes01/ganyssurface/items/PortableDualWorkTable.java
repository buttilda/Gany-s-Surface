package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.handlers.KeyBindingHandler;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PortableDualWorkTable extends Item {

	public PortableDualWorkTable() {
		setMaxStackSize(1);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.PORTABLE_DUAL_WORK_TABLE_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.PORTABLE_DUAL_WORK_TABLE_NAME));
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

		TileEntityDualWorkTable tile = getTile(stack);

		HashMap<String, Integer> items = new HashMap<String, Integer>();
		for (int i = 0; i < tile.getSizeInventory(); i++) {
			ItemStack item = tile.getStackInSlot(i);
			if (tile.getStackInSlot(i) != null)
				if (items.containsKey(item.getDisplayName()))
					items.put(item.getDisplayName(), items.get(item.getDisplayName()) + item.stackSize);
				else
					items.put(item.getDisplayName(), item.stackSize);
		}

		for (Entry<String, Integer> entry : items.entrySet())
			list.add(entry.getValue() + "x " + entry.getKey());
	}

	public static TileEntityDualWorkTable getTile(ItemStack stack) {
		TileEntityDualWorkTable tile = new TileEntityDualWorkTable();
		if (!stack.hasTagCompound())
			return tile;

		tile.readFromNBT(stack.getTagCompound().getCompoundTag("Inventory"));
		return tile;
	}
}