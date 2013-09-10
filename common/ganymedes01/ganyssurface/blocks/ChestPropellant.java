package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityChestPropellant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ChestPropellant extends BlockContainer {

	public static final int MAX_PILE_SIZE = 16;

	public ChestPropellant(int id) {
		super(id, Material.rock);
		setHardness(1.0F);
		setLightOpacity(0);
		setCreativeTab(GanysSurface.surfaceTab);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.CHEST_PROPELLANT_NAME));
		setTextureName(Utils.getBlockTexture(Strings.CHEST_PROPELLANT_NAME, false));
		setBlockBounds(0.07F, 0.07F, 0.07F, 0.93F, 0.93F, 0.93F);
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
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		int metadata = 0;
		if (world.getBlockTileEntity(x, y - 1, z) instanceof IInventory)
			metadata = 1;
		if (world.getBlockTileEntity(x, y + 1, z) instanceof IInventory)
			metadata = 2;
		if (world.getBlockTileEntity(x, y + 1, z) instanceof IInventory && world.getBlockTileEntity(x, y - 1, z) instanceof IInventory)
			metadata = 3;

		world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
		switch (access.getBlockMetadata(x, y, z)) {
			case 0:
				setBlockBounds(0.07F, 0.07F, 0.07F, 0.93F, 0.93F, 0.93F);
				break;
			case 1:
				setBlockBounds(0.07F, 0.0F, 0.07F, 0.93F, 0.93F, 0.93F);
				break;
			case 2:
				setBlockBounds(0.07F, 0.07F, 0.07F, 0.93F, 1.0F, 0.93F);
				break;
			case 3:
				setBlockBounds(0.07F, 0.0F, 0.07F, 0.93F, 1.0F, 0.93F);
				break;
		}
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		for (int i = 0; i < MAX_PILE_SIZE; i++) {
			TileEntity tile = world.getBlockTileEntity(x, y - i, z);
			if (tile instanceof IInventory) {
				if (!(tile instanceof TileEntityChestPropellant))
					return Block.blocksList[world.getBlockId(x, y - i, z)].onBlockActivated(world, x, y - i, z, player, side, hitX, hitY, hitZ);
			} else
				return false;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityChestPropellant();
	}
}
