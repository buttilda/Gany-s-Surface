package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Market extends BlockContainer implements IConfigurable {

	public Market() {
		super(Material.iron);
		setHardness(2.5F);
		setStepSound(soundTypeMetal);
		setBlockName(Utils.getUnlocalisedName(Strings.MARKET));
		setBlockTextureName(Utils.getBlockTexture(Strings.MARKET));
		setCreativeTab(GanysSurface.enableMarket ? GanysSurface.surfaceTab : null);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (player != null && player instanceof EntityPlayer) {
			TileEntityMarket tile = Utils.getTileEntity(world, x, y, z, TileEntityMarket.class);
			tile.setOwner((EntityPlayer) player);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityMarket tile = Utils.getTileEntity(world, x, y, z, TileEntityMarket.class);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.MARKET.ordinal(), world, x, y, z);
			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityMarket tile = Utils.getTileEntity(world, x, y, z, TileEntityMarket.class);
		if (tile != null)
			for (int i = 0; i < 7; i++)
				tile.setInventorySlotContents(i, null);
		InventoryUtils.dropInventoryContents(tile);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMarket();
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableMarket;
	}
}