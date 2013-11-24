package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ChestPropellant extends BlockContainer {

	public static final int MAX_PILE_SIZE = 17;

	@SideOnly(Side.CLIENT)
	private Icon blockTop, blockSide;

	public ChestPropellant() {
		super(ModIDs.CHEST_PROPELLANT_ID, Material.rock);
		setHardness(1.0F);
		setLightOpacity(0);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CHEST_PROPELLANT_NAME));
		setTextureName(Utils.getBlockTexture(Strings.CHEST_PROPELLANT_NAME));
		setBlockBounds(0.07F, 0.0F, 0.07F, 0.93F, 1.0F, 0.93F);
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
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		ItemStack currentItem = player.inventory.getCurrentItem();
		if (currentItem != null && currentItem.itemID == blockID)
			return false;

		for (int i = 1; i < MAX_PILE_SIZE; i++) {
			TileEntity tile = world.getBlockTileEntity(x, y - i, z);
			if (tile instanceof IInventory) {
				if (!(tile instanceof TileEntityChestPropellant))
					if (world.getBlockId(x, y - i, z) < Block.blocksList.length) {
						Block block = Block.blocksList[world.getBlockId(x, y - i, z)];
						return block.onBlockActivated(world, x, y - i, z, player, side, hitX, hitY, hitZ);
					}
			} else
				return false;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityChestPropellant();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? blockTop : blockSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.CHEST_PROPELLANT_NAME) + "_side");
		blockTop = reg.registerIcon(Utils.getBlockTexture(Strings.CHEST_PROPELLANT_NAME) + "_top");
	}
}
