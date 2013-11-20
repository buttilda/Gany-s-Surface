package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.recipes.MarketSales;
import ganymedes01.ganyssurface.tileentities.TileEntityMarket;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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

public class Market extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon blockTopBottom, blockSides;

	protected Market() {
		super(ModIDs.MARKET_ID, Material.cloth);
		setBlockUnbreakable();
		setResistance(Float.MAX_VALUE);
		setStepSound(soundMetalFootstep);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.MARKET_NAME));
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityMarket)
			return ((TileEntityMarket) tile).isOwner(player.username) ? 1.0F / 30.0F : -1.0F;
		return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		if (player instanceof EntityPlayer) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if (tile != null && tile instanceof TileEntityMarket)
				((TileEntityMarket) tile).setOwner(((EntityPlayer) player).username);
		} else
			world.setBlockToAir(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		TileEntityMarket market = (TileEntityMarket) world.getBlockTileEntity(x, y, z);
		if (market != null)
			if (market.isOwner(player.username)) {
				if (player.isSneaking())
					player.openGui(GanysSurface.instance, GUIsID.MARKET_PRIVATE, world, x, y, z);
				else
					player.openGui(GanysSurface.instance, GUIsID.MARKET_PUBLIC, world, x, y, z);
			} else
				player.openGui(GanysSurface.instance, GUIsID.MARKET_PUBLIC, world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int oldID, int oldMeta) {
		TileEntityMarket tile = (TileEntityMarket) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			for (int i = 0; i < TileEntityMarket.OFFER_ONE; i++) {
				ItemStack stack = tile.getStackInSlot(i);
				if (stack != null)
					Utils.dropStack(world, x, y, z, stack);
			}
			for (ItemStack extraStack : tile.getExtraInventory())
				Utils.dropStack(world, x, y, z, extraStack);

			MarketSales.removeMarket(tile);
			world.func_96440_m(x, y, z, oldID);
		}
		super.breakBlock(world, x, y, z, oldID, oldMeta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side <= 1 ? blockTopBottom : blockSides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockTopBottom = reg.registerIcon(Utils.getBlockTexture(Strings.MARKET_NAME, true) + "topBottom");
		blockSides = reg.registerIcon(Utils.getBlockTexture(Strings.MARKET_NAME, true) + "sides");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMarket();
	}
}