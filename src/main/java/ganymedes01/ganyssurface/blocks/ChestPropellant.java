package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class ChestPropellant extends BlockContainer implements IConfigurable {

	public static final int MAX_PILE_SIZE = 17;

	@SideOnly(Side.CLIENT)
	private IIcon blockTop, blockSide;

	public ChestPropellant() {
		super(Material.rock);
		setHardness(1.0F);
		setLightOpacity(0);
		setBlockBounds(0.07F, 0.0F, 0.07F, 0.93F, 1.0F, 0.93F);
		setBlockName(Utils.getUnlocalisedName(Strings.CHEST_PROPELLANT_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.CHEST_PROPELLANT_NAME));
		setCreativeTab(GanysSurface.enableChestPropellant ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		ItemStack currentItem = player.inventory.getCurrentItem();
		if (currentItem != null && currentItem.getItem() == Item.getItemFromBlock(this))
			return false;

		for (int i = 1; i < MAX_PILE_SIZE; i++) {
			TileEntity tile = world.getTileEntity(x, y - i, z);
			if (tile instanceof IInventory)
				if (!(tile instanceof TileEntityChestPropellant))
					return world.getBlock(x, y - i, z).onBlockActivated(world, x, y - i, z, player, side, hitX, hitY, hitZ);
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityChestPropellant();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? blockTop : blockSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.CHEST_PROPELLANT_NAME) + "_side");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.CHEST_PROPELLANT_NAME) + "_top");
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableChestPropellant;
	}
}