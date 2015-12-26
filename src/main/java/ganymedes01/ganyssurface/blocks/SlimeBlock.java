package ganymedes01.ganyssurface.blocks;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.api.ISlimeBlockSpreable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModSounds;
import ganymedes01.ganyssurface.lib.Reference;
import ganymedes01.ganyssurface.lib.RenderIDs;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SlimeBlock extends Block implements IConfigurable {

	public SlimeBlock() {
		super(Material.cloth);
		setHardness(2.0F);
		setTickRandomly(true);
		setHarvestLevel("shovel", 0);
		setBlockTextureName("slime");
		setStepSound(ModSounds.soundSlime);
		setBlockName(Utils.getUnlocalisedName(Strings.SLIME_BLOCK_NAME));
		setCreativeTab(GanysSurface.enableSlimeBlock ? GanysSurface.surfaceTab : null);
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (!entity.isSneaking()) {
			entity.fallDistance = 0;
			if (entity.motionY < 0)
				entity.getEntityData().setDouble(Reference.MOD_ID + ":slime", -entity.motionY);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		NBTTagCompound data = entity.getEntityData();
		if (data.hasKey(Reference.MOD_ID + ":slime")) {
			entity.motionY = data.getDouble(Reference.MOD_ID + ":slime");
			data.removeTag(Reference.MOD_ID + ":slime");
		}

		if (Math.abs(entity.motionY) < 0.1 && !entity.isSneaking()) {
			double d = 0.4 + Math.abs(entity.motionY) * 0.2;
			entity.motionX *= d;
			entity.motionZ *= d;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.875F, z + 1);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int xx = x + dir.offsetX;
			int yy = y + dir.offsetY;
			int zz = z + dir.offsetZ;

			Block block = world.getBlock(xx, yy, zz);
			int meta = world.getBlockMetadata(xx, yy, zz);
			boolean spread = false;

			if (block.getMaterial() == Material.water && meta == 0 && rand.nextFloat() <= 0.025F)
				spread = true;
			else if (block instanceof ISlimeBlockSpreable && rand.nextFloat() <= ((ISlimeBlockSpreable) block).getSpreadChance(world, xx, yy, zz))
				spread = true;

			if (spread) {
				world.setBlock(xx, yy, zz, this);
				return;
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(Items.slime_ball, 4));
		return list;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
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
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.SLIME_BLOCK;
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableSlimeBlock;
	}
}