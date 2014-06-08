package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.RenderIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.network.PacketHandler;
import ganymedes01.ganyssurface.tileentities.TileEntityItemDisplay;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

	public ItemDisplay() {
		super(Material.glass);
		setHardness(1.0F);
		setStepSound(soundTypeGlass);
		setCreativeTab(GanysSurface.surfaceTab);
		setBlockTextureName(Utils.getBlockTexture(Strings.ITEM_DISPLAY_NAME));
		setBlockName(Utils.getUnlocalizedName(Strings.ITEM_DISPLAY_NAME));
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item id, CreativeTabs tab, List list) {
		for (int i = 0; i < 16; i++)
			list.add(new ItemStack(id, 1, i));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityItemDisplay tile = Utils.getTileEntity(world, x, y, z, TileEntityItemDisplay.class);
			if (tile.getDisplayItem() == null && player.getCurrentEquippedItem() != null) {
				tile.addItemToDisplay(player.getCurrentEquippedItem());
				tile.markDirty();
				PacketHandler.sendToAll(tile.getPacket());
				player.getCurrentEquippedItem().stackSize--;
				if (player.getCurrentEquippedItem().stackSize <= 0)
					player.setCurrentItemOrArmor(0, null);
			} else if (tile.getDisplayItem() != null) {
				if (!player.inventory.addItemStackToInventory(tile.getDisplayItem().copy()))
					InventoryUtils.dropStack(world, x, y + 1, z, tile.getDisplayItem().copy());
				tile.addItemToDisplay(null);
				PacketHandler.sendToAll(tile.getPacket());
			}
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
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
	public int getRenderType() {
		return RenderIDs.ITEM_DISPLAY;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Utils.getBlockTexture(Strings.ITEM_DISPLAY_NAME));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityItemDisplay();
	}
}