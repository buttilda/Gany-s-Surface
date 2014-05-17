package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.recipes.MarketSales;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class Market extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon blockTopBottom, blockSides;

	protected Market() {
		super(Material.cloth);
		setBlockUnbreakable();
		setResistance(Float.MAX_VALUE);
		setStepSound(soundTypeMetal);
		if (GanysSurface.enableMarket)
			setCreativeTab(GanysSurface.surfaceTab);
		setBlockName(Utils.getUnlocalizedName(Strings.MARKET_NAME));
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		TileEntityMarket tile = Utils.getTileEntity(world, x, y, z, TileEntityMarket.class);
		if (tile != null)
			return tile.isOwner(player.getCommandSenderName()) ? 1.0F / 30.0F : -1.0F;
		return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (player instanceof EntityPlayer) {
			TileEntityMarket tile = Utils.getTileEntity(world, x, y, z, TileEntityMarket.class);
			if (tile != null)
				tile.setOwner(((EntityPlayer) player).getCommandSenderName());
		} else
			world.setBlockToAir(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		TileEntityMarket market = Utils.getTileEntity(world, x, y, z, TileEntityMarket.class);
		if (market != null)
			if (market.isOwner(player.getCommandSenderName())) {
				if (player.isSneaking())
					player.openGui(GanysSurface.instance, GUIsID.MARKET_PRIVATE, world, x, y, z);
				else
					player.openGui(GanysSurface.instance, GUIsID.MARKET_PUBLIC, world, x, y, z);
			} else
				player.openGui(GanysSurface.instance, GUIsID.MARKET_PUBLIC, world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityMarket tile = Utils.getTileEntity(world, x, y, z, TileEntityMarket.class);
		if (tile != null) {
			InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
			MarketSales.removeMarket(tile);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side <= 1 ? blockTopBottom : blockSides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockTopBottom = reg.registerIcon(Utils.getBlockTexture(Strings.MARKET_NAME) + "_topBottom");
		blockSides = reg.registerIcon(Utils.getBlockTexture(Strings.MARKET_NAME) + "_sides");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMarket();
	}
}