package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.items.ModItems;
import ganymedes01.ganyssurface.lib.ModIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
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

public class PoopBlock extends Block {

	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public PoopBlock() {
		super(ModIDs.POOP_BLOCK_ID, Material.clay);
		float pixel = 1.0F / 16.0F;
		setBlockBounds(5 * pixel, 0.0F, 5 * pixel, 1.0F - 5 * pixel, 6 * pixel, 1.0F - 5 * pixel);
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.POOP_BLOCK_NAME));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighbourID) {
		if (!canBlockStay(world, x, y, z)) {
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlock(x, y, z, 0, 0, 2);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block block = Block.blocksList[world.getBlockId(x, y - 1, z)];
		return block != null && block.isBlockSolidOnSide(world, x, y, z, ForgeDirection.UP);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return canBlockStay(world, x, y, z);
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return false;
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
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public int idDropped(int id, Random rand, int fortune) {
		return ModItems.poop.itemID;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		world.spawnParticle("mobSpell", x + 0.5D, y + 0.3D, z + 0.5D, 45.0D / 255.0D, 104.0D / 255.0D, 20.0D / 255.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int x, int y, int z) {
		return ModItems.poop.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icons = new Icon[2];

		icons[0] = reg.registerIcon(Utils.getBlockTexture(Strings.POOP_BLOCK_NAME + "0"));
		icons[1] = reg.registerIcon(Utils.getBlockTexture(Strings.POOP_BLOCK_NAME + "1"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta > icons.length)
			meta = 0;
		return icons[meta];
	}
}