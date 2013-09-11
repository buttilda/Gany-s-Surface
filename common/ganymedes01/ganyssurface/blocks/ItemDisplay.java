package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ItemDisplay extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon blockBottom;

	public ItemDisplay(int id) {
		super(id, Material.glass);
		setHardness(1.0F);
		setStepSound(soundGlassFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ITEM_DISPLAY_NAME));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityItemDisplay tile = (TileEntityItemDisplay) world.getBlockTileEntity(x, y, z);
			if (tile.getDisplayItem() == null && player.getCurrentEquippedItem() != null) {
				tile.addItemToDisplay(player.getCurrentEquippedItem());
				GanysSurface.proxy.handleTileWithSingleDisplayItemPacket(x, y, z, player.getCurrentEquippedItem().itemID, player.getCurrentEquippedItem().getItemDamage(), 1);
				player.getCurrentEquippedItem().stackSize--;
				if (player.getCurrentEquippedItem().stackSize <= 0)
					player.setCurrentItemOrArmor(0, null);
			} else {
				Utils.dropStack(world, x, y + 1, z, tile.getDisplayItem().copy());
				tile.addItemToDisplay(null);
				GanysSurface.proxy.handleTileWithSingleDisplayItemPacket(x, y, z, Item.appleGold.itemID, 0, 0);
			}
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityItemDisplay tile = (TileEntityItemDisplay) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			ItemStack stack = tile.getDisplayItem();
			if (stack != null)
				Utils.dropStack(world, x, y, z, stack);
			world.func_96440_m(x, y, z, par5);
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityItemDisplay();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 0 ? blockBottom : blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon(Utils.getBlockTexture(Strings.ITEM_DISPLAY_NAME, true) + "rest");
		blockBottom = reg.registerIcon(Utils.getBlockTexture(Strings.ITEM_DISPLAY_NAME, true) + "bottom");
	}
}
