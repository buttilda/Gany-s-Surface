package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModSounds;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
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

public class PoopBlock extends Block implements IGrowable, IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public PoopBlock() {
		super(Material.circuits);
		float pixel = 1.0F / 16.0F;
		disableStats();
		setTickRandomly(true);
		setStepSound(ModSounds.soundSlime);
		setBlockName(Utils.getUnlocalisedName(Strings.POOP_BLOCK_NAME));
		setBlockBounds(5 * pixel, 0.0F, 5 * pixel, 1.0F - 5 * pixel, 6 * pixel, 1.0F - 5 * pixel);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (world.isRemote)
			return;
		boolean flag = rand.nextInt(getBonemealchance(world, x, y - 1, z)) == 0;
		if (!world.provider.hasNoSky && world.canBlockSeeTheSky(x, y + 1, z) && (world.isRaining() || world.isThundering()))
			flag = true;

		world.setBlockToAir(x, y, z);
		if (GanysSurface.poopRandomBonemeals && flag)
			ItemDye.func_150919_a(new ItemStack(Items.dye, 1, 15), world, x, y - 1, z);
	}

	private int getBonemealchance(World world, int x, int y, int z) {
		Material groundMaterial = world.getBlock(x, y - 1, z).getMaterial();
		if (groundMaterial == Material.ground || groundMaterial == Material.grass || groundMaterial == Material.sand)
			return 30;
		return 50;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		if (!canBlockStay(world, x, y, z)) {
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlock(x, y - 1, z).isSideSolid(world, x, y, z, ForgeDirection.UP);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return canBlockStay(world, x, y, z);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
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
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.poop, 1, meta));
		return list;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		world.spawnParticle("mobSpell", x + 0.5D, y + 0.3D, z + 0.5D, 45.0D / 255.0D, 104.0D / 255.0D, 20.0D / 255.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return ModItems.poop;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[2];

		icons[0] = reg.registerIcon(Utils.getBlockTexture(Strings.POOP_BLOCK_NAME + "0"));
		icons[1] = reg.registerIcon(Utils.getBlockTexture(Strings.POOP_BLOCK_NAME + "1"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta > icons.length)
			meta = 0;
		return icons[meta];
	}

	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean bool) {
		return false;
	}

	@Override
	public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
		return false;
	}

	@Override
	public void func_149853_b(World world, Random rand, int x, int y, int z) {
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enablePoop;
	}
}