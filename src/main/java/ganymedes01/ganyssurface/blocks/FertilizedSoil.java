package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FertilizedSoil extends Block {

	public FertilizedSoil() {
		super(Material.ground);
		setHardness(0.6F);
		setLightOpacity(0);
		setTickRandomly(true);
		setHarvestLevel("shovel", 0);
		setStepSound(soundTypeGravel);
		setCreativeTab(GanysSurface.surfaceTab);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
		setBlockName(Utils.getUnlocalizedName(Strings.FERTILIZED_SOIL_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.FERTILIZED_SOIL_NAME));
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plant) {
		return Blocks.farmland.canSustainPlant(world, x, y, z, direction, plant);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (isWaterNearby(world, x, y, z))
			if (world.getBlock(x, y + 1, z) instanceof IPlantable)
				if (world.getBlockMetadata(x, y + 1, z) < 7)
					world.setBlockMetadataWithNotify(x, y + 1, z, world.getBlockMetadata(x, y + 1, z) + 1, 2);
				else if (rand.nextInt(50) == 25)
					world.setBlock(x, y, z, Blocks.dirt);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	private boolean isWaterNearby(World world, int x, int y, int z) {
		for (int i = x - 4; i <= x + 4; i++)
			for (int j = y; j <= y + 1; j++)
				for (int k = z - 4; k <= z + 4; k++)
					if (world.getBlock(i, j, k).getMaterial() == Material.water)
						return true;
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(Blocks.dirt);
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}
}