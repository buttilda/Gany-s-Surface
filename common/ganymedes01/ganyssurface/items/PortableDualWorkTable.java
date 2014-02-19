package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityDualWorkTable;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PortableDualWorkTable extends Item {

	public PortableDualWorkTable() {
		super(ModIDs.PORTABLE_DUAL_WORK_TABLE_ID);
		setCreativeTab(GanysSurface.surfaceTab);
		setTextureName(Utils.getItemTexture(Strings.PORTABLE_DUAL_WORK_TABLE_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.PORTABLE_DUAL_WORK_TABLE_NAME));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.openGui(GanysSurface.instance, GUIsID.PORTABLE_DUAL_WORK_TABLE, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		TileEntityDualWorkTable tile = getTile(stack);
		for (int i = 0; i < tile.getSizeInventory(); i++) {
			ItemStack item = tile.getStackInSlot(i);
			if (tile.getStackInSlot(i) != null)
				list.add(item.stackSize + "x " + item.getDisplayName());
		}
	}

	public static TileEntityDualWorkTable getTile(ItemStack stack) {
		TileEntityDualWorkTable tile = new TileEntityDualWorkTable();
		if (!stack.hasTagCompound())
			return tile;

		tile.readFromNBT(stack.getTagCompound().getCompoundTag("Inventory"));
		return tile;
	}
}